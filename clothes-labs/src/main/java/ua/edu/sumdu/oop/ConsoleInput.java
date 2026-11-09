
package ua.edu.sumdu.oop;

import java.util.Locale;
import java.util.Scanner;

public final class ConsoleInput {
    private ConsoleInput() {
    }

    public static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();

            if (!text.isEmpty()) {
                return text;
            }

            System.out.println("Error: input cannot be empty.");
        }
    }

    public static int readInt(Scanner scanner, String prompt,
                              int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(line);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Error: enter a whole number from "
                    + min + " to " + max + ".");
        }
    }

    public static double readPositiveDouble(
            Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim()
                    .replace(',', '.');

            try {
                double number = Double.parseDouble(line);

                if (Double.isFinite(number) && number > 0) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Error: enter a positive number.");
        }
    }

    public static Season readSeason(Scanner scanner) {
        while (true) {
            String text = readText(
                    scanner,
                    "Season (SPRING/SUMMER/AUTUMN/WINTER): "
            );

            try {
                return Season.valueOf(
                        text.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                System.out.println("Error: invalid season.");
            }
        }
    }
}
