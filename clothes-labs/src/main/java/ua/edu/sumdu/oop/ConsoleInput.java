package ua.edu.sumdu.oop;
import java.util.Scanner;
import java.util.Locale;
public final class ConsoleInput {
    private ConsoleInput() { }
    public static String readText(Scanner scanner, String prompt) {
        while (true) { System.out.print(prompt); String text = scanner.nextLine().trim();
            if (!text.isEmpty()) return text; System.out.println("Error: input cannot be empty."); }
    }
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) { System.out.print(prompt); String line = scanner.nextLine().trim();
            try { int n = Integer.parseInt(line); if (n >= min && n <= max) return n; }
            catch (NumberFormatException ignored) { }
            System.out.println("Error: enter a whole number from " + min + " to " + max + "."); }
    }
    public static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) { System.out.print(prompt); String line = scanner.nextLine().trim().replace(',', '.');
            try { double n = Double.parseDouble(line); if (Double.isFinite(n) && n > 0) return n; }
            catch (NumberFormatException ignored) { }
            System.out.println("Error: enter a positive number."); }
    }
    public static Season readSeason(Scanner scanner) {
        while (true) { String text = readText(scanner, "Season (SPRING/SUMMER/AUTUMN/WINTER): ");
            try { return Season.valueOf(text.toUpperCase(Locale.ROOT)); }
            catch (IllegalArgumentException ignored) { System.out.println("Error: invalid season."); } }
    }
}
