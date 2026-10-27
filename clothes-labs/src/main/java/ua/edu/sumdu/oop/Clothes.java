package ua.edu.sumdu.oop;

import java.util.Objects;

/** Одяг з назвою, розміром та ціною. */
public class Clothes {
    private String name;
    private int size;
    private double price;

    /** Створює предмет одягу. */
    public Clothes(String name, int size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    /** @return назва */
    public String getName() { return name; }
    /** @param name нова назва */
    public void setName(String name) { this.name = name; }
    /** @return розмір */
    public int getSize() { return size; }
    /** @param size новий розмір */
    public void setSize(int size) { this.size = size; }
    /** @return ціна */
    public double getPrice() { return price; }
    /** @param price нова ціна */
    public void setPrice(double price) { this.price = price; }

    /** @return текстове представлення об'єкта */
    @Override
    public String toString() {
        return "Clothes{name='" + name + "', size=" + size + ", price=" + price + "}";
    }

    /** Порівнює всі поля двох предметів одягу. */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Clothes)) return false;
        Clothes that = (Clothes) other;
        return size == that.size && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name);
    }

    /** @return хеш за всіма полями */
    @Override
    public int hashCode() { return Objects.hash(name, size, price); }
}
