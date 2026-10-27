package ua.edu.sumdu.oop;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/** Драйвер практичної роботи №8: поліморфна колекція одягу. */
public class Main {

    /** Запускає консольне меню. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        ArrayList<Clothes> items = new ArrayList<Clothes>();
        System.out.println("Практична робота №8. Варіант 1. Ієрархія одягу.");

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

    /** Створює об'єкти потрібного типу без попереднього заповнення колекції. */
    private static void create(Scanner scanner, ArrayList<Clothes> items) {
        System.out.println("0 - Назад, 1 - Clothes, 2 - Pants, 3 - Shirts");
        System.out.println("4 - Jeans, 5 - TShirt");
        int type = ConsoleInput.readInt(scanner, "Тип: ", 0, 5);
        if (type == 0) return;
        String name = ConsoleInput.readText(scanner, "Назва: ");
        int size = ConsoleInput.readInt(scanner, "Розмір (32-70): ", 32, 70);
        double price = ConsoleInput.readPositiveDouble(scanner, "Ціна: ");
        String color = ConsoleInput.readText(scanner, "Колір: ");
        int quantity = ConsoleInput.readInt(scanner, "Кількість (0-100000): ", 0, 100000);
        Season season = ConsoleInput.readSeason(scanner);
        try {
            Clothes item;
            switch (type) {
                case 1:
                    item = new Clothes(name, size, price, color, quantity, season);
                    break;
                case 2:
                    int waist = ConsoleInput.readInt(scanner, "Талія (50-140 см): ", 50, 140);
                    item = new Pants(name, size, price, color, quantity, season, waist);
                    break;
                case 3:
                    int sleeve = ConsoleInput.readInt(scanner, "Рукав (1-120 см): ", 1, 120);
                    item = new Shirts(name, size, price, color, quantity, season, sleeve);
                    break;
                case 4:
                    int jeansWaist = ConsoleInput.readInt(scanner, "Талія (50-140 см): ", 50, 140);
                    int weight = ConsoleInput.readInt(scanner, "Щільність деніму (5-30 oz): ", 5, 30);
                    item = new Jeans(name, size, price, color, quantity, season, jeansWaist, weight);
                    break;
                case 5:
                    int shirtSleeve = ConsoleInput.readInt(scanner, "Рукав (1-120 см): ", 1, 120);
                    String printText = ConsoleInput.readText(scanner, "Напис (або 'Без принта'): ");
                    item = new TShirt(name, size, price, color, quantity, season, shirtSleeve, printText);
                    break;
                default:
                    return;
            }
            items.add(item);
            System.out.println("Додано: " + item.getClass().getSimpleName());
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення: " + exception.getMessage());
        }
    }

    /** Виводить усі типи через посилання базового класу. */
    private static void printAll(ArrayList<Clothes> items) {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        int index = 1;
        for (Clothes item : items) {
            System.out.println(index + ". [" + item.getClass().getSimpleName() + "] " + item.toString());
            index++;
        }
    }
}
