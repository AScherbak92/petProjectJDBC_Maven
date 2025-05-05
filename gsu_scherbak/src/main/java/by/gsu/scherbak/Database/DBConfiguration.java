package by.gsu.scherbak.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfiguration {
    public static Connection establishConnection() throws IOException, SQLException {
        Properties props = new Properties();
        try (InputStream input = DBConfiguration.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл configuration.properties не найден в resources");
            }
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
