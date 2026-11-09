
package ua.edu.sumdu.oop;

import java.util.Objects;

public abstract class Clothes implements Comparable<Clothes> {
    private String name;
    private int size;
    private double price;
    private String color;
    private int quantity;
    private Season season;

    public Clothes(String name, int size, double price,
                   String color, int quantity, Season season) {
        setName(name);
        setSize(size);
        setPrice(price);
        setColor(color);
        setQuantity(quantity);
        setSeason(season);
    }

    public Clothes(Clothes original) {
        this(Objects.requireNonNull(original).name,
                original.size, original.price, original.color,
                original.quantity, original.season);
    }

    @Override
    public int compareTo(Clothes other) {
        return name.compareToIgnoreCase(other.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 32 || size > 70) {
            throw new IllegalArgumentException(
                    "Size must be between 32 and 70.");
        }
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (!Double.isFinite(price) || price <= 0) {
            throw new IllegalArgumentException(
                    "Price must be positive.");
        }
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Color cannot be empty.");
        }
        this.color = color.trim();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season is required.");
        }
        this.season = season;
    }

    @Override
    public String toString() {
        return "Clothes{name='" + name
                + "', size=" + size
                + ", price=" + price
                + ", color='" + color
                + "', quantity=" + quantity
                + ", season=" + season + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Clothes that = (Clothes) other;

        return size == that.size
                && Double.compare(price, that.price) == 0
                && quantity == that.quantity
                && Objects.equals(name, that.name)
                && Objects.equals(color, that.color)
                && season == that.season;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                name, size, price, color, quantity, season);
    }
}
