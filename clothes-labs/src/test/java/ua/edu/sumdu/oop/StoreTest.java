package ua.edu.sumdu.oop;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
public class StoreTest {
    private Pants pants(String name, int quantity) {
        return new Pants(name, 46, 80, "Blue", quantity, Season.AUTUMN, 80);
    }
    @Test void searchDoesNotChangeInventory() {
        Store store = new Store("Test"); store.addNewClothes(pants("Chinos", 2), 2);
        store.addNewClothes(pants("Cargo", 1), 1);
        ArrayList<Clothes> before = store.getAll();
        assertEquals(1, store.searchByName("CHINOS").size());
        assertEquals(2, store.searchBySize(46).size());
        assertEquals(2, store.searchBySeason(Season.AUTUMN).size());
        assertEquals(0, store.searchByName("Unknown").size());
        assertEquals(before, store.getAll());
    }
    @Test void duplicateIncreasesQuantity() {
        Store store = new Store("Test"); store.addNewClothes(pants("Chinos", 2), 2);
        store.addNewClothes(pants("Chinos", 3), 3);
        assertEquals(1, store.getAll().size()); assertEquals(5, store.getAll().get(0).getQuantity());
    }
}
