package ua.edu.sumdu.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Перевірка валідації, копіювання, статичного лічильника та агрегації. */
class ClothesTest {
    /** Перевіряє виняток сеттера. */
    @Test
    void rejectsNegativePrice() {
        Clothes item = sample();
        assertThrows(IllegalArgumentException.class, () -> item.setPrice(-10));
    }

    /** Перевіряє виняток конструктора. */
    @Test
    void rejectsNullSeason() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clothes("Куртка", 48, 1200, "Чорний", 1, null));
    }

    /** Перевіряє конструктор копіювання та лічильник. */
    @Test
    void copyIncreasesCounterAndPreservesValues() {
        Clothes item = sample();
        int before = Clothes.getCreatedCount();
        Clothes copy = new Clothes(item);
        assertEquals(before + 1, Clothes.getCreatedCount());
        assertEquals(item, copy);
        assertNotSame(item, copy);
    }

    /** Перевіряє агрегацію у класі Wardrobe. */
    @Test
    void wardrobeKeepsExistingReference() {
        Clothes item = sample();
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.add(item);
        assertSame(item, wardrobe.getItems().get(0));
    }

    /** Створює валідний тестовий об'єкт. */
    private Clothes sample() {
        return new Clothes("Куртка", 48, 1200, "Чорний", 1, Season.WINTER);
    }
}
