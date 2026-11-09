
package ua.edu.sumdu.oop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

        System.out.println("Lab 18 | Clothing Store");

        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1 - Search products");
            System.out.println("2 - Add new product");
            System.out.println("3 - View all products");
            System.out.println("4 - Show sorted products");
            System.out.println("5 - Increase product quantity");
            System.out.println("6 - Save products");
            System.out.println("7 - Search by UUID");
            System.out.println("8 - Update product by UUID");
            System.out.println("9 - Delete product by UUID");
            System.out.println("0 - Save and exit");

            int choice = ConsoleInput.readInt(
                    scanner, "Your choice: ", 0, 9);

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
                    case 8:
                        updateProduct(scanner, store);
                        break;
                    case 9:
                        deleteProduct(scanner, store);
                        break;
                    case 0:
                        saveProducts(store, file);
                        System.out.println("Program finished.");
                        return;
                    default:
                        break;
                }
            } catch (ProductNotFoundException ex) {
                System.out.println("Not found: " + ex.getMessage());
            } catch (DuplicateProductException ex) {
                System.out.println("Duplicate: " + ex.getMessage());
            } catch (InvalidProductException ex) {
                System.out.println("Invalid product: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (ArithmeticException ex) {
                System.out.println("Error: Quantity is too large.");
            }
        }
    }

    private static void updateProduct(Scanner scanner, Store store) {
        System.out.println("\n========== UPDATE PRODUCT ==========");
        printProducts(store.getAll());

        String uuid = ConsoleInput.readText(
                scanner, "Enter product UUID: ");

        Clothes existing = store.searchByUuid(uuid);

        if (existing == null) {
            throw new ProductNotFoundException(
                    "Product not found: " + uuid);
        }

        System.out.println("Selected product:");
        System.out.println(existing);

        System.out.println("1 - Change name");
        System.out.println("2 - Change size");
        System.out.println("3 - Change price");
        System.out.println("4 - Change color");
        System.out.println("5 - Change quantity");
        System.out.println("0 - Cancel");

        int choice = ConsoleInput.readInt(
                scanner, "Field: ", 0, 5);

        if (choice == 0) {
            return;
        }

        String field;
        String value;

        switch (choice) {
            case 1:
                field = "name";
                value = ConsoleInput.readText(
                        scanner, "New name: ");
                break;
            case 2:
                field = "size";
                value = String.valueOf(ConsoleInput.readInt(
                        scanner, "New size (32-70): ", 32, 70));
                break;
            case 3:
                field = "price";
                value = String.valueOf(
                        ConsoleInput.readPositiveDouble(
                                scanner, "New price: "));
                break;
            case 4:
                field = "color";
                value = ConsoleInput.readText(
                        scanner, "New color: ");
                break;
            case 5:
                field = "quantity";
                value = String.valueOf(ConsoleInput.readInt(
                        scanner, "New quantity: ", 0, 100000));
                break;
            default:
                return;
        }

        store.updateProduct(uuid, field, value);

        System.out.println("Product updated successfully.");
        System.out.println(store.searchByUuid(uuid));
    }

    private static void deleteProduct(Scanner scanner, Store store) {
        System.out.println("\n========== DELETE PRODUCT ==========");
        printProducts(store.getAll());

        String uuid = ConsoleInput.readText(
                scanner, "Enter product UUID: ");

        Clothes existing = store.searchByUuid(uuid);

        if (existing == null) {
            throw new ProductNotFoundException(
                    "Product not found: " + uuid);
        }

        System.out.println(existing);

        String confirmation = ConsoleInput.readText(
                scanner, "Type YES to delete: ");

        if (!confirmation.equalsIgnoreCase("YES")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        store.deleteProduct(uuid);
        System.out.println("Product deleted successfully.");
    }

    private static void searchUuid(Scanner scanner, Store store) {
        String uuid = ConsoleInput.readText(
                scanner, "Enter UUID: ");

        Clothes result = store.searchByUuid(uuid);

        if (result == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println("Product found:");
            System.out.println(result);
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

            sorted.sort(comparator);
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
        String color = ConsoleInput.readText(
                scanner, "Color: ");
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
        System.out.println("Unique products: "
                + store.getProductCount());
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
        System.out.println("Unique products: "
                + store.getProductCount());
        System.out.println("Total quantity: "
                + store.getTotalQuantity());
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
