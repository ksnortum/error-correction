package correcter;

import java.util.Random;

public class Corrupter {
    private static final Random RANDOM = new Random();

    public byte[] corruptData(byte[] send) {
        byte[] received = new byte[send.length];

        for (int i = 0; i < received.length; i++) {
            // decide which bit
            byte bit = (byte) RANDOM.nextInt(7);
            byte number = (byte) Math.pow(2, bit);
//            System.out.println("Working on : " + Integer.toBinaryString(send[i])); // debug
//            System.out.println("Bit to flip: " + Integer.toBinaryString(number)); // debug
//            System.out.println("Random bit : " + bit); // debug

            // is bit in byte on or off (0 or 1)?
            if ((send[i] & number) == 0) {
                //System.out.println("Bit is off"); // debug
                received[i] = (byte) (send[i] | number);
                //System.out.println("Number or'd: " + Integer.toBinaryString(received[i])); // debug
            } else {
                //System.out.println("Bit is on"); // debug
                received[i] = (byte) (send[i] & ~number);
                //System.out.println("Number invert and'd: " + Integer.toBinaryString(received[i])); // debug
            }

            //System.out.println(); // debug
        }

        return received;
    }
}
