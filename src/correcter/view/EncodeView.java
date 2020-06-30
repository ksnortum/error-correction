package correcter.view;

import correcter.util.BitUtility;

public class EncodeView {

    public void displaySendText(String fileName, char[] chars) {
        System.out.println(fileName + ":");
        String text = new String(chars);
        System.out.printf("text view: %s%n", text);
        displayHexView(chars);
        System.out.print("bin view: ");
        BitUtility.printBinView(chars);
        System.out.println();
    }

    public void displayExpanded(String fileName, Boolean[] expanded) {
        System.out.println(fileName + ":");
        System.out.print("expand:");

        for (int i = 0; i < expanded.length; i++) {
            if (i % 8 == 0) {
                System.out.print(" ");
            }

            if (expanded[i] == null) {
                System.out.print(".");
            } else {
                System.out.print(expanded[i] ? "1" : "0");
            }
        }

        System.out.println();
    }

    public void displayParity(boolean[] parity) {
        System.out.print("parity: ");
        BitUtility.printBoolView(parity);
    }

    public void displayHexView(char[] chars) {
        System.out.print("hex view: ");
        BitUtility.printHexView(chars);
    }
}
