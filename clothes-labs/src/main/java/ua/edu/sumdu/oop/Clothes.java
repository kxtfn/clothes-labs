package ua.edu.sumdu.oop;

import java.util.Objects;

/** Предмет одягу із перевіркою коректності властивостей. */
public class Clothes {
    private String name;
    private int size;
    private double price;
    private String color;
    private int quantity;

    /**
     * Створює предмет одягу та перевіряє всі поля.
     * @throws IllegalArgumentException якщо дані некоректні
     */
    public Clothes(String name, int size, double price, String color, int quantity) {
        setName(name);
        setSize(size);
        setPrice(price);
        setColor(color);
        setQuantity(quantity);
    }

    /** @return назва */
    public String getName() { return name; }
    /** @param name непорожня назва */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Назва не може бути порожньою.");
        this.name = name.trim();
    }
    /** @return розмір */
    public int getSize() { return size; }
    /** @param size розмір від 32 до 70 */
    public void setSize(int size) {
        if (size < 32 || size > 70)
            throw new IllegalArgumentException("Розмір має бути від 32 до 70.");
        this.size = size;
    }
    /** @return ціна */
    public double getPrice() { return price; }
    /** @param price скінченна додатна ціна */
    public void setPrice(double price) {
        if (!Double.isFinite(price) || price <= 0)
            throw new IllegalArgumentException("Ціна має бути додатним числом.");
        this.price = price;
    }
    /** @return колір */
    public String getColor() { return color; }
    /** @param color непорожній колір */
    public void setColor(String color) {
        if (color == null || color.trim().isEmpty())
            throw new IllegalArgumentException("Колір не може бути порожнім.");
        this.color = color.trim();
    }
    /** @return кількість */
    public int getQuantity() { return quantity; }
    /** @param quantity кількість не менша нуля */
    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Кількість не може бути від'ємною.");
        this.quantity = quantity;
    }

    /** @return інформація про одяг */
    @Override
    public String toString() {
        return "Clothes{name='" + name + "', size=" + size + ", price=" + price
                + ", color='" + color + "', quantity=" + quantity + "}";
    }

    /** @return true, якщо об'єкти однакові за полями */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Clothes)) return false;
        Clothes that = (Clothes) other;
        return size == that.size && Double.compare(price, that.price) == 0
                && quantity == that.quantity && Objects.equals(name, that.name)
                && Objects.equals(color, that.color);
    }

    /** @return хеш за полями об'єкта */
    @Override
    public int hashCode() { return Objects.hash(name, size, price, color, quantity); }
}
