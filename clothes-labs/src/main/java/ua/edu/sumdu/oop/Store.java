package ua.edu.sumdu.oop;
import java.util.ArrayList;
import java.util.UUID;
public class Store {
    private final String name;
    private final ArrayList<Clothes> items = new ArrayList<Clothes>();
    public Store(String name) { this.name = name; }
    public String getName() { return name; }
    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null || quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        for (Clothes item : items) {
            if (sameProduct(item, cl)) {
                item.setQuantity(Math.addExact(item.getQuantity(), quantity)); return;
            }
        }
        cl.setQuantity(quantity);
        items.add(cl);
    }
    private boolean sameProduct(Clothes a, Clothes b) {
        if (a.getClass() != b.getClass()) return false;
        Clothes first = copyOf(a); Clothes second = copyOf(b);
        first.setQuantity(0); second.setQuantity(0);
        return first.equals(second);
    }
    public ArrayList<Clothes> getAll() { return new ArrayList<Clothes>(items); }
    public ArrayList<Clothes> searchByName(String name) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();
        for (Clothes cl : items) if (cl.getName().equalsIgnoreCase(name)) result.add(cl);
        return result;
    }
    public ArrayList<Clothes> searchBySize(int size) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();
        for (Clothes cl : items) if (cl.getSize() == size) result.add(cl);
        return result;
    }
    public ArrayList<Clothes> searchBySeason(Season season) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();
        for (Clothes cl : items) if (cl.getSeason() == season) result.add(cl);
        return result;
    }

    public boolean update(Clothes existingObject, Clothes newObject) {
        if (newObject == null) throw new IllegalArgumentException("Replacement product is missing.");
        for (int i = 0; i < items.size(); i++) {
            Clothes current = items.get(i);
            if (current == existingObject || current.equals(existingObject)) {
                
                items.set(i, newObject); return true;
            }
        }
        throw new IllegalArgumentException("Product to update was not found.");
    }
    public boolean delete(Clothes existingObject) {
        for (int i = 0; i < items.size(); i++) {
            Clothes current = items.get(i);
            if (current == existingObject || current.equals(existingObject)) {
                items.remove(i); return true;
            }
        }
        throw new IllegalArgumentException("Product to delete was not found.");
    }
    public static Clothes copyOf(Clothes a) {
        Clothes copy;
        if (a instanceof Jeans) {
            Jeans v = (Jeans) a;
            copy = new Jeans(v.getName(),v.getSize(),v.getPrice(),v.getColor(),v.getQuantity(),v.getSeason(),v.getWaistCm(),v.getDenimWeightOz());
        } else if (a instanceof TShirt) {
            TShirt v = (TShirt) a;
            copy = new TShirt(v.getName(),v.getSize(),v.getPrice(),v.getColor(),v.getQuantity(),v.getSeason(),v.getSleeveLengthCm(),v.getPrintText());
        } else if (a instanceof Pants) {
            Pants v = (Pants) a;
            copy = new Pants(v.getName(),v.getSize(),v.getPrice(),v.getColor(),v.getQuantity(),v.getSeason(),v.getWaistCm());
        } else if (a instanceof Shirts) {
            Shirts v = (Shirts) a;
            copy = new Shirts(v.getName(),v.getSize(),v.getPrice(),v.getColor(),v.getQuantity(),v.getSeason(),v.getSleeveLengthCm());
        } else { throw new IllegalArgumentException("Unknown product type."); }
        
        return copy;
    }
}
