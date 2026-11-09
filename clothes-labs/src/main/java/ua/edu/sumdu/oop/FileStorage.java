
package ua.edu.sumdu.oop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public final class FileStorage {

    private FileStorage() {
    }

    public static ArrayList<Clothes> load(Path file) {
        ArrayList<Clothes> result = new ArrayList<Clothes>();

        if (!Files.exists(file)) {
            System.out.println("Input file not found.");
            return result;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                file, StandardCharsets.UTF_8)) {

            String line;
            int number = 0;

            while ((line = reader.readLine()) != null) {
                number++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    result.add(parse(line));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid line " + number
                            + ": " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("Read error: " + ex.getMessage());
        }

        return result;
    }

    public static void save(Path file, List<Clothes> items)
            throws IOException {

        Path absolute = file.toAbsolutePath();
        Path temp = absolute.resolveSibling(
                absolute.getFileName().toString() + ".tmp");

        try (BufferedWriter writer = Files.newBufferedWriter(
                temp, StandardCharsets.UTF_8)) {

            for (Clothes item : items) {
                writer.write(serialize(item));
                writer.newLine();
            }
        }

        try {
            Files.move(temp, absolute,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ex) {
            Files.move(temp, absolute,
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String serialize(Clothes item) {
        String base = item.getClass().getSimpleName()
                + "|" + encode(item.getName())
                + "|" + item.getSize()
                + "|" + item.getPrice()
                + "|" + encode(item.getColor())
                + "|" + item.getQuantity()
                + "|" + item.getSeason();

        String extra;

        if (item instanceof Jeans) {
            Jeans v = (Jeans) item;
            extra = "|" + v.getWaistCm()
                    + "|" + v.getDenimWeightOz();
        } else if (item instanceof TShirt) {
            TShirt v = (TShirt) item;
            extra = "|" + v.getSleeveLengthCm()
                    + "|" + encode(v.getPrintText());
        } else if (item instanceof Pants) {
            extra = "|" + ((Pants) item).getWaistCm();
        } else if (item instanceof Shirts) {
            extra = "|" + ((Shirts) item).getSleeveLengthCm();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported product type.");
        }

        return base + extra + "|" + item.getUuid();
    }

    private static Clothes parse(String line) {
        String[] p = line.split("\\|", -1);

        if (p.length < 8) {
            throw new IllegalArgumentException("Missing fields.");
        }

        String type = p[0];
        String name = decode(p[1]);
        int size = Integer.parseInt(p[2]);
        double price = Double.parseDouble(p[3]);
        String color = decode(p[4]);
        int quantity = Integer.parseInt(p[5]);
        Season season = Season.valueOf(p[6]);

        Clothes item;
        int oldFieldCount;

        switch (type) {
            case "Pants":
                oldFieldCount = 8;
                item = new Pants(name, size, price, color,
                        quantity, season, Integer.parseInt(p[7]));
                break;

            case "Shirts":
                oldFieldCount = 8;
                item = new Shirts(name, size, price, color,
                        quantity, season, Integer.parseInt(p[7]));
                break;

            case "Jeans":
                if (p.length < 9) {
                    throw new IllegalArgumentException("Missing fields.");
                }
                oldFieldCount = 9;
                item = new Jeans(name, size, price, color,
                        quantity, season, Integer.parseInt(p[7]),
                        Integer.parseInt(p[8]));
                break;

            case "TShirt":
                if (p.length < 9) {
                    throw new IllegalArgumentException("Missing fields.");
                }
                oldFieldCount = 9;
                item = new TShirt(name, size, price, color,
                        quantity, season, Integer.parseInt(p[7]),
                        decode(p[8]));
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown product type: " + type);
        }

        if (p.length == oldFieldCount + 1) {
            item.restoreUuid(UUID.fromString(p[oldFieldCount]));
        } else if (p.length != oldFieldCount) {
            throw new IllegalArgumentException("Invalid field count.");
        }

        return item;
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
