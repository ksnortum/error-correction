package correcter.controller;

import correcter.dao.DataAccess;
import correcter.util.BitUtility;
import correcter.view.EncodeView;

import java.util.stream.IntStream;

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
        Boolean[] expanded = new Boolean[bits.length * 2];

        for (int bitIndex = 0, expandedIndex = 0; bitIndex < bits.length; bitIndex += 4, expandedIndex += 8) {
            expanded[expandedIndex + 2] = bits[bitIndex];
            expanded[expandedIndex + 4] = bits[bitIndex + 1];
            expanded[expandedIndex + 5] = bits[bitIndex + 2];
            expanded[expandedIndex + 6] = bits[bitIndex + 3];
        }

        return expanded;
    }

    private boolean[] getParityFromExpanded(Boolean[] expanded) {
        boolean[] parity = new boolean[expanded.length];

        for (int expandedIndex = 0, parityIndex = 0;
                expandedIndex < expanded.length;
                expandedIndex += 8, parityIndex += 8) {
            parity[parityIndex] = getOnesParity(expanded, expandedIndex);
            parity[parityIndex + 1] = getTwosParity(expanded, expandedIndex);
            parity[parityIndex + 2] = expanded[expandedIndex + 2];
            parity[parityIndex + 3] = getFoursParity(expanded, expandedIndex);
            parity[parityIndex + 4] = expanded[expandedIndex + 4];
            parity[parityIndex + 5] = expanded[expandedIndex + 5];
            parity[parityIndex + 6] = expanded[expandedIndex + 6];
        }

        return parity;
    }

    private boolean getOnesParity(Boolean[] expanded, int expandedIndex) {
        return getParity(IntStream.of(0, 2, 4, 6), expanded, expandedIndex);
    }

    private boolean getTwosParity(Boolean[] expanded, int expandedIndex) {
        return getParity(IntStream.of(1, 2, 5, 6), expanded, expandedIndex);
    }

    private boolean getFoursParity(Boolean[] expanded, int expandedIndex) {
        return getParity(IntStream.of(3, 4, 5, 6), expanded, expandedIndex);
    }

    // 0 if even, 1 if odd
    private boolean getParity(IntStream intStream, Boolean[] bits, int bitsIndex) {
        long count = intStream
                .map(i -> bits[bitsIndex + i] == null || !bits[bitsIndex + i] ? 0 : 1)
                .filter(i -> i == 1)
                .count();
        return count % 2 != 0;
    }
}
