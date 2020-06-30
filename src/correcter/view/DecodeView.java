package correcter.view;

import correcter.util.BitUtility;

public class DecodeView {
    public void displayReceived(String fileName, char[] chars) {
        System.out.println(fileName + ":");
        System.out.print("hex view: ");
        BitUtility.printHexView(chars);
        System.out.print("bin view: ");
        BitUtility.printBinView(chars);
        System.out.println();
    }

    public void displayCorrected(String fileName, boolean[] corrected) {
        System.out.println(fileName + ":");
        System.out.print("correct: ");
        BitUtility.printBoolView(corrected);
    }

    public void displayDecoded(boolean[] decoded) {
        System.out.print("decode: ");
        BitUtility.printBoolView(decoded);
    }

    public void displayRemoved(boolean[] removed) {
        System.out.print("remove: ");
        BitUtility.printBoolView(removed);
    }

    public void displayTextHex(char[] chars) {
        System.out.print("hex view: ");
        BitUtility.printHexView(chars);
    }

    public void displayText(String text) {
        System.out.println("text view: " + text);
    }
}
