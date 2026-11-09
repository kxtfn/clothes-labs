
package ua.edu.sumdu.oop;

import java.util.ArrayList;

public class Store {
    private final String name;
    private final ArrayList<Clothes> items = new ArrayList<Clothes>();

    public Store(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name is required.");
        }
        this.name = name.trim();
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

    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Product and positive quantity are required.");
        }

        for (Clothes item : items) {
            if (sameProduct(item, cl)) {
                item.setQuantity(Math.addExact(
                        item.getQuantity(), quantity));
                return;
            }
        }

        Clothes copy = copyOf(cl);
        copy.setQuantity(quantity);
        items.add(copy);
    }

    public void increaseStock(int index, int quantity) {
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException(
                    "Product number is invalid.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive.");
        }

        Clothes item = items.get(index);
        item.setQuantity(Math.addExact(
                item.getQuantity(), quantity));
    }

    private boolean sameProduct(Clothes a, Clothes b) {
        if (a.getClass() != b.getClass()) {
            return false;
        }

        Clothes first = copyOf(a);
        Clothes second = copyOf(b);
        first.setQuantity(0);
        second.setQuantity(0);

        return first.equals(second);
    }

    public ArrayList<Clothes> getAll() {
        ArrayList<Clothes> result = new ArrayList<Clothes>();
        for (Clothes item : items) {
            result.add(copyOf(item));
        }
        return result;
    }

    public ArrayList<Clothes> searchByName(String name) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

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

    public static Clothes copyOf(Clothes a) {
        if (a instanceof Jeans) {
            Jeans v = (Jeans) a;
            return new Jeans(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getWaistCm(),
                    v.getDenimWeightOz());
        }

        if (a instanceof TShirt) {
            TShirt v = (TShirt) a;
            return new TShirt(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getSleeveLengthCm(),
                    v.getPrintText());
        }

        if (a instanceof Pants) {
            Pants v = (Pants) a;
            return new Pants(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getWaistCm());
        }

        if (a instanceof Shirts) {
            Shirts v = (Shirts) a;
            return new Shirts(v.getName(), v.getSize(),
                    v.getPrice(), v.getColor(), v.getQuantity(),
                    v.getSeason(), v.getSleeveLengthCm());
        }

        throw new IllegalArgumentException("Unsupported product type.");
    }
}
