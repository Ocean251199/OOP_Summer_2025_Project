import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection connect() {
        try {
            String relativePath = "src/main/resources/db/library.db";
            String absolutePath = new File(relativePath).getAbsolutePath();
            String url = "jdbc:sqlite:" + absolutePath;
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }
}
