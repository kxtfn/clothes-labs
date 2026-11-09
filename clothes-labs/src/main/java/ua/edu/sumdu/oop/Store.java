
package ua.edu.sumdu.oop;

import java.util.ArrayList;
import java.util.UUID;

public class Store {

    private final String name;
    private final ArrayList<Clothes> items;

    public Store(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name is required.");
        }

        this.name = name.trim();
        this.items = new ArrayList<Clothes>();
    }

    public String getName() {
        return name;
    }

    public int getProductCount() {
        return items.size();
    }

    public int getTotalQuantity() {
        int total = 0;
        for (Clothes item : items) {
            total = Math.addExact(total, item.getQuantity());
        }
        return total;
    }

    public void addNewClothes(Clothes product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Product and positive quantity are required.");
        }

        for (Clothes existing : items) {
            if (sameProduct(existing, product)) {
                existing.setQuantity(Math.addExact(
                        existing.getQuantity(), quantity));
                return;
            }
        }

        Clothes copy = copyOf(product);
        copy.setQuantity(quantity);
        items.add(copy);
    }

    private boolean sameProduct(Clothes first, Clothes second) {
        if (first.getClass() != second.getClass()) {
            return false;
        }

        Clothes a = copyOf(first);
        Clothes b = copyOf(second);
        a.setQuantity(0);
        b.setQuantity(0);

        return a.equals(b);
    }

    public void increaseStock(int index, int quantity) {
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException(
                    "Invalid product number.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive.");
        }

        Clothes item = items.get(index);
        item.setQuantity(Math.addExact(
                item.getQuantity(), quantity));
    }

    public Clothes searchByUuid(String text) {
        if (text == null) {
            throw new IllegalArgumentException(
                    "UUID cannot be empty.");
        }

        UUID uuid;

        try {
            uuid = UUID.fromString(text.trim());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid UUID format.");
        }

        for (Clothes item : items) {
            if (item.getUuid().equals(uuid)) {
                return copyOf(item);
            }
        }

        return null;
    }

    public ArrayList<Clothes> searchByName(String name) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        if (name == null) {
            return result;
        }

        for (Clothes item : items) {
            if (item.getName().equalsIgnoreCase(name.trim())) {
                result.add(copyOf(item));
            }
        }
        return result;
    }

    public ArrayList<Clothes> searchBySize(int size) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        for (Clothes item : items) {
            if (item.getSize() == size) {
                result.add(copyOf(item));
            }
        }
        return result;
    }

    public ArrayList<Clothes> searchBySeason(Season season) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        for (Clothes item : items) {
            if (item.getSeason() == season) {
                result.add(copyOf(item));
            }
        }
        return result;
    }

    public ArrayList<Clothes> getAll() {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        for (Clothes item : items) {
            result.add(copyOf(item));
        }

        return result;
    }

    public static Clothes copyOf(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException("Product is null.");
        }

        Clothes copy;

        if (item instanceof Jeans) {
            Jeans v = (Jeans) item;
            copy = new Jeans(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getWaistCm(),
                    v.getDenimWeightOz());

        } else if (item instanceof TShirt) {
            TShirt v = (TShirt) item;
            copy = new TShirt(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getSleeveLengthCm(),
                    v.getPrintText());

        } else if (item instanceof Pants) {
            Pants v = (Pants) item;
            copy = new Pants(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getWaistCm());

        } else if (item instanceof Shirts) {
            Shirts v = (Shirts) item;
            copy = new Shirts(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getSleeveLengthCm());

        } else {
            throw new IllegalArgumentException(
                    "Unsupported product type.");
        }

        copy.restoreUuid(item.getUuid());
        return copy;
    }
}
