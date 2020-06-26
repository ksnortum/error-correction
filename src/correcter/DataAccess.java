package correcter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataAccess {
    //private final static String SEND_FILE_NAME = "/home/knute/Documents/send.txt";
    private final static String SEND_FILE_NAME = "send.txt";
    private final static String RECEIVED_FILE_NAME = "received.txt";

    public byte[] getByteArray() {
        File file = new File(SEND_FILE_NAME);

        if (!file.isFile()) {
            System.err.println("Could not find file " + SEND_FILE_NAME);
            return new byte[0];
        }

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.err.println("Could not read file " + SEND_FILE_NAME);
            e.printStackTrace();
            return new byte[0];
        }
    }

    public void writeByteArray(byte[] received) {
        try {
            Files.write(Paths.get(RECEIVED_FILE_NAME), received);
        } catch (IOException e) {
            System.err.println("Could not write to file " + RECEIVED_FILE_NAME);
            e.printStackTrace();
        }
    }
}
