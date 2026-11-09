
package ua.edu.sumdu.oop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Path inputFile = Path.of("input.txt");

        if (!Files.exists(inputFile)) {
            inputFile = Path.of("clothes-labs", "input.txt");
        }

        Store store = new Store("Clothing Store");

        ArrayList<Clothes> loaded = FileStorage.load(inputFile);

        for (Clothes item : loaded) {
            try {
                store.addNewClothes(item, item.getQuantity());
            } catch (IllegalArgumentException ex) {
                System.out.println("Loading error: " + ex.getMessage());
            }
        }

        System.out.println("Lab 13 | Variant 1 | Clothing Store");
        System.out.println("Loaded products: " + store.getProductCount());
        System.out.println("Total quantity: " + store.getTotalQuantity());

        boolean running = true;

        while (running) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1 - Search products");
            System.out.println("2 - Add new product");
            System.out.println("3 - View all products");
            System.out.println("4 - Show sorted products");
            System.out.println("5 - Increase existing product quantity");
            System.out.println("6 - Save products");
            System.out.println("0 - Save and exit");

            int choice = ConsoleInput.readInt(
                    scanner, "Your choice: ", 0, 6
            );

            try {
                switch (choice) {
                    case 1:
                        searchMenu(scanner, store);
                        break;

                    case 2:
                        addProduct(scanner, store);
                        break;

                    case 3:
                        showProducts(store);
                        break;

                    case 4:
                        showSortedProducts(store);
                        break;

                    case 5:
                        increaseQuantity(scanner, store);
                        break;

                    case 6:
                        saveProducts(store, inputFile);
                        break;

                    case 0:
                        saveProducts(store, inputFile);
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (ArithmeticException ex) {
                System.out.println("Error: Quantity is too large.");
            }
        }

        System.out.println("Program finished.");
    }

    private static void searchMenu(Scanner scanner, Store store) {
        while (true) {
            System.out.println("\n========== SEARCH MENU ==========");
            System.out.println("1 - Search by name");
            System.out.println("2 - Search by size");
            System.out.println("3 - Search by season");
            System.out.println("0 - Back");

            int choice = ConsoleInput.readInt(
                    scanner, "Choice: ", 0, 3
            );

            if (choice == 0) {
                return;
            }

            ArrayList<Clothes> results;

            switch (choice) {
                case 1:
                    String name = ConsoleInput.readText(
                            scanner, "Product name: "
                    );
                    results = store.searchByName(name);
                    break;

                case 2:
                    int size = ConsoleInput.readInt(
                            scanner, "Size (32-70): ", 32, 70
                    );
                    results = store.searchBySize(size);
                    break;

                case 3:
                    Season season = ConsoleInput.readSeason(scanner);
                    results = store.searchBySeason(season);
                    break;

                default:
                    return;
            }

            printProducts(results);
        }
    }

    private static void addProduct(Scanner scanner, Store store) {
        System.out.println("\n========== ADD PRODUCT ==========");
        System.out.println("1 - Pants");
        System.out.println("2 - Shirts");
        System.out.println("3 - Jeans");
        System.out.println("4 - TShirt");
        System.out.println("0 - Back");

        int type = ConsoleInput.readInt(
                scanner, "Product type: ", 0, 4
        );

        if (type == 0) {
            return;
        }

        String name = ConsoleInput.readText(
                scanner, "Name: "
        );

        int size = ConsoleInput.readInt(
                scanner, "Size (32-70): ", 32, 70
        );

        double price = ConsoleInput.readPositiveDouble(
                scanner, "Price: "
        );

        String color = ConsoleInput.readText(
                scanner, "Color: "
        );

        int quantity = ConsoleInput.readInt(
                scanner, "Quantity: ", 1, 100000
        );

        Season season = ConsoleInput.readSeason(scanner);

        Clothes product;

        if (type == 1 || type == 3) {
            int waist = ConsoleInput.readInt(
                    scanner, "Waist (50-140 cm): ", 50, 140
            );

            if (type == 1) {
                product = new Pants(
                        name, size, price, color,
                        quantity, season, waist
                );
            } else {
                int weight = ConsoleInput.readInt(
                        scanner, "Denim weight (5-30 oz): ", 5, 30
                );

                product = new Jeans(
                        name, size, price, color,
                        quantity, season, waist, weight
                );
            }
        } else {
            int sleeve = ConsoleInput.readInt(
                    scanner, "Sleeve length (1-120 cm): ", 1, 120
            );

            if (type == 2) {
                product = new Shirts(
                        name, size, price, color,
                        quantity, season, sleeve
                );
            } else {
                String printText = ConsoleInput.readText(
                        scanner, "Print text: "
                );

                product = new TShirt(
                        name, size, price, color,
                        quantity, season, sleeve, printText
                );
            }
        }

        store.addNewClothes(product, quantity);

        System.out.println("Product added successfully.");
        System.out.println("Unique products: " + store.getProductCount());
        System.out.println("Total quantity: " + store.getTotalQuantity());
    }

    private static void showSortedProducts(Store store) {
        System.out.println("\n========== SORTED PRODUCTS ==========");
        System.out.println("Sorting criterion: Product name (A-Z)");

        ArrayList<Clothes> sorted = store.getAll();

        if (sorted.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        Collections.sort(sorted);
        printProducts(sorted);

        System.out.println("Total products: " + sorted.size());
    }

    private static void increaseQuantity(
            Scanner scanner, Store store) {

        ArrayList<Clothes> products = store.getAll();

        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("\n========== INCREASE STOCK ==========");
        printProducts(products);

        int number = ConsoleInput.readInt(
                scanner,
                "Select product number (0 to cancel): ",
                0,
                products.size()
        );

        if (number == 0) {
            return;
        }

        int quantity = ConsoleInput.readInt(
                scanner,
                "Additional quantity: ",
                1,
                100000
        );

        store.increaseStock(number - 1, quantity);

        System.out.println("Stock updated successfully.");
        System.out.println("Total quantity: " + store.getTotalQuantity());
    }

    private static void showProducts(Store store) {
        System.out.println("\n========== INVENTORY ==========");
        printProducts(store.getAll());

        System.out.println("Unique products: " + store.getProductCount());
        System.out.println("Total quantity: " + store.getTotalQuantity());
    }

    private static void printProducts(ArrayList<Clothes> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }

    private static void saveProducts(
            Store store, Path inputFile) {

        try {
            FileStorage.save(inputFile, store.getAll());
            System.out.println("Products saved successfully.");
            System.out.println("File: " + inputFile.toAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Saving error: " + ex.getMessage());
        }
    }
}
