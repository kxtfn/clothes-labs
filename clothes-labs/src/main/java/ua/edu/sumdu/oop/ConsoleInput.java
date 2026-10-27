package ua.edu.sumdu.oop;

import java.util.Scanner;

/** Безпечне введення та повторне запитування даних користувача. */
public final class ConsoleInput {
    /** Заборонено створювати екземпляри допоміжного класу. */
    private ConsoleInput() { }

    /** Зчитує непорожній рядок. */
    public static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("Помилка: порожній рядок.");
        }
    }

    /** Зчитує ціле число в заданому включному діапазоні. */
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(text);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException exception) {
                // Для порожніх і нечислових значень повторюємо запит.
            }
            System.out.println("Помилка: введіть ціле число від " + min + " до " + max + ".");
        }
    }

    /** Зчитує додатне скінченне дійсне число. */
    public static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim().replace(',', '.');
            try {
                double value = Double.parseDouble(text);
                if (Double.isFinite(value) && value > 0) return value;
            } catch (NumberFormatException exception) {
                // Невірний формат: запитуємо повторно.
            }
            System.out.println("Помилка: введіть додатне число.");
        }
    }

    /** Зчитує одну з чотирьох сезонних категорій. */
    public static Season readSeason(Scanner scanner) {
        while (true) {
            String value = readText(scanner, "Сезон (SPRING/SUMMER/AUTUMN/WINTER): ");
            try {
                return Season.valueOf(value.toUpperCase(java.util.Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                System.out.println("Помилка: невідомий сезон.");
            }
        }
    }
}
