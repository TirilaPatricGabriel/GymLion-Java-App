package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfiguration {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "patric";
    private static final String PASSWORD = "0000";

    private static Connection databaseConnection;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConfiguration.class.getName());

    private DatabaseConfiguration() {
    }

    public static Connection getConnection() {
        try {
            if (databaseConnection == null || databaseConnection.isClosed()) {
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
        return databaseConnection;
    }
}
