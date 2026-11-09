
package ua.edu.sumdu.oop;

import java.util.Objects;

public class Shirts extends Clothes {
    private int sleeveLengthCm;

    public Shirts(String name, int size, double price,
                  String color, int quantity, Season season,
                  int sleeveLengthCm) {
        super(name, size, price, color, quantity, season);
        setSleeveLengthCm(sleeveLengthCm);
    }

    public int getSleeveLengthCm() {
        return sleeveLengthCm;
    }

    public void setSleeveLengthCm(int sleeveLengthCm) {
        if (sleeveLengthCm < 1 || sleeveLengthCm > 120) {
            throw new IllegalArgumentException(
                    "Sleeve length must be between 1 and 120 cm.");
        }
        this.sleeveLengthCm = sleeveLengthCm;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Shirts{sleeveLengthCm="
                + sleeveLengthCm + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Shirts shirts = (Shirts) other;
        return sleeveLengthCm == shirts.sleeveLengthCm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sleeveLengthCm);
    }
}
