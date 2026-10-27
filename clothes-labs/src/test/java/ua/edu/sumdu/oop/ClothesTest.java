package ua.edu.sumdu.oop;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/** Автотести п'яти класів ієрархії. */
class ClothesTest {
    /** Перевіряє валідацію конструктора. */
    @Test
    void rejectsInvalidConstructor() {
        assertThrows(IllegalArgumentException.class,
            () -> new Clothes("", 44, 50, "Сірий", 1, Season.SPRING));
    }

    /** Перевіряє сеттер джинсів. */
    @Test
    void rejectsInvalidDenimWeight() {
        Jeans jeans = new Jeans("Джинси", 46, 1200, "Синій", 1, Season.AUTUMN, 80, 12);
        assertThrows(IllegalArgumentException.class, () -> jeans.setDenimWeightOz(0));
    }

    /** Перевіряє поліморфізм ієрархії. */
    @Test
    void storesFiveClassTypesInOneList() {
        ArrayList<Clothes> items = new ArrayList<Clothes>();
        items.add(new Clothes("Одяг", 46, 100, "Білий", 1, Season.SUMMER));
        items.add(new Pants("Штани", 46, 200, "Чорний", 1, Season.WINTER, 80));
        items.add(new Shirts("Сорочка", 46, 300, "Синій", 1, Season.SPRING, 60));
        items.add(new Jeans("Джинси", 46, 400, "Синій", 1, Season.AUTUMN, 80, 12));
        items.add(new TShirt("Футболка", 46, 500, "Білий", 1, Season.SUMMER, 20, "Hello"));
        assertEquals(5, items.size());
        assertTrue(items.get(3).toString().contains("denimWeightOz"));
        assertTrue(items.get(4).toString().contains("Hello"));
    }
}
