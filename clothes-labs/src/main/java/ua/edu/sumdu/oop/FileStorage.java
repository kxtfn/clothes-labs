
package ua.edu.sumdu.oop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public final class FileStorage {
    private FileStorage() {
    }

    public static ArrayList<Clothes> load(Path file) {
        ArrayList<Clothes> items = new ArrayList<Clothes>();

        if (!Files.exists(file)) {
            System.out.println(
                    "input.txt not found. Starting with an empty inventory.");
            return items;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                file, StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    items.add(parseLine(line));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Skipping line "
                            + lineNumber + ": " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("File read error: " + ex.getMessage());
        }

        return items;
    }

    public static void save(Path file, List<Clothes> items)
            throws IOException {

        Path absolute = file.toAbsolutePath();
        Path temp = absolute.resolveSibling(
                absolute.getFileName().toString() + ".tmp");

        try (BufferedWriter writer = Files.newBufferedWriter(
                temp,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Clothes item : items) {
                writer.write(serialize(item));
                writer.newLine();
            }
        }

        try {
            Files.move(temp, absolute,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException ex) {
            Files.move(temp, absolute,
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String serialize(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Cannot save a null product.");
        }

        String type = item.getClass().getSimpleName();

        String base = type
                + "|" + encode(item.getName())
                + "|" + item.getSize()
                + "|" + item.getPrice()
                + "|" + encode(item.getColor())
                + "|" + item.getQuantity()
                + "|" + item.getSeason();

        if (item instanceof Jeans) {
            Jeans jeans = (Jeans) item;
            return base + "|" + jeans.getWaistCm()
                    + "|" + jeans.getDenimWeightOz();
        }

        if (item instanceof TShirt) {
            TShirt shirt = (TShirt) item;
            return base + "|" + shirt.getSleeveLengthCm()
                    + "|" + encode(shirt.getPrintText());
        }

        if (item instanceof Pants) {
            return base + "|"
                    + ((Pants) item).getWaistCm();
        }

        if (item instanceof Shirts) {
            return base + "|"
                    + ((Shirts) item).getSleeveLengthCm();
        }

        throw new IllegalArgumentException(
                "Unsupported type: " + type);
    }

    private static Clothes parseLine(String line) {
        String[] p = line.split("\\|", -1);

        if (p.length < 7) {
            throw new IllegalArgumentException("Not enough fields.");
        }

        try {
            String type = p[0];
            String name = decode(p[1]);
            int size = Integer.parseInt(p[2]);
            double price = Double.parseDouble(p[3]);
            String color = decode(p[4]);
            int quantity = Integer.parseInt(p[5]);
            Season season = Season.valueOf(p[6]);

            switch (type) {
                case "Pants":
                    checkCount(p, 8);
                    return new Pants(
                            name, size, price, color,
                            quantity, season,
                            Integer.parseInt(p[7]));

                case "Shirts":
                    checkCount(p, 8);
                    return new Shirts(
                            name, size, price, color,
                            quantity, season,
                            Integer.parseInt(p[7]));

                case "Jeans":
                    checkCount(p, 9);
                    return new Jeans(
                            name, size, price, color,
                            quantity, season,
                            Integer.parseInt(p[7]),
                            Integer.parseInt(p[8]));

                case "TShirt":
                    checkCount(p, 9);
                    return new TShirt(
                            name, size, price, color,
                            quantity, season,
                            Integer.parseInt(p[7]),
                            decode(p[8]));

                default:
                    throw new IllegalArgumentException(
                            "Unknown type: " + type);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException(
                    "Not enough fields.", ex);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid data: " + ex.getMessage(), ex);
        }
    }

    private static void checkCount(String[] values, int expected) {
        if (values.length != expected) {
            throw new IllegalArgumentException(
                    "Expected " + expected + " fields.");
        }
    }

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(
                value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(
                Base64.getDecoder().decode(value),
                StandardCharsets.UTF_8);
    }
}
