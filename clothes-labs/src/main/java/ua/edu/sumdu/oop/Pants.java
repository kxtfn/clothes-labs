package ua.edu.sumdu.oop;

import java.util.Objects;

/** Штани із додатковим параметром обхвату талії. */
public class Pants extends Clothes {
    private int waistCm;

    /** Створює штани. */
    public Pants(String name, int size, double price, String color, int quantity,
                 Season season, int waistCm) {
        super(name, size, price, color, quantity, season);
        setWaistCm(waistCm);
    }

    /** @return обхват талії у сантиметрах */
    public int getWaistCm() { return waistCm; }
    /** @param waistCm обхват талії від 50 до 140 см */
    public void setWaistCm(int waistCm) {
        if (waistCm < 50 || waistCm > 140)
            throw new IllegalArgumentException("Талія має бути від 50 до 140 см.");
        this.waistCm = waistCm;
    }

    /** @return текст із характеристиками штанів */
    @Override
    public String toString() { return super.toString() + ", Pants{waistCm=" + waistCm + "}"; }

    /** @return рівність двох об'єктів Pants */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        Pants pants = (Pants) other;
        return waistCm == pants.waistCm;
    }

    /** @return хеш з урахуванням талії */
    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), waistCm); }
}
