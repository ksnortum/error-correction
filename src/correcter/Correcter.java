package correcter;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Correcter {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final String SYMBOLS = " 0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public void run() {
        // get input
        String text = STDIN.nextLine();
        System.out.println(text);
        // triple each symbol
        text = tripleSymbols(text);
        System.out.println(text);
        text = simulateBadConnection(text);
        System.out.println(text);
        text = correctErrors(text);
        System.out.println(text);
    }

    private String tripleSymbols(String text) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(text.charAt(i));
            }
        }

        return sb.toString();
    }

    private String simulateBadConnection(String text) {
        // loop by three symbols, taking care at the end
        for (int i = 0; i < text.length(); i += 3) {
            // pick 1 - 3 for which symbol (even at end)
            int textIndex = getRandomFromTo(i, i + 2);

            if (textIndex < text.length()) {
                // check that you don't pick the same letter as text
                int symbolIndex;

                do {
                    // pick symbol from String: Space, 0-9, a-z, A-Z
                    symbolIndex = getRandomFromTo(0, SYMBOLS.length() - 1);
                } while (text.charAt(textIndex) == SYMBOLS.charAt(symbolIndex));

                // set new symbol in for selected one
                text = text.substring(0, textIndex) +
                        SYMBOLS.substring(symbolIndex, symbolIndex + 1) +
                        text.substring(textIndex + 1);
            }
        }
        // print error text
        //System.out.println(text);
        return text;
    }

    private String correctErrors(String text) {
        StringBuilder sb = new StringBuilder();

        for (int textIndex = 0; textIndex < text.length(); textIndex += 3) {
            String[] threeSymbols = text.substring(textIndex, textIndex + 3).split("");
            // count number of letters
            Map<String, Long> lettersByCount = Arrays.stream(threeSymbols)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // append letter with a count of 2 to sb
            String letter = lettersByCount.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.joining());
            sb.append(letter);
        }

        return sb.toString();
    }

    private int getRandomFromTo(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
