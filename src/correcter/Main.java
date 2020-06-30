package correcter;

import correcter.controller.DecodeController;
import correcter.controller.EncodeController;
import correcter.controller.SendController;

import java.util.Scanner;

public class Main {
    private static final Scanner STDIN = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.print("Write a mode: ");
        String mode = STDIN.nextLine();

        switch (mode) {
            case "encode":
                EncodeController encodeController = new EncodeController();
                encodeController.execute();
                break;
            case "send":
                SendController sendController = new SendController();
                sendController.execute();
                break;
            case "decode":
                DecodeController decodeController = new DecodeController();
                decodeController.execute();
                break;
            default:
                System.out.println("Invalid mode");
        }
    }
}
