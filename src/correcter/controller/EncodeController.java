package correcter.controller;

import correcter.dao.DataAccess;
import correcter.util.BitUtility;
import correcter.view.EncodeView;

import static correcter.model.GlobalData.ENCODED_FILE_NAME;
import static correcter.model.GlobalData.SEND_FILE_NAME;

public class EncodeController {
    public void execute() {

        // send.txt
        DataAccess dataAccess = new DataAccess();
        char[] chars = dataAccess.getCharArray(SEND_FILE_NAME);
        EncodeView encodeView = new EncodeView();
        encodeView.displaySendText(SEND_FILE_NAME, chars);

        // encoded.txt
        Boolean[] expanded = charsToExpandedBits(chars);
        encodeView.displayExpanded(ENCODED_FILE_NAME, expanded);
        boolean[] parity = getParityFromExpanded(expanded);
        encodeView.displayParity(parity);
        chars = BitUtility.bitsToChars(parity);
        encodeView.displayHexView(chars);
        dataAccess.writeCharArray(ENCODED_FILE_NAME, chars);
    }

    private Boolean[] charsToExpandedBits(char[] chars) {
        boolean[] bits = BitUtility.charsToBits(chars);
        Boolean[] expanded = new Boolean[(int) Math.ceil(bits.length / 3.0) * 8];
        int expandedIndex = 0;

        for (int bitIndex = 0; bitIndex < bits.length; bitIndex++) {
            if (bitIndex != 0 && bitIndex % 3 == 0) {
                expandedIndex += 2;
            }

            boolean thisBit = bits[bitIndex];
            expanded[expandedIndex] = thisBit;
            expandedIndex++;
            expanded[expandedIndex] = thisBit;
            expandedIndex++;
        }

        return expanded;
    }

    private boolean[] getParityFromExpanded(Boolean[] expanded) {
        boolean[] parity = new boolean[expanded.length];
        boolean[] tripleBits = new boolean[3];

        for (int expandedIndex = 0; expandedIndex < expanded.length; expandedIndex += 8) {
            int tripleBitsIndex = 0;

            for (int parityIndex = expandedIndex; parityIndex < expandedIndex + 6; parityIndex += 2) {
                boolean bitOne = expanded[parityIndex] == null ? false : expanded[parityIndex];
                parity[parityIndex] = bitOne;
                parity[parityIndex + 1] = bitOne;
                tripleBits[tripleBitsIndex] = bitOne;
                tripleBitsIndex++;
            }

            boolean parityBit = tripleBits[0] ^ tripleBits[1] ^ tripleBits[2];
            parity[expandedIndex + 6] = parityBit;
            parity[expandedIndex + 7] = parityBit;
        }

        return parity;
    }

}
