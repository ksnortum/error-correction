package correcter.controller;

import correcter.dao.DataAccess;
import correcter.util.BitUtility;
import correcter.view.DecodeView;

import java.util.Arrays;

import static correcter.model.GlobalData.DECODED_FILE_NAME;
import static correcter.model.GlobalData.RECEIVED_FILE_NAME;

public class DecodeController {
    public void execute() {
        DataAccess dataAccess = new DataAccess();
        char[] received = dataAccess.getCharArray(RECEIVED_FILE_NAME);
        DecodeView decodeView = new DecodeView();
        decodeView.displayReceived(RECEIVED_FILE_NAME, received);

        boolean[] corrected = correctReceived(received);
        decodeView.displayCorrected(DECODED_FILE_NAME, corrected);
        boolean[] decoded = decodeCorrected(corrected);
        decodeView.displayDecoded(decoded);
        boolean[] removed = Arrays.copyOfRange(decoded, 0, decoded.length - decoded.length % 8);
        decodeView.displayRemoved(removed);
        char[] textChars = BitUtility.bitsToChars(removed);
        decodeView.displayTextHex(textChars);
        String text = String.valueOf(textChars);
        decodeView.displayText(text);
        dataAccess.writeCharArray(DECODED_FILE_NAME, textChars);
    }

    private boolean[] correctReceived(char[] received) {
        boolean[] receivedBits = BitUtility.charsToBits(received);
        boolean[] corrected = receivedBits;

        for (int i = 0; i < receivedBits.length; i += 8) {
            boolean[] eightBits = Arrays.copyOfRange(receivedBits, i, i + 8);
            Boolean[] dataBits = setDataBits(eightBits);
            Boolean parityBit = setBitOrNull(eightBits[6], eightBits[7]);

            if (parityBit != null) {
                // correct bad bit
                int indexToChange = findNullIndex(dataBits);
                boolean[] twoBits = setTwoBits(dataBits);
                dataBits[indexToChange] = twoBits[0] == twoBits[1] ? parityBit : !parityBit;

                // set data into corrected array
                corrected[i] = corrected[i + 1] = dataBits[0];
                corrected[i + 2] = corrected[i + 3] = dataBits[1];
                corrected[i + 4] = corrected[i + 5] = dataBits[2];
            } else {
                // determine correct parity
                parityBit = eightBits[0] ^ eightBits[2] ^ eightBits[4];

                // set parity into corrected array
                corrected[i + 6] = corrected[i + 7] = parityBit;
            }
        }

        return corrected;
    }

    private boolean[] decodeCorrected(boolean[] corrected) {
        boolean[] decoded = new boolean[corrected.length / 8 * 3];
        int decodedIndex = 0;

        for (int i = 0; i < corrected.length; i += 8) {
            decoded[decodedIndex] = corrected[i];
            decodedIndex++;
            decoded[decodedIndex] = corrected[i + 2];
            decodedIndex++;
            decoded[decodedIndex] = corrected[i + 4];
            decodedIndex++;
        }

        return decoded;
    }

    private Boolean[] setDataBits(boolean[] eightBits) {
        Boolean[] dataBits = new Boolean[3];

        for (int i = 0; i < 3; i++) {
            dataBits[i] = setBitOrNull(eightBits[i * 2], eightBits[i * 2 + 1]);
        }

        return dataBits;
    }

    private Boolean setBitOrNull(boolean bitOne, boolean bitTwo) {
        return bitOne != bitTwo ? null : bitOne;
    }

    // -1 is not found
    private int findNullIndex(Boolean[] dataBits) {
        for (int foundIndex = 0; foundIndex < 3; foundIndex++) {
            if (dataBits[foundIndex] == null) {
                return foundIndex;
            }
        }

        return -1;
    }

    private boolean[] setTwoBits(Boolean[] dataBits) {
        boolean[] twoBits = new boolean[2];
        int twoBitsIndex = 0;

        for (Boolean dataBit : dataBits) {
            if (dataBit != null) {
                twoBits[twoBitsIndex] = dataBit;
                twoBitsIndex++;
            }
        }

        return twoBits;
    }
}
