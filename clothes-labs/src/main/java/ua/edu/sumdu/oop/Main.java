package ua.edu.sumdu.oop;
import java.util.*;
import java.nio.file.Path;
import java.io.IOException;

public final class Main {
    private static final Path FILE = Path.of("input.txt");
    private Main() { }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Store store = new Store("Clothing Store");
        for (Clothes item : FileStorage.load(FILE)) store.addNewClothes(item, item.getQuantity());
        System.out.println("Lab 10 | Variant 1 | Clothing inventory");
        System.out.println("Loaded products: " + store.getAll().size());
        boolean running = true;
        while (running) {
            System.out.println("\n1 - Search products\n2 - Add product\n3 - View all products\n4 - Sort products" + (10 >= 17 ? "\n5 - Update product\n6 - Delete product" : "") + "\n0 - Save and exit");
            int choice = ConsoleInput.readInt(in, "Choice: ", 0, 10 >= 17 ? 6 : 4);
            try {
                switch (choice) {
                    case 1: search(in, store); break;
                    case 2: {
                        Clothes product = create(in);
                        if (product != null) {
                            store.addNewClothes(product, product.getQuantity());
                            System.out.println("Product added successfully.");
                        }
                        break;
                    }
                    case 3: print(store.getAll()); break;
                    case 4: sort(in, store); break;
                    case 5: if (10 >= 17) update(in, store); break;
                    case 6: if (10 >= 17) delete(in, store); break;
                    case 0: FileStorage.save(FILE, store.getAll()); System.out.println("Saved to input.txt. Goodbye!"); running = false; break;
                    default: break;
                }
            } catch (IOException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
    private static Clothes create(Scanner in) {
        System.out.println("1 - Pants | 2 - Shirts | 3 - Jeans | 4 - TShirt | 0 - Back");
        int type = ConsoleInput.readInt(in, "Type: ", 0, 4);
        if (type == 0) return null;
        String name = ConsoleInput.readText(in, "Name: ");
        int size = ConsoleInput.readInt(in, "Size (32-70): ", 32, 70);
        double price = ConsoleInput.readPositiveDouble(in, "Price: ");
        String color = ConsoleInput.readText(in, "Color: ");
        int quantity = ConsoleInput.readInt(in, "Quantity (1-100000): ", 1, 100000);
        Season season = ConsoleInput.readSeason(in);
        if (type == 1 || type == 3) {
            int waist = ConsoleInput.readInt(in, "Waist (50-140 cm): ", 50, 140);
            if (type == 1) return new Pants(name, size, price, color, quantity, season, waist);
            int weight = ConsoleInput.readInt(in, "Denim weight (5-30 oz): ", 5, 30);
            return new Jeans(name, size, price, color, quantity, season, waist, weight);
        }
        int sleeve = ConsoleInput.readInt(in, "Sleeve length (1-120 cm): ", 1, 120);
        if (type == 2) return new Shirts(name, size, price, color, quantity, season, sleeve);
        return new TShirt(name, size, price, color, quantity, season, sleeve, ConsoleInput.readText(in, "Print: "));
    }
    private static void search(Scanner in, Store store) {
        while (true) {
            System.out.println("\nSearch by: 1 - Name | 2 - Size | 3 - Season" + (10 >= 16 ? " | 4 - UUID" : "") + " | 0 - Back");
            int c = ConsoleInput.readInt(in, "Criterion: ", 0, 10 >= 16 ? 4 : 3);
            if (c == 0) return;
            if (c == 1) print(searchByName(store.getAll(), ConsoleInput.readText(in, "Name (case-insensitive): ")));
            else if (c == 2) print(searchBySize(store.getAll(), ConsoleInput.readInt(in, "Size: ", 32, 70)));
            else if (c == 3) print(searchBySeason(store.getAll(), ConsoleInput.readSeason(in)));
            else {
                try {
                    Clothes found = null;
                    if (found == null) System.out.println("No matching products found.");
                    else System.out.println(found);
                } catch (IllegalArgumentException ex) { System.out.println("Invalid UUID format."); }
            }
        }
    }
    private static ArrayList<Clothes> searchByName(ArrayList<Clothes> items, String name) {
        ArrayList<Clothes> matches = new ArrayList<Clothes>();
        for (Clothes item : items) if (item.getName().equalsIgnoreCase(name)) matches.add(item);
        return matches;
    }
    private static ArrayList<Clothes> searchBySize(ArrayList<Clothes> items, int size) {
        ArrayList<Clothes> matches = new ArrayList<Clothes>();
        for (Clothes item : items) if (item.getSize() == size) matches.add(item);
        return matches;
    }
    private static ArrayList<Clothes> searchBySeason(ArrayList<Clothes> items, Season season) {
        ArrayList<Clothes> matches = new ArrayList<Clothes>();
        for (Clothes item : items) if (item.getSeason() == season) matches.add(item);
        return matches;
    }
    private static void print(ArrayList<Clothes> products) {
        if (products.isEmpty()) { System.out.println("No matching products found."); return; }
        for (int i = 0; i < products.size(); i++) System.out.println((i + 1) + ". " + products.get(i));
        System.out.println("Total: " + products.size());
    }
    private static void sort(Scanner in, Store store) {
        while (true) {
            System.out.println("Sort by: 1 - Name | 2 - Price | 3 - Size | 0 - Back");
            int choice = ConsoleInput.readInt(in, "Criterion: ", 0, 3);
            if (choice == 0) return;
            ArrayList<Clothes> result = store.getAll();
            Comparator<Clothes> comparator;
            if (choice == 1) comparator = SORT_NAME;
            else if (choice == 2) comparator = SORT_PRICE;
            else comparator = SORT_SIZE;
            Collections.sort(result, comparator);
            print(result);
        }
    }
    private static final Comparator<Clothes> SORT_NAME = new Comparator<Clothes>() { public int compare(Clothes a, Clothes b) { return a.compareTo(b); } };
    private static final Comparator<Clothes> SORT_PRICE = new Comparator<Clothes>() { public int compare(Clothes a, Clothes b) { return Double.compare(a.getPrice(), b.getPrice()); } };
    private static final Comparator<Clothes> SORT_SIZE = new Comparator<Clothes>() { public int compare(Clothes a, Clothes b) { return Integer.compare(a.getSize(), b.getSize()); } };
    private static void update(Scanner in, Store store) {
        ArrayList<Clothes> all = store.getAll();
        if (all.isEmpty()) { System.out.println("Inventory is empty."); return; }
        print(all);
        int index = ConsoleInput.readInt(in, "Product number (0 to cancel): ", 0, all.size());
        if (index == 0) return;
        Clothes old = all.get(index - 1);
        Clothes replacement = Store.copyOf(old);
        System.out.println("1 - Name | 2 - Price | 3 - Size | 4 - Color | 5 - Quantity | 0 - Back");
        int field = ConsoleInput.readInt(in, "Field: ", 0, 5);
        if (field == 0) return;
        if (field == 1) replacement.setName(ConsoleInput.readText(in, "New name: "));
        if (field == 2) replacement.setPrice(ConsoleInput.readPositiveDouble(in, "New price: "));
        if (field == 3) replacement.setSize(ConsoleInput.readInt(in, "New size: ", 32, 70));
        if (field == 4) replacement.setColor(ConsoleInput.readText(in, "New color: "));
        if (field == 5) replacement.setQuantity(ConsoleInput.readInt(in, "New quantity: ", 0, 100000));
        System.out.println(store.update(old, replacement) ? "Product updated." : "Product not found.");
    }
    private static void delete(Scanner in, Store store) {
        ArrayList<Clothes> all = store.getAll();
        if (all.isEmpty()) { System.out.println("Inventory is empty."); return; }
        print(all);
        int index = ConsoleInput.readInt(in, "Product number (0 to cancel): ", 0, all.size());
        if (index == 0) return;
        String answer = ConsoleInput.readText(in, "Confirm delete (yes/no): ");
        if (!answer.equalsIgnoreCase("yes")) { System.out.println("Deletion cancelled."); return; }
        System.out.println(store.delete(all.get(index - 1)) ? "Product deleted." : "Product not found.");
    }
}
