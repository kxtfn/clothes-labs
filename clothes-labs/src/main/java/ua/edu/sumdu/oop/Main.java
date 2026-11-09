
package ua.edu.sumdu.oop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Path file = Path.of("input.txt");

        if (!Files.exists(file)) {
            file = Path.of("clothes-labs", "input.txt");
        }

        Store store = new Store("Clothing Store");

        for (Clothes item : FileStorage.load(file)) {
            try {
                store.addNewClothes(item, item.getQuantity());
            } catch (IllegalArgumentException ex) {
                System.out.println("Loading error: " + ex.getMessage());
            }
        }

        System.out.println("Lab 16 | UUID | Clothing Store");
        System.out.println("Loaded products: " + store.getProductCount());

        boolean running = true;

        while (running) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1 - Search products");
            System.out.println("2 - Add new product");
            System.out.println("3 - View all products");
            System.out.println("4 - Show sorted products");
            System.out.println("5 - Increase product quantity");
            System.out.println("6 - Save products");
            System.out.println("7 - Search by UUID");
            System.out.println("0 - Save and exit");

            int choice = ConsoleInput.readInt(
                    scanner, "Your choice: ", 0, 7);

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
                        sortingMenu(scanner, store);
                        break;
                    case 5:
                        increaseQuantity(scanner, store);
                        break;
                    case 6:
                        saveProducts(store, file);
                        break;
                    case 7:
                        searchUuid(scanner, store);
                        break;
                    case 0:
                        saveProducts(store, file);
                        running = false;
                        break;
                    default:
                        break;
                }
            } catch (IllegalArgumentException | ArithmeticException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        System.out.println("Program finished.");
    }

    private static void searchUuid(Scanner scanner, Store store) {
        String text = ConsoleInput.readText(
                scanner, "Enter UUID: ");

        try {
            Clothes result = store.searchByUuid(text);

            if (result == null) {
                System.out.println("Product not found.");
            } else {
                System.out.println("Product found:");
                System.out.println(result);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void searchMenu(Scanner scanner, Store store) {
        while (true) {
            System.out.println("\n========== SEARCH MENU ==========");
            System.out.println("1 - Search by name");
            System.out.println("2 - Search by size");
            System.out.println("3 - Search by season");
            System.out.println("0 - Back");

            int choice = ConsoleInput.readInt(
                    scanner, "Choice: ", 0, 3);

            if (choice == 0) {
                return;
            }

            ArrayList<Clothes> result;

            switch (choice) {
                case 1:
                    result = store.searchByName(
                            ConsoleInput.readText(scanner, "Name: "));
                    break;
                case 2:
                    result = store.searchBySize(
                            ConsoleInput.readInt(
                                    scanner, "Size: ", 32, 70));
                    break;
                case 3:
                    result = store.searchBySeason(
                            ConsoleInput.readSeason(scanner));
                    break;
                default:
                    return;
            }

            printProducts(result);
        }
    }

    private static void sortingMenu(Scanner scanner, Store store) {
        while (true) {
            System.out.println("\n========== SORTING MENU ==========");
            System.out.println("1 - Sort by name");
            System.out.println("2 - Sort by price");
            System.out.println("3 - Sort by size");
            System.out.println("0 - Back");

            int choice = ConsoleInput.readInt(
                    scanner, "Criterion: ", 0, 3);

            if (choice == 0) {
                return;
            }

            ArrayList<Clothes> sorted = store.getAll();
            Comparator<Clothes> comparator;

            switch (choice) {
                case 1:
                    comparator = (a, b) ->
                            a.getName().compareToIgnoreCase(b.getName());
                    break;
                case 2:
                    comparator = (a, b) ->
                            Double.compare(a.getPrice(), b.getPrice());
                    break;
                case 3:
                    comparator = (a, b) ->
                            Integer.compare(a.getSize(), b.getSize());
                    break;
                default:
                    return;
            }

            Collections.sort(sorted, comparator);
            printProducts(sorted);
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
                scanner, "Type: ", 0, 4);

        if (type == 0) {
            return;
        }

        String name = ConsoleInput.readText(scanner, "Name: ");
        int size = ConsoleInput.readInt(
                scanner, "Size (32-70): ", 32, 70);
        double price = ConsoleInput.readPositiveDouble(
                scanner, "Price: ");
        String color = ConsoleInput.readText(scanner, "Color: ");
        int quantity = ConsoleInput.readInt(
                scanner, "Quantity: ", 1, 100000);
        Season season = ConsoleInput.readSeason(scanner);

        Clothes product;

        if (type == 1 || type == 3) {
            int waist = ConsoleInput.readInt(
                    scanner, "Waist (50-140): ", 50, 140);

            if (type == 1) {
                product = new Pants(name, size, price,
                        color, quantity, season, waist);
            } else {
                int weight = ConsoleInput.readInt(
                        scanner, "Denim weight (5-30): ", 5, 30);

                product = new Jeans(name, size, price,
                        color, quantity, season, waist, weight);
            }
        } else {
            int sleeve = ConsoleInput.readInt(
                    scanner, "Sleeve length (1-120): ", 1, 120);

            if (type == 2) {
                product = new Shirts(name, size, price,
                        color, quantity, season, sleeve);
            } else {
                String printText = ConsoleInput.readText(
                        scanner, "Print text: ");

                product = new TShirt(name, size, price,
                        color, quantity, season, sleeve, printText);
            }
        }

        store.addNewClothes(product, quantity);
        System.out.println("Product added successfully.");
        System.out.println("UUID: " + product.getUuid());
        System.out.println("Unique products: " + store.getProductCount());
    }

    private static void increaseQuantity(
            Scanner scanner, Store store) {

        ArrayList<Clothes> products = store.getAll();

        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        printProducts(products);

        int number = ConsoleInput.readInt(
                scanner, "Product number (0 to cancel): ",
                0, products.size());

        if (number == 0) {
            return;
        }

        int quantity = ConsoleInput.readInt(
                scanner, "Additional quantity: ", 1, 100000);

        store.increaseStock(number - 1, quantity);
        System.out.println("Stock updated successfully.");
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

    private static void saveProducts(Store store, Path file) {
        try {
            FileStorage.save(file, store.getAll());
            System.out.println("Products saved successfully.");
        } catch (IOException ex) {
            System.out.println("Saving error: " + ex.getMessage());
        }
    }
}
