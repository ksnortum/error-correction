package correcter;

import correcter.dao.DataAccess;
import correcter.util.BitUtility;
import correcter.view.EncodeView;

import static correcter.model.GlobalData.SEND_FILE_NAME;

public class Tester {
    public static void main(String[] args) {
        EncodeView view = new EncodeView();
        view.displaySendText("Test String", "Test".toCharArray());

        char c = 'T';
        for (int bit = 7; bit >= 0; bit--) {
            if (BitUtility.isBitSet(c, bit)) {
                System.out.print("1");
            } else {
                System.out.print("0");
            }
        }

        System.out.println();
        DataAccess dataAccess = new DataAccess();
        dataAccess.writeCharArray(SEND_FILE_NAME, "Test".toCharArray());
    }

    private static void printBits(char[] chars) {
        for (char c : chars ) {
            for ( int mask = 0x01; mask != 0x100; mask <<= 1 ) {
                boolean value = ( c & mask ) != 0;

                if (value) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }

            System.out.print(" ");
        }

        System.out.println();
    }
}
