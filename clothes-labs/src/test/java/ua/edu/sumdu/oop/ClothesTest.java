package ua.edu.sumdu.oop;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/** Перевіряє валідацію та поліморфізм ієрархії. */
class ClothesTest {
    /** Перевіряє виняток конструктора базового класу. */
    @Test
    void invalidConstructorThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Clothes("", 48, 100, "Білий", 1, Season.SUMMER));
    }

    /** Перевіряє виняток у сеттері похідного класу. */
    @Test
    void invalidDerivedSetterThrows() {
        Pants item = new Pants("Штани", 48, 600, "Сірий", 1, Season.AUTUMN, 80);
        assertThrows(IllegalArgumentException.class, () -> item.setWaistCm(10));
    }

    /** Перевіряє одну колекцію об'єктів різних підкласів. */
    @Test
    void polymorphicCollectionContainsThreeTypes() {
        ArrayList<Clothes> items = new ArrayList<Clothes>();
        items.add(new Clothes("Одяг", 46, 100, "Білий", 1, Season.SUMMER));
        items.add(new Pants("Штани", 46, 200, "Чорний", 1, Season.WINTER, 80));
        items.add(new Shirts("Сорочка", 46, 300, "Синій", 1, Season.SPRING, 60));
        assertEquals(3, items.size());
        assertTrue(items.get(1).toString().contains("waistCm"));
        assertTrue(items.get(2).toString().contains("sleeveLengthCm"));
    }
}
