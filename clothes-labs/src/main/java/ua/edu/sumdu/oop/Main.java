
package ua.edu.sumdu.oop;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public final class Main {

    private static final Path FILE = Path.of("input.txt");

    private Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store("Clothing Store");

        loadProducts(store);

        System.out.println("Lab 11 | Variant 1 | Clothing Store");
        System.out.println("Store: " + store.getName());
        System.out.println("Loaded products: " + store.getProductCount());

        boolean running = true;

        while (running) {
            printMainMenu();

            int choice = ConsoleInput.readInt(
                    scanner, "Your choice: ", 0, 3
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
                        printAllProducts(store);
                        break;

                    case 0:
                        saveProducts(store);
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid menu option.");
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

    private static void printMainMenu() {
        System.out.println();
        System.out.println("========== MAIN MENU ==========");
        System.out.println("1 - Search products");
        System.out.println("2 - Add new product");
        System.out.println("3 - View all products");
        System.out.println("0 - Save and exit");
        System.out.println("===============================");
    }

    private static void loadProducts(Store store) {
        ArrayList<Clothes> loaded = FileStorage.load(FILE);

        for (Clothes item : loaded) {
            try {
                store.addNewClothes(item, item.getQuantity());
            } catch (IllegalArgumentException ex) {
                System.out.println(
                        "Skipping invalid product: " + ex.getMessage()
                );
            }
        }
    }

    private static void saveProducts(Store store) {
        try {
            FileStorage.save(FILE, store.getAll());
            System.out.println("Products saved to input.txt.");
        } catch (IOException ex) {
            System.out.println("File saving error: " + ex.getMessage());
        }
    }

    private static void addProduct(Scanner scanner, Store store) {
        System.out.println();
        System.out.println("========== ADD PRODUCT ==========");
        System.out.println("1 - Pants");
        System.out.println("2 - Shirts");
        System.out.println("3 - Jeans");
        System.out.println("4 - TShirt");
        System.out.println("0 - Back to main menu");

        int type = ConsoleInput.readInt(
                scanner, "Product type: ", 0, 4
        );

        if (type == 0) {
            return;
        }

        String name = ConsoleInput.readText(
                scanner, "Product name: "
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
                scanner, "Quantity (1-100000): ", 1, 100000
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
                int denimWeight = ConsoleInput.readInt(
                        scanner, "Denim weight (5-30 oz): ", 5, 30
                );

                product = new Jeans(
                        name, size, price, color,
                        quantity, season, waist, denimWeight
                );
            }
        } else {
            int sleeveLength = ConsoleInput.readInt(
                    scanner, "Sleeve length (1-120 cm): ", 1, 120
            );

            if (type == 2) {
                product = new Shirts(
                        name, size, price, color,
                        quantity, season, sleeveLength
                );
            } else {
                String printText = ConsoleInput.readText(
                        scanner, "Print text: "
                );

                product = new TShirt(
                        name, size, price, color,
                        quantity, season, sleeveLength, printText
                );
            }
        }

        store.addNewClothes(product, quantity);

        System.out.println("Product added successfully.");
        System.out.println(
                "Unique products: " + store.getProductCount()
        );
        System.out.println(
                "Total quantity: " + store.getTotalQuantity()
        );
    }

    private static void searchMenu(Scanner scanner, Store store) {
        boolean searching = true;

        while (searching) {
            System.out.println();
            System.out.println("========== SEARCH MENU ==========");
            System.out.println("1 - Search by name");
            System.out.println("2 - Search by size");
            System.out.println("3 - Search by season");
            System.out.println("0 - Back to main menu");

            int choice = ConsoleInput.readInt(
                    scanner, "Search criterion: ", 0, 3
            );

            ArrayList<Clothes> results;

            switch (choice) {
                case 1:
                    String name = ConsoleInput.readText(
                            scanner, "Enter product name: "
                    );

                    results = store.searchByName(name);
                    printProducts(results);
                    break;

                case 2:
                    int size = ConsoleInput.readInt(
                            scanner, "Enter size (32-70): ", 32, 70
                    );

                    results = store.searchBySize(size);
                    printProducts(results);
                    break;

                case 3:
                    Season season = ConsoleInput.readSeason(scanner);

                    results = store.searchBySeason(season);
                    printProducts(results);
                    break;

                case 0:
                    searching = false;
                    break;

                default:
                    System.out.println("Invalid search criterion.");
                    break;
            }
        }
    }

    private static void printAllProducts(Store store) {
        System.out.println();
        System.out.println("========== STORE INVENTORY ==========");
        System.out.println("Store: " + store.getName());

        printProducts(store.getAll());

        System.out.println(
                "Unique products: " + store.getProductCount()
        );
        System.out.println(
                "Total quantity: " + store.getTotalQuantity()
        );
    }

    private static void printProducts(ArrayList<Clothes> products) {
        if (products.isEmpty()) {
            System.out.println("No matching products found.");
            return;
        }

        System.out.println();
        System.out.println("Products:");

        for (int i = 0; i < products.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + products.get(i)
            );
        }

        System.out.println("Found products: " + products.size());
    }
}
