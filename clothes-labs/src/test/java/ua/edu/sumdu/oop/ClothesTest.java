package ua.edu.sumdu.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Автотести перевірки даних класу Clothes. */
class ClothesTest {
    /** Перевіряє виняток під час помилкового значення в сеттері. */
    @Test
    void rejectsNegativeQuantityInSetter() {
        Clothes item = new Clothes("Куртка", 48, 1500.0, "Чорний", 3);
        assertThrows(IllegalArgumentException.class, () -> item.setQuantity(-1));
    }

    /** Перевіряє виняток під час помилкового створення об'єкта. */
    @Test
    void rejectsEmptyNameInConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clothes("", 48, 1500.0, "Чорний", 3));
    }

    /** Перевіряє нормальне створення та рівність об'єктів. */
    @Test
    void acceptsValidClothesAndEquals() {
        Clothes first = new Clothes("Куртка", 48, 1500.0, "Чорний", 3);
        Clothes second = new Clothes("Куртка", 48, 1500.0, "Чорний", 3);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
