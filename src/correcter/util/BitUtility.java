package correcter.util;

public class BitUtility {
    private BitUtility() {} // don't instantiate

    public static void printHexView(char[] chars) {
        for (char c : chars) {
            System.out.printf("%02X ", (int) c);
        }

        System.out.println();
    }

    public static void printBinView(char[] chars) {
        for (char c : chars) {
            for (int bit = 7; bit >= 0; bit--) {
                 System.out.print(isBitSet(c, bit) ? "1" : "0");
            }

            System.out.print(" ");
        }

        System.out.println();
    }

    public static void printBoolView(boolean[] bits) {
        for (int i = 0; i < bits.length; i++) {
            if (i != 0 && i % 8 == 0) {
                System.out.print(" ");
            }

            System.out.print(bits[i] ? "1" : "0");
        }

        System.out.println();
    }

    public static boolean[] charsToBits(char[] chars) {
        boolean[] bits = new boolean[chars.length * 8];
        int bitIndex = 0;

        for (char c : chars) {
            for (int bit = 7; bit >= 0; bit--) {
                bits[bitIndex] = isBitSet(c, bit);
                bitIndex++;
            }
        }

        return bits;
    }

    public static boolean isBitSet(char c, int bit) {
        if (bit < 0 || bit > 7) {
            throw new IllegalArgumentException("Bit must be between 0 and 7 (" + bit + ")");
        }

        return (c & (1 << bit)) != 0;
    }

    public static char[] bitsToChars(boolean[] bits) {
        if (bits.length % 8 != 0) {
            throw new IllegalArgumentException("Bits cannot fit evenly into chars (length = " + bits.length + ")");
        }

        char[] chars = new char[bits.length / 8];

        for (int charIndex = 0; charIndex < chars.length; charIndex++) {
            //for (int bitIndex = byteIndex * 8 + 7; bitIndex < byteIndex * 8; bitIndex--) {
            int charInt = 0;

            for (int j = 0; j < 8; j++) {
                boolean thisBit = bits[charIndex * 8 + 7 - j];

                if (thisBit) {
                    charInt += (int) Math.pow(2, j);
                }
            }

            chars[charIndex] = (char) charInt;
        }

        return chars;
    }
}
