package ua.edu.sumdu.oop;

import java.util.Objects;

/** Сорочка з довжиною рукава. */
public class Shirts extends Clothes {
    private int sleeveLengthCm;

    /** Створює сорочку. */
    public Shirts(String name, int size, double price, String color, int quantity,
                  Season season, int sleeveLengthCm) {
        super(name, size, price, color, quantity, season);
        setSleeveLengthCm(sleeveLengthCm);
    }

    /** @return довжина рукава в сантиметрах */
    public int getSleeveLengthCm() { return sleeveLengthCm; }
    /** @param sleeveLengthCm довжина рукава від 1 до 120 см */
    public void setSleeveLengthCm(int sleeveLengthCm) {
        if (sleeveLengthCm < 1 || sleeveLengthCm > 120)
            throw new IllegalArgumentException("Довжина рукава має бути від 1 до 120 см.");
        this.sleeveLengthCm = sleeveLengthCm;
    }

    /** @return текст із характеристиками сорочки */
    @Override
    public String toString() {
        return super.toString() + ", Shirts{sleeveLengthCm=" + sleeveLengthCm + "}";
    }

    /** @return рівність двох об'єктів Shirts */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        Shirts shirts = (Shirts) other;
        return sleeveLengthCm == shirts.sleeveLengthCm;
    }

    /** @return хеш з урахуванням довжини рукава */
    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), sleeveLengthCm); }
}
