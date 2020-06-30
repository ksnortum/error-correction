package correcter.view;

import correcter.util.BitUtility;

public class SendView {

    public void displayEncoded(String fileName, char[] encoded) {
        System.out.println(fileName + ":");
        displayHexView(encoded);
        displayBinView(encoded);
    }

    public void displayCorrupted(String fileName, char[] corrupted) {
        System.out.println();
        System.out.println(fileName + ":");
        displayBinView(corrupted);
        displayHexView(corrupted);
    }

    private void displayHexView(char[] chars) {
        System.out.print("hex view: ");
        BitUtility.printHexView(chars);
    }

    private void displayBinView(char[] chars) {
        System.out.print("bin view: ");
        BitUtility.printBinView(chars);
    }

}
