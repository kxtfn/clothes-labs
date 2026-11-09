package ua.edu.sumdu.oop;

import java.util.UUID;

public final class Lab18Test {

    private static int passed = 0;
    private static int failed = 0;

    private Lab18Test() {
    }

    public static void main(String[] args) {
        run("Add and find product", () -> {
            Store store = new Store("Test Store");
            Pants pants = makePants("Classic", 79.99, 5);

            store.addNewClothes(pants, 5);

            Clothes found = store.searchByUuid(
                    pants.getUuid().toString());

            check(found != null, "Product was not found");
            check(found.getName().equals("Classic"),
                    "Wrong product name");
        });

        run("Update price and preserve UUID", () -> {
            Store store = new Store("Test Store");
            Pants pants = makePants("Classic", 79.99, 5);
            store.addNewClothes(pants, 5);

            String uuid = pants.getUuid().toString();
            store.updateProduct(uuid, "price", "99.50");

            Clothes updated = store.searchByUuid(uuid);

            check(updated != null, "Product disappeared");
            check(updated.getPrice() == 99.50,
                    "Price was not updated");
            check(updated.getUuid().equals(pants.getUuid()),
                    "UUID changed");
        });

        run("Delete product", () -> {
            Store store = new Store("Test Store");
            Pants pants = makePants("Classic", 79.99, 5);
            store.addNewClothes(pants, 5);

            String uuid = pants.getUuid().toString();
            store.deleteProduct(uuid);

            check(store.getProductCount() == 0,
                    "Product was not deleted");
            check(store.searchByUuid(uuid) == null,
                    "Deleted product can still be found");
        });

        run("Invalid UUID", () -> {
            Store store = new Store("Test Store");

            expectException(InvalidProductException.class,
                    () -> store.searchByUuid("wrong-uuid"));
        });

        run("Missing product on update", () -> {
            Store store = new Store("Test Store");

            expectException(ProductNotFoundException.class,
                    () -> store.updateProduct(
                            UUID.randomUUID().toString(),
                            "price", "50"));
        });

        run("Missing product on delete", () -> {
            Store store = new Store("Test Store");

            expectException(ProductNotFoundException.class,
                    () -> store.deleteProduct(
                            UUID.randomUUID().toString()));
        });

        run("Invalid quantity", () -> {
            Store store = new Store("Test Store");
            Pants pants = makePants("Classic", 79.99, 5);

            expectException(InvalidProductException.class,
                    () -> store.addNewClothes(pants, -3));
        });

        run("Invalid price update", () -> {
            Store store = new Store("Test Store");
            Pants pants = makePants("Classic", 79.99, 5);
            store.addNewClothes(pants, 5);

            String uuid = pants.getUuid().toString();

            expectException(InvalidProductException.class,
                    () -> store.updateProduct(
                            uuid, "price", "-10"));

            check(store.searchByUuid(uuid).getPrice() == 79.99,
                    "Original price changed after failed update");
        });

        run("Duplicate product update", () -> {
            Store store = new Store("Test Store");

            Pants first = makePants("First", 79.99, 5);
            Pants second = makePants("Second", 79.99, 5);

            store.addNewClothes(first, 5);
            store.addNewClothes(second, 5);

            expectException(DuplicateProductException.class,
                    () -> store.updateProduct(
                            second.getUuid().toString(),
                            "name", "First"));

            check(store.getProductCount() == 2,
                    "Inventory changed after failed update");
        });

        run("Increase stock", () -> {
            Store store = new Store("Test Store");
            Pants pants = makePants("Classic", 79.99, 5);
            store.addNewClothes(pants, 5);

            store.increaseStock(0, 3);

            check(store.getTotalQuantity() == 8,
                    "Wrong total quantity");
        });

        System.out.println("\n========== TEST SUMMARY ==========");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static Pants makePants(
            String name, double price, int quantity) {

        return new Pants(name, 46, price, "Blue",
                quantity, Season.AUTUMN, 80);
    }

    private static void run(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("[PASS] " + name);
        } catch (Throwable ex) {
            failed++;
            System.out.println("[FAIL] " + name);
            System.out.println("       " + ex);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void expectException(
            Class<? extends Throwable> expected,
            Runnable action) {

        try {
            action.run();
        } catch (Throwable actual) {
            if (expected.isInstance(actual)) {
                return;
            }

            throw new AssertionError(
                    "Expected " + expected.getSimpleName()
                    + ", got " + actual.getClass().getSimpleName(),
                    actual);
        }

        throw new AssertionError(
                "Expected exception: " + expected.getSimpleName());
    }
}
