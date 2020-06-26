package correcter;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        DataAccess dataAccess = new DataAccess();
        byte[] send = dataAccess.getByteArray();

        if (send.length > 0) {
            Corrupter corrupter = new Corrupter();
            byte[] received = corrupter.corruptData(send);
            dataAccess.writeByteArray(received);
        }
    }
}
