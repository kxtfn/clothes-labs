package ua.edu.sumdu.oop;

import java.util.Objects;

/** Футболка, що успадковує властивості сорочки та має напис. */
public class TShirt extends Shirts {
    private String printText;

    /** Створює футболку з текстом принта. */
    public TShirt(String name, int size, double price, String color, int quantity,
                  Season season, int sleeveLengthCm, String printText) {
        super(name, size, price, color, quantity, season, sleeveLengthCm);
        setPrintText(printText);
    }

    /** @return текст принта */
    public String getPrintText() { return printText; }
    /** @param printText непорожній текст (або 'Без принта') */
    public void setPrintText(String printText) {
        if (printText == null || printText.trim().isEmpty())
            throw new IllegalArgumentException("Напис не може бути порожнім.");
        this.printText = printText.trim();
    }

    /** @return опис футболки */
    @Override
    public String toString() {
        return super.toString() + ", TShirt{printText='" + printText + "'}";
    }

    /** @return рівність двох футболок */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        TShirt shirt = (TShirt) other;
        return Objects.equals(printText, shirt.printText);
    }

    /** @return хеш із текстом принта */
    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), printText); }
}
