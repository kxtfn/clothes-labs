package ua.edu.sumdu.oop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;

public class MainApp extends Application {

    private final Store store = new Store("Clothing Store");
    private final ListView<String> productList =
            new ListView<String>();

    private final TextArea resultArea = new TextArea();
    private final TextField uuidField = new TextField();

    private final ComboBox<String> typeBox =
            new ComboBox<String>();
    private final ComboBox<Season> seasonBox =
            new ComboBox<Season>();

    private final TextField nameField = new TextField();
    private final TextField sizeField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField colorField = new TextField();
    private final TextField quantityField = new TextField();
    private final TextField extraField = new TextField();
    private final TextField secondExtraField = new TextField();

    private Path file;

    @Override
    public void start(Stage stage) {
        file = Path.of("input.txt");

        if (!Files.exists(file)) {
            file = Path.of("clothes-labs", "input.txt");
        }

        for (Clothes item : FileStorage.load(file)) {
            store.addNewClothes(item, item.getQuantity());
        }

        typeBox.getItems().addAll(
                "Pants", "Shirts", "Jeans", "TShirt");
        typeBox.setValue("Pants");

        seasonBox.getItems().addAll(Season.values());
        seasonBox.setValue(Season.SUMMER);

        typeBox.setOnAction(event -> updateExtraLabels());
        updateExtraLabels();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);

        form.add(new Label("Product type:"), 0, 0);
        form.add(typeBox, 1, 0);

        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(new Label("Size:"), 0, 2);
        form.add(sizeField, 1, 2);

        form.add(new Label("Price:"), 0, 3);
        form.add(priceField, 1, 3);

        form.add(new Label("Color:"), 0, 4);
        form.add(colorField, 1, 4);

        form.add(new Label("Quantity:"), 0, 5);
        form.add(quantityField, 1, 5);

        form.add(new Label("Season:"), 0, 6);
        form.add(seasonBox, 1, 6);

        form.add(new Label("Waist / Sleeve length:"), 0, 7);
        form.add(extraField, 1, 7);

        form.add(new Label("Denim weight / Print text:"), 0, 8);
        form.add(secondExtraField, 1, 8);

        Button addButton = new Button("Add product");
        addButton.setOnAction(event -> addProduct());

        Button saveButton = new Button("Save inventory");
        saveButton.setOnAction(event -> saveInventory());

        HBox actions = new HBox(10, addButton, saveButton);

        uuidField.setPromptText("Enter product UUID");

        Button searchButton = new Button("Find by UUID");
        searchButton.setOnAction(event -> findProduct());

        HBox searchBox = new HBox(10, uuidField, searchButton);

        productList.setPrefHeight(180);

        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefRowCount(5);

        VBox root = new VBox(
                12,
                new Label("Lab 16 | Clothing Store"),
                form,
                actions,
                new Label("Products (name + UUID):"),
                productList,
                new Label("Search by UUID:"),
                searchBox,
                new Label("Product details:"),
                resultArea
        );

        root.setPadding(new Insets(16));

        refreshList();

        stage.setTitle("Clothing Store - Lab 16");
        stage.setScene(new Scene(root, 650, 850));
        stage.setOnCloseRequest(event -> saveInventory());
        stage.show();
    }

    private void updateExtraLabels() {
        String type = typeBox.getValue();

        if ("Pants".equals(type) || "Shirts".equals(type)) {
            secondExtraField.setDisable(true);
            secondExtraField.clear();
        } else {
            secondExtraField.setDisable(false);
        }

        if ("Pants".equals(type) || "Jeans".equals(type)) {
            extraField.setPromptText("Waist in cm (50-140)");
        } else {
            extraField.setPromptText("Sleeve length in cm (1-120)");
        }

        if ("Jeans".equals(type)) {
            secondExtraField.setPromptText("Denim weight (5-30)");
        } else {
            secondExtraField.setPromptText("Print text");
        }
    }

    private void addProduct() {
        try {
            String type = typeBox.getValue();
            String name = nameField.getText().trim();
            int size = Integer.parseInt(sizeField.getText().trim());
            double price = Double.parseDouble(
                    priceField.getText().trim().replace(',', '.'));
            String color = colorField.getText().trim();
            int quantity = Integer.parseInt(
                    quantityField.getText().trim());
            Season season = seasonBox.getValue();
            int extra = Integer.parseInt(
                    extraField.getText().trim());

            Clothes product;

            switch (type) {
                case "Pants":
                    product = new Pants(name, size, price,
                            color, quantity, season, extra);
                    break;

                case "Shirts":
                    product = new Shirts(name, size, price,
                            color, quantity, season, extra);
                    break;

                case "Jeans":
                    int weight = Integer.parseInt(
                            secondExtraField.getText().trim());
                    product = new Jeans(name, size, price,
                            color, quantity, season, extra, weight);
                    break;

                case "TShirt":
                    product = new TShirt(name, size, price,
                            color, quantity, season, extra,
                            secondExtraField.getText().trim());
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Select a product type.");
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException(
                        "Quantity must be positive.");
            }

            store.addNewClothes(product, quantity);
            refreshList();
            saveInventory();

            resultArea.setText(
                    "Product added successfully.\n"
                    + "New object UUID: " + product.getUuid()
                    + "\n\n"
                    + "If an identical product already exists, "
                    + "its original UUID is retained.");

        } catch (NumberFormatException ex) {
            showError("Enter valid numeric values.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void findProduct() {
        try {
            Clothes product = store.searchByUuid(
                    uuidField.getText());

            if (product == null) {
                resultArea.setText("Product not found.");
            } else {
                resultArea.setText(product.toString());
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void refreshList() {
        productList.getItems().clear();

        ArrayList<Clothes> products = store.getAll();

        for (Clothes product : products) {
            productList.getItems().add(
                    product.toShortString());
        }
    }

    private void saveInventory() {
        try {
            FileStorage.save(file, store.getAll());
        } catch (IOException ex) {
            showError("Save error: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Operation failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

