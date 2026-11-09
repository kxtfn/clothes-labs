
package ua.edu.sumdu.oop;

import java.util.Objects;

public class TShirt extends Shirts {
    private String printText;

    public TShirt(String name, int size, double price,
                  String color, int quantity, Season season,
                  int sleeveLengthCm, String printText) {
        super(name, size, price, color, quantity,
                season, sleeveLengthCm);
        setPrintText(printText);
    }

    public String getPrintText() {
        return printText;
    }

    public void setPrintText(String printText) {
        if (printText == null || printText.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Print text cannot be empty.");
        }
        this.printText = printText.trim();
    }

    @Override
    public String toString() {
        return super.toString()
                + ", TShirt{printText='"
                + printText + "'}";
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        TShirt shirt = (TShirt) other;
        return Objects.equals(printText, shirt.printText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), printText);
    }
}
