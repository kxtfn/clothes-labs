package ua.edu.sumdu.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Гардероб агрегує вже створені предмети одягу. */
public class Wardrobe {
    private final ArrayList<Clothes> clothes = new ArrayList<Clothes>();

    /** Додає посилання на існуючий одяг, не створюючи нового. */
    public void add(Clothes item) {
        if (item == null) throw new IllegalArgumentException("Немає предмета одягу.");
        clothes.add(item);
    }

    /** @return незмінюваний вигляд колекції предметів */
    public List<Clothes> getItems() { return Collections.unmodifiableList(clothes); }

    /** @return кількість предметів у гардеробі */
    public int size() { return clothes.size(); }
}
