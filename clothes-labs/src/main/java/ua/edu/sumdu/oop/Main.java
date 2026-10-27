package ua.edu.sumdu.oop;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Драйвер роботи №4: масив предметів одягу. */
public class Main {
    /** Запускає програму та заповнює масив з клавіатури. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("Практична робота №4. Клас Clothes. Варіант 1.");
        int count = readInt(scanner, "Кількість предметів (1-100): ", 1, 100);
        Clothes[] items = new Clothes[count];
        for (int i = 0; i < count; i++) {
            System.out.println("Предмет " + (i + 1) + ":");
            String name = readText(scanner, "Назва: ");
            int size = readInt(scanner, "Розмір (32-70): ", 32, 70);
            double price = readPrice(scanner, "Ціна: ");
            items[i] = new Clothes(name, size, price);
        }
        System.out.println("\nУсі створені предмети:");
        for (Clothes item : items) {
            System.out.println(item.toString());
        }
    }

    /** Читає непорожній рядок. */
    private static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("Помилка: рядок не може бути порожнім.");
        }
    }

    /** Читає ціле число в межах допустимого діапазону. */
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number >= min && number <= max) return number;
            } catch (NumberFormatException exception) {
                // Повторюємо запит.
            }
            System.out.println("Помилка: число має бути від " + min + " до " + max + ".");
        }
    }

    /** Читає додатну ціну. */
    private static double readPrice(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (Double.isFinite(value) && value > 0) return value;
            } catch (NumberFormatException exception) {
                // Повторюємо запит.
            }
            System.out.println("Помилка: ціна має бути додатною.");
        }
    }
}
