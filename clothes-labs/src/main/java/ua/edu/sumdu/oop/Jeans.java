package ua.edu.sumdu.oop;

import java.util.Objects;

/** Джинси, що успадковують властивості штанів і мають щільність деніму. */
public class Jeans extends Pants {
    private int denimWeightOz;

    /** Створює джинси з вагою деніму в унціях на ярд. */
    public Jeans(String name, int size, double price, String color, int quantity,
                 Season season, int waistCm, int denimWeightOz) {
        super(name, size, price, color, quantity, season, waistCm);
        setDenimWeightOz(denimWeightOz);
    }

    /** @return щільність тканини (oz) */
    public int getDenimWeightOz() { return denimWeightOz; }
    /** @param denimWeightOz значення від 5 до 30 */
    public void setDenimWeightOz(int denimWeightOz) {
        if (denimWeightOz < 5 || denimWeightOz > 30)
            throw new IllegalArgumentException("Щільність деніму має бути від 5 до 30 oz.");
        this.denimWeightOz = denimWeightOz;
    }

    /** @return опис джинсів */
    @Override
    public String toString() {
        return super.toString() + ", Jeans{denimWeightOz=" + denimWeightOz + "}";
    }

    /** @return рівність двох джинсів */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        Jeans jeans = (Jeans) other;
        return denimWeightOz == jeans.denimWeightOz;
    }

    /** @return хеш із щільністю тканини */
    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), denimWeightOz); }
}
