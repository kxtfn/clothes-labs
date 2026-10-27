package ua.edu.sumdu.oop;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/** Драйвер роботи №5 з безпечним меню та ArrayList. */
public class Main {
    /** Запускає програму. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        ArrayList<Clothes> items = new ArrayList<Clothes>();
        System.out.println("Практична робота №5. Варіант 1. Перевірка даних та JUnit.");
        boolean running = true;
        while (running) {
            System.out.println("\n1 - Створити новий об'єкт");
            System.out.println("2 - Вивести всі об'єкти");
            System.out.println("3 - Завершити роботу");
            int choice = ConsoleInput.readInt(scanner, "Ваш вибір: ", 1, 3);
            switch (choice) {
                case 1:
                    create(scanner, items);
                    break;
                case 2:
                    printAll(items);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }

    /** Запитує дані та додає лише успішно створений об'єкт. */
    private static void create(Scanner scanner, ArrayList<Clothes> items) {
        String name = ConsoleInput.readText(scanner, "Назва: ");
        int size = ConsoleInput.readInt(scanner, "Розмір (32-70): ", 32, 70);
        double price = ConsoleInput.readPositiveDouble(scanner, "Ціна: ");
        String color = ConsoleInput.readText(scanner, "Колір: ");
        int quantity = ConsoleInput.readInt(scanner, "Кількість (0-100000): ", 0, 100000);
        try {
            items.add(new Clothes(name, size, price, color, quantity));
            System.out.println("Предмет додано.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Не вдалося створити об'єкт: " + exception.getMessage());
        }
    }

    /** Виводить колекцію. */
    private static void printAll(ArrayList<Clothes> items) {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        for (Clothes item : items) System.out.println(item.toString());
    }
}
