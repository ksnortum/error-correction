package correcter.controller;

import correcter.dao.DataAccess;
import correcter.view.SendView;

import java.util.Random;

import static correcter.model.GlobalData.ENCODED_FILE_NAME;
import static correcter.model.GlobalData.RECEIVED_FILE_NAME;

public class SendController {
    private final static Random RANDOM = new Random();

    public void execute() {

        // encoded.txt
        DataAccess dataAccess = new DataAccess();
        char[] encoded = dataAccess.getCharArray(ENCODED_FILE_NAME);
        SendView sendView = new SendView();
        sendView.displayEncoded(ENCODED_FILE_NAME, encoded);

        // received.txt
        char[] corrupted = corruptEncodedData(encoded);
        sendView.displayCorrupted(RECEIVED_FILE_NAME, corrupted);
        dataAccess.writeCharArray(RECEIVED_FILE_NAME, corrupted);
    }

    private char[] corruptEncodedData(char[] encoded) {
        char[] corrupted = new char[encoded.length];

        for (int i = 0; i < corrupted.length; i++) {
            // decide which bit
            int bit = RANDOM.nextInt(6) + 1;
            int number = (int) Math.pow(2, bit);

            // is bit in byte on or off (0 or 1)?
            if ((encoded[i] & number) == 0) {
                corrupted[i] = (char) (encoded[i] | number);
            } else {
                corrupted[i] = (char) (encoded[i] & ~number);
            }
        }

        return corrupted;
    }
}
