
package ua.edu.sumdu.oop;

import java.util.ArrayList;
import java.util.UUID;

public class Store {

    private final String name;
    private final ArrayList<Clothes> items;

    public Store(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidProductException("Store name is required.");
        }

        this.name = name.trim();
        this.items = new ArrayList<>();
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
        if (product == null) {
            throw new InvalidProductException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new InvalidProductException(
                    "Quantity must be positive.");
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
            throw new InvalidProductException("Invalid product number.");
        }

        if (quantity <= 0) {
            throw new InvalidProductException(
                    "Quantity must be positive.");
        }

        Clothes item = items.get(index);
        item.setQuantity(Math.addExact(
                item.getQuantity(), quantity));
    }

    public Clothes searchByUuid(String text) {
        UUID uuid = parseUuid(text);
        Clothes item = findInternal(uuid);

        return item == null ? null : copyOf(item);
    }

    public void updateProduct(String uuidText,
                              String field,
                              String value) {
        UUID uuid = parseUuid(uuidText);
        Clothes item = findInternal(uuid);

        if (item == null) {
            throw new ProductNotFoundException(
                    "Product not found: " + uuid);
        }

        if (field == null || value == null) {
            throw new InvalidProductException(
                    "Field and value are required.");
        }

        Clothes updated = copyOf(item);

        try {
            switch (field.toLowerCase()) {
                case "name":
                    updated.setName(value);
                    break;

                case "size":
                    updated.setSize(Integer.parseInt(value.trim()));
                    break;

                case "price":
                    updated.setPrice(Double.parseDouble(
                            value.trim().replace(',', '.')));
                    break;

                case "color":
                    updated.setColor(value);
                    break;

                case "quantity":
                    updated.setQuantity(Integer.parseInt(value.trim()));
                    break;

                default:
                    throw new InvalidProductException(
                            "Unsupported field: " + field);
            }
        } catch (NumberFormatException ex) {
            throw new InvalidProductException(
                    "Invalid numeric value: " + value, ex);
        } catch (IllegalArgumentException ex) {
            if (ex instanceof InvalidProductException) {
                throw ex;
            }
            throw new InvalidProductException(
                    ex.getMessage(), ex);
        }

        for (Clothes other : items) {
            if (!other.getUuid().equals(uuid)
                    && sameProduct(other, updated)) {
                throw new DuplicateProductException(
                        "Update would create a duplicate product.");
            }
        }

        int index = indexOf(uuid);
        items.set(index, updated);
    }

    public void deleteProduct(String uuidText) {
        UUID uuid = parseUuid(uuidText);
        int index = indexOf(uuid);

        if (index < 0) {
            throw new ProductNotFoundException(
                    "Product not found: " + uuid);
        }

        items.remove(index);
    }

    private int indexOf(UUID uuid) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    private Clothes findInternal(UUID uuid) {
        int index = indexOf(uuid);
        return index < 0 ? null : items.get(index);
    }

    private UUID parseUuid(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new InvalidProductException("UUID is required.");
        }

        try {
            return UUID.fromString(text.trim());
        } catch (IllegalArgumentException ex) {
            throw new InvalidProductException(
                    "Invalid UUID format.", ex);
        }
    }

    public ArrayList<Clothes> searchByName(String name) {
        ArrayList<Clothes> result = new ArrayList<>();

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
        ArrayList<Clothes> result = new ArrayList<>();

        for (Clothes item : items) {
            if (item.getSize() == size) {
                result.add(copyOf(item));
            }
        }

        return result;
    }

    public ArrayList<Clothes> searchBySeason(Season season) {
        ArrayList<Clothes> result = new ArrayList<>();

        for (Clothes item : items) {
            if (item.getSeason() == season) {
                result.add(copyOf(item));
            }
        }

        return result;
    }

    public ArrayList<Clothes> getAll() {
        ArrayList<Clothes> result = new ArrayList<>();

        for (Clothes item : items) {
            result.add(copyOf(item));
        }

        return result;
    }

    public static Clothes copyOf(Clothes item) {
        if (item == null) {
            throw new InvalidProductException("Product cannot be null.");
        }

        Clothes copy;

        if (item instanceof Jeans) {
            Jeans v = (Jeans) item;

            copy = new Jeans(
                    v.getName(), v.getSize(), v.getPrice(),
                    v.getColor(), v.getQuantity(), v.getSeason(),
                    v.getWaistCm(), v.getDenimWeightOz());

        } else if (item instanceof TShirt) {
            TShirt v = (TShirt) item;

            copy = new TShirt(
                    v.getName(), v.getSize(), v.getPrice(),
                    v.getColor(), v.getQuantity(), v.getSeason(),
                    v.getSleeveLengthCm(), v.getPrintText());

        } else if (item instanceof Pants) {
            Pants v = (Pants) item;

            copy = new Pants(
                    v.getName(), v.getSize(), v.getPrice(),
                    v.getColor(), v.getQuantity(), v.getSeason(),
                    v.getWaistCm());

        } else if (item instanceof Shirts) {
            Shirts v = (Shirts) item;

            copy = new Shirts(
                    v.getName(), v.getSize(), v.getPrice(),
                    v.getColor(), v.getQuantity(), v.getSeason(),
                    v.getSleeveLengthCm());

        } else {
            throw new InvalidProductException(
                    "Unsupported product type.");
        }

        copy.restoreUuid(item.getUuid());
        return copy;
    }
}
