package correcter.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataAccess {
    public char[] getCharArray(String fileName) {
        File file = new File(fileName);

        if (!file.isFile()) {
            System.err.println("Could not find file " + fileName);
            return new char[0];
        }

        char[] chars = new char[(int) file.length()];
        int charsIndex = 0;

        try (DataInputStream reader = new DataInputStream(new FileInputStream(file))) {
            while (reader.available() > 0) {
                chars[charsIndex] = (char) reader.readUnsignedByte();
                charsIndex++;
            }
        } catch (IOException e) {
            System.err.println("Could not read file " + fileName);
            e.printStackTrace();
            return new char[0];
        }

        return chars;
    }

    public void writeCharArray(String fileName, char[] chars) {
        File file = new File(fileName);

        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file))) {
            for (char c : chars) {
                writer.writeByte(c);
            }
        } catch (IOException e) {
            System.err.println("Could not write to file " + fileName);
            e.printStackTrace();
        }
    }
}
