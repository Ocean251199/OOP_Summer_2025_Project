package service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection connect() {
        try {
            // Explicit driver load
            Class.forName("org.sqlite.JDBC");

            // Absolute path for development
            String relativePath = "src/main/resources/db/library_new.db";
            String absolutePath = new File(relativePath).getAbsolutePath();
            String url = "jdbc:sqlite:" + absolutePath;

            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to SQLite database at " + e.getMessage(), e);
        }
    }
}
