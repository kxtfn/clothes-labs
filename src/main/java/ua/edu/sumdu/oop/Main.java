package ua.edu.sumdu.oop;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Драйвер роботи №6: агрегація, enum, статичні члени, копіювання. */
public class Main {
    /** Запускає інтерактивне меню. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Wardrobe wardrobe = new Wardrobe();
        System.out.println("Практична робота №6. Варіант 1. Одяг та гардероб.");
        boolean running = true;
        while (running) {
            System.out.println("\n1 - Створити предмет або копію");
            System.out.println("2 - Вивести гардероб і лічильник");
            System.out.println("3 - Завершити роботу");
            int choice = ConsoleInput.readInt(scanner, "Ваш вибір: ", 1, 3);
            switch (choice) {
                case 1:
                    create(scanner, wardrobe);
                    break;
                case 2:
                    printAll(wardrobe);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }

    /** Створює новий предмет або його незалежну копію. */
    private static void create(Scanner scanner, Wardrobe wardrobe) {
        System.out.println("1 - Новий предмет, 2 - Копія наявного, 0 - Назад");
        int choice = ConsoleInput.readInt(scanner, "Тип дії: ", 0, 2);
        if (choice == 0) return;
        try {
            Clothes item;
            if (choice == 2) {
                if (wardrobe.size() == 0) {
                    System.out.println("Немає об'єктів для копіювання.");
                    return;
                }
                int index = ConsoleInput.readInt(scanner, "Номер для копіювання (1-"
                        + wardrobe.size() + "): ", 1, wardrobe.size());
                item = new Clothes(wardrobe.getItems().get(index - 1));
            } else {
                String name = ConsoleInput.readText(scanner, "Назва: ");
                int size = ConsoleInput.readInt(scanner, "Розмір (32-70): ", 32, 70);
                double price = ConsoleInput.readPositiveDouble(scanner, "Ціна: ");
                String color = ConsoleInput.readText(scanner, "Колір: ");
                int quantity = ConsoleInput.readInt(scanner, "Кількість (0-100000): ", 0, 100000);
                Season season = ConsoleInput.readSeason(scanner);
                item = new Clothes(name, size, price, color, quantity, season);
            }
            wardrobe.add(item);
            System.out.println("Предмет додано. Створено об'єктів: " + Clothes.getCreatedCount());
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка: " + exception.getMessage());
        }
    }

    /** Демонструє агрегацію: гардероб зберігає посилання на одяг. */
    private static void printAll(Wardrobe wardrobe) {
        if (wardrobe.size() == 0) System.out.println("Гардероб порожній.");
        for (Clothes item : wardrobe.getItems()) System.out.println(item.toString());
        System.out.println("У гардеробі: " + wardrobe.size());
        System.out.println("Усього створено Clothes: " + Clothes.getCreatedCount());
    }
}
