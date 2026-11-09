package ua.edu.sumdu.oop;

import java.util.Objects;



/** Предмет одягу з перевіркою полів і характеристикою сезонності. */
public abstract class Clothes implements Comparable<Clothes> {
    
    private String name;
    private int size;
    private double price;
    private String color;
    private int quantity;
    private Season season;


    /** Створює предмет одягу з коректними властивостями. */
    public Clothes(String name, int size, double price, String color,
                   int quantity, Season season) {
        setName(name);
        setSize(size);
        setPrice(price);
        setColor(color);
        setQuantity(quantity);
        setSeason(season);

    }

    /** Конструктор копіювання всіх характеристик. */
    public Clothes(Clothes original) {
        this(Objects.requireNonNull(original, "Об'єкт для копіювання відсутній.").name,
             original.size, original.price, original.color, original.quantity, original.season);
    }



    public int compareTo(Clothes other) { int c = name.compareToIgnoreCase(other.name); return c; }
    /** @return назва */
    public String getName() { return name; }
    /** @param name непорожня назва */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }
    /** @return розмір */
    public int getSize() { return size; }
    /** @param size розмір від 32 to 70 */
    public void setSize(int size) {
        if (size < 32 || size > 70)
            throw new IllegalArgumentException("Size must be between 32 and 70.");
        this.size = size;
    }
    /** @return ціна */
    public double getPrice() { return price; }
    /** @param price додатна скінченна ціна */
    public void setPrice(double price) {
        if (!Double.isFinite(price) || price <= 0)
            throw new IllegalArgumentException("Price must be positive.");
        this.price = price;
    }
    /** @return колір */
    public String getColor() { return color; }
    /** @param color непорожній колір */
    public void setColor(String color) {
        if (color == null || color.trim().isEmpty())
            throw new IllegalArgumentException("Color cannot be empty.");
        this.color = color.trim();
    }
    /** @return кількість */
    public int getQuantity() { return quantity; }
    /** @param quantity невід'ємна кількість */
    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity = quantity;
    }
    /** @return пора року */
    public Season getSeason() { return season; }
    /** @param season ненульова пора року */
    public void setSeason(Season season) {
        if (season == null)
            throw new IllegalArgumentException("Season is required.");
        this.season = season;
    }



    /** @return дані про предмет одягу */
    @Override
    public String toString() {
        return "Clothes{name='" + name + "', size=" + size + ", price=" + price
                + ", color='" + color + "', quantity=" + quantity
                + ", season=" + season + "}";
    }

    /** Порівнює об'єкти одного класу за усіма полями. */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Clothes that = (Clothes) other;
        return size == that.size && Double.compare(price, that.price) == 0
                && quantity == that.quantity && Objects.equals(name, that.name)
                && Objects.equals(color, that.color) && season == that.season;
    }

    /** @return хеш за усіма полями */
    @Override
    public int hashCode() { return Objects.hash(name, size, price, color, quantity, season); }
}
