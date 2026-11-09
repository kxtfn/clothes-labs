
package ua.edu.sumdu.oop;

import java.util.Objects;
import java.util.UUID;

public abstract class Clothes
        implements Comparable<Clothes>, Identifiable {

    private UUID uuid;
    private String name;
    private int size;
    private double price;
    private String color;
    private int quantity;
    private Season season;

    public Clothes(String name, int size, double price,
                   String color, int quantity, Season season) {
        this.uuid = UUID.randomUUID();
        setName(name);
        setSize(size);
        setPrice(price);
        setColor(color);
        setQuantity(quantity);
        setSeason(season);
    }

    public Clothes(Clothes original) {
        this(original.getName(), original.getSize(),
                original.getPrice(), original.getColor(),
                original.getQuantity(), original.getSeason());
        this.uuid = original.getUuid();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    void restoreUuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
    }

    @Override
    public int compareTo(Clothes other) {
        int result = name.compareToIgnoreCase(other.name);
        if (result != 0) {
            return result;
        }
        return uuid.compareTo(other.uuid);
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
            throw new IllegalArgumentException("Color cannot be empty.");
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
        this.season = Objects.requireNonNull(season);
    }

    public String toShortString() {
        return getClass().getSimpleName() + ": " + name
                + " | UUID: " + uuid;
    }

    @Override
    public String toString() {
        return "Type: " + getClass().getSimpleName()
                + " | UUID: " + uuid
                + " | Name: " + name
                + " | Size: " + size
                + " | Price: " + price
                + " | Color: " + color
                + " | Quantity: " + quantity
                + " | Season: " + season;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Clothes item = (Clothes) other;
        return size == item.size
                && Double.compare(price, item.price) == 0
                && quantity == item.quantity
                && name.equals(item.name)
                && color.equals(item.color)
                && season == item.season;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, size,
                price, color, quantity, season);
    }
}
