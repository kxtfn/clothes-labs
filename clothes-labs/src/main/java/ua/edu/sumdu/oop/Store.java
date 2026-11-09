
package ua.edu.sumdu.oop;

import java.util.ArrayList;

public class Store {

    private final String name;
    private final ArrayList<Clothes> items;

    public Store(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name cannot be empty.");
        }

        this.name = name.trim();
        this.items = new ArrayList<Clothes>();
    }

    public String getName() {
        return name;
    }

    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        for (Clothes existing : items) {
            if (sameProduct(existing, cl)) {
                int newQuantity = Math.addExact(
                        existing.getQuantity(), quantity
                );

                existing.setQuantity(newQuantity);
                return;
            }
        }

        Clothes copy = copyOf(cl);
        copy.setQuantity(quantity);
        items.add(copy);
    }

    private boolean sameProduct(Clothes first, Clothes second) {
        if (first.getClass() != second.getClass()) {
            return false;
        }

        Clothes firstCopy = copyOf(first);
        Clothes secondCopy = copyOf(second);

        firstCopy.setQuantity(0);
        secondCopy.setQuantity(0);

        return firstCopy.equals(secondCopy);
    }

    public ArrayList<Clothes> getAll() {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        for (Clothes item : items) {
            result.add(copyOf(item));
        }

        return result;
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

    public ArrayList<Clothes> searchByName(String name) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        if (name == null || name.trim().isEmpty()) {
            return result;
        }

        String searchName = name.trim();

        for (Clothes item : items) {
            if (item.getName().equalsIgnoreCase(searchName)) {
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

        if (season == null) {
            return result;
        }

        for (Clothes item : items) {
            if (item.getSeason() == season) {
                result.add(copyOf(item));
            }
        }

        return result;
    }

    public static Clothes copyOf(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        if (item instanceof Jeans) {
            Jeans jeans = (Jeans) item;

            return new Jeans(
                    jeans.getName(),
                    jeans.getSize(),
                    jeans.getPrice(),
                    jeans.getColor(),
                    jeans.getQuantity(),
                    jeans.getSeason(),
                    jeans.getWaistCm(),
                    jeans.getDenimWeightOz()
            );
        }

        if (item instanceof TShirt) {
            TShirt shirt = (TShirt) item;

            return new TShirt(
                    shirt.getName(),
                    shirt.getSize(),
                    shirt.getPrice(),
                    shirt.getColor(),
                    shirt.getQuantity(),
                    shirt.getSeason(),
                    shirt.getSleeveLengthCm(),
                    shirt.getPrintText()
            );
        }

        if (item instanceof Pants) {
            Pants pants = (Pants) item;

            return new Pants(
                    pants.getName(),
                    pants.getSize(),
                    pants.getPrice(),
                    pants.getColor(),
                    pants.getQuantity(),
                    pants.getSeason(),
                    pants.getWaistCm()
            );
        }

        if (item instanceof Shirts) {
            Shirts shirts = (Shirts) item;

            return new Shirts(
                    shirts.getName(),
                    shirts.getSize(),
                    shirts.getPrice(),
                    shirts.getColor(),
                    shirts.getQuantity(),
                    shirts.getSeason(),
                    shirts.getSleeveLengthCm()
            );
        }

        throw new IllegalArgumentException("Unsupported product type.");
    }
}
