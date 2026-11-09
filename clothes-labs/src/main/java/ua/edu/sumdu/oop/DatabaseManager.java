
package ua.edu.sumdu.oop;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

public final class DatabaseManager {

    private final String url;

    public DatabaseManager(Path configPath)
            throws IOException, SQLException {

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
        }

        url = properties.getProperty("db.url");

        if (url == null || !url.startsWith("jdbc:sqlite:")) {
            throw new IllegalArgumentException(
                    "Invalid SQLite database URL."
            );
        }

        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS clothes_items ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "type TEXT NOT NULL,"
                + "name TEXT NOT NULL,"
                + "size INTEGER NOT NULL,"
                + "price REAL NOT NULL,"
                + "color TEXT NOT NULL,"
                + "quantity INTEGER NOT NULL,"
                + "season TEXT NOT NULL,"
                + "waist_cm INTEGER,"
                + "denim_weight_oz INTEGER,"
                + "sleeve_length_cm INTEGER,"
                + "print_text TEXT"
                + ")";

        try (Connection connection =
                     DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        }
    }

    public void insert(Clothes item) throws SQLException {
        String sql = "INSERT INTO clothes_items "
                + "(type, name, size, price, color, quantity, "
                + "season, waist_cm, denim_weight_oz, "
                + "sleeve_length_cm, print_text) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection =
                     DriverManager.getConnection(url);
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1, item.getClass().getSimpleName()
            );
            statement.setString(2, item.getName());
            statement.setInt(3, item.getSize());
            statement.setDouble(4, item.getPrice());
            statement.setString(5, item.getColor());
            statement.setInt(6, item.getQuantity());
            statement.setString(7, item.getSeason().name());

            if (item instanceof Pants) {
                statement.setInt(
                        8, ((Pants) item).getWaistCm()
                );
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            if (item instanceof Jeans) {
                statement.setInt(
                        9, ((Jeans) item).getDenimWeightOz()
                );
            } else {
                statement.setNull(9, Types.INTEGER);
            }

            if (item instanceof Shirts) {
                statement.setInt(
                        10, ((Shirts) item).getSleeveLengthCm()
                );
            } else {
                statement.setNull(10, Types.INTEGER);
            }

            if (item instanceof TShirt) {
                statement.setString(
                        11, ((TShirt) item).getPrintText()
                );
            } else {
                statement.setNull(11, Types.VARCHAR);
            }

            statement.executeUpdate();
        }
    }
}
