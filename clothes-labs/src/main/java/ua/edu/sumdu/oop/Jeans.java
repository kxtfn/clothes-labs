
package ua.edu.sumdu.oop;

import java.util.Objects;

public class Jeans extends Pants {
    private int denimWeightOz;

    public Jeans(String name, int size, double price,
                 String color, int quantity, Season season,
                 int waistCm, int denimWeightOz) {
        super(name, size, price, color, quantity, season, waistCm);
        setDenimWeightOz(denimWeightOz);
    }

    public int getDenimWeightOz() {
        return denimWeightOz;
    }

    public void setDenimWeightOz(int denimWeightOz) {
        if (denimWeightOz < 5 || denimWeightOz > 30) {
            throw new IllegalArgumentException(
                    "Denim weight must be between 5 and 30 oz.");
        }
        this.denimWeightOz = denimWeightOz;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Jeans{denimWeightOz="
                + denimWeightOz + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Jeans jeans = (Jeans) other;
        return denimWeightOz == jeans.denimWeightOz;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), denimWeightOz);
    }
}
