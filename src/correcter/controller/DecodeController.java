package correcter.controller;

import correcter.dao.DataAccess;
import correcter.util.BitUtility;
import correcter.view.DecodeView;

import java.util.stream.IntStream;

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
        char[] textChars = BitUtility.bitsToChars(decoded);
        decodeView.displayTextHex(textChars);
        String text = String.valueOf(textChars);
        decodeView.displayText(text);
        dataAccess.writeCharArray(DECODED_FILE_NAME, textChars);
    }

    private boolean[] correctReceived(char[] receivedChars) {
        boolean[] received = BitUtility.charsToBits(receivedChars);

        for (int receivedIndex = 0; receivedIndex < received.length; receivedIndex += 8) {
            boolean[] badParity = new boolean[3];
            badParity[0] = isOnesParityBad(received, receivedIndex);
            badParity[1] = isTwosParityBad(received, receivedIndex);
            badParity[2] = isFoursParityBad(received, receivedIndex);
            int badBit = calculateBadBit(badParity);

            if (badBit != 0) {
                received[receivedIndex + badBit - 1] = !received[receivedIndex + badBit - 1];
            }
        }

        return received;
    }

    private boolean[] decodeCorrected(boolean[] corrected) {
        boolean[] decoded = new boolean[corrected.length / 2];

        for (int correctedIndex = 0, decodedIndex = 0;
                correctedIndex < corrected.length;
                correctedIndex += 8, decodedIndex += 4) {
            decoded[decodedIndex] = corrected[correctedIndex + 2];
            decoded[decodedIndex + 1] = corrected[correctedIndex + 4];
            decoded[decodedIndex + 2] = corrected[correctedIndex + 5];
            decoded[decodedIndex + 3] = corrected[correctedIndex + 6];
        }

        return decoded;
    }

    private boolean isOnesParityBad(final boolean[] received, int receivedIndex) {
        int sum = IntStream.of(0, 2, 4, 6)
                .map(i -> received[receivedIndex + i] ? 1 : 0)
                .sum();
        return sum % 2 != 0;
    }

    private boolean isTwosParityBad(boolean[] received, int receivedIndex) {
        int sum = IntStream.of(1, 2, 5, 6)
                .map(i -> received[receivedIndex + i] ? 1 : 0)
                .sum();
        return sum % 2 != 0;
    }

    private boolean isFoursParityBad(boolean[] received, int receivedIndex) {
        int sum = IntStream.of(3, 4, 5, 6)
                .map(i -> received[receivedIndex + i] ? 1 : 0)
                .sum();
        return sum % 2 != 0;
    }

    private int calculateBadBit(boolean[] badParity) {
        int sum = 0;
        sum += badParity[0] ? 1 : 0;
        sum += badParity[1] ? 2 : 0;
        sum += badParity[2] ? 4 : 0;

        return sum;
    }

}
