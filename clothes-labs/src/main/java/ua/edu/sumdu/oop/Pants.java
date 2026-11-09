
package ua.edu.sumdu.oop;

import java.util.Objects;

public class Pants extends Clothes {
    private int waistCm;

    public Pants(String name, int size, double price,
                 String color, int quantity, Season season,
                 int waistCm) {
        super(name, size, price, color, quantity, season);
        setWaistCm(waistCm);
    }

    public int getWaistCm() {
        return waistCm;
    }

    public void setWaistCm(int waistCm) {
        if (waistCm < 50 || waistCm > 140) {
            throw new IllegalArgumentException(
                    "Waist must be between 50 and 140 cm.");
        }
        this.waistCm = waistCm;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Pants{waistCm=" + waistCm + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Pants pants = (Pants) other;
        return waistCm == pants.waistCm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), waistCm);
    }
}
