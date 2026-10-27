package ua.edu.sumdu.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/** Тести повноти збереження та обробки пошкоджених рядків. */
class FileStorageTest {
    /** Зворотне зчитування зберігає всі типи та значення полів. */
    @Test
    void roundTripPreservesAllFiveTypes(@TempDir Path directory) throws Exception {
        Path file = directory.resolve("input.txt");
        ArrayList<Clothes> original = new ArrayList<Clothes>();
        original.add(new Clothes("Костюм", 46, 999, "Сірий", 2, Season.AUTUMN));
        original.add(new Pants("Штани", 48, 700, "Чорний", 1, Season.WINTER, 82));
        original.add(new Shirts("Сорочка", 46, 450, "Білий", 3, Season.SPRING, 64));
        original.add(new Jeans("Джинси", 48, 1500, "Синій", 2, Season.AUTUMN, 84, 14));
        original.add(new TShirt("Футболка | тест", 44, 200, "Білий", 4,
                Season.SUMMER, 22, "Принт | UTF-8"));
        FileStorage.save(file, original);
        ArrayList<Clothes> restored = FileStorage.load(file);
        assertEquals(original, restored);
        assertTrue(restored.get(3) instanceof Jeans);
        assertTrue(restored.get(4) instanceof TShirt);
    }

    /** Пошкоджені рядки пропускаються, коректні не губляться. */
    @Test
    void skipsInvalidLines(@TempDir Path directory) throws Exception {
        Path file = directory.resolve("input.txt");
        ArrayList<Clothes> sample = new ArrayList<Clothes>();
        sample.add(new Clothes("Куртка", 48, 800, "Сірий", 1, Season.WINTER));
        FileStorage.save(file, sample);
        Files.writeString(file, "Unknown|broken\n" + Files.readString(file));
        ArrayList<Clothes> result = FileStorage.load(file);
        assertEquals(1, result.size());
    }
}
