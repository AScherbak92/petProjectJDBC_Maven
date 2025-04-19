import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.*; // JUnit 4
import org.junit.Test;

public class UnitTest {

    private static Connection connection;

    public static void initConnection(String configFilePath) throws SQLException, IOException {
        connection = DBConfiguration.establishConnection(configFilePath);
    }

    @Test
    public void testInsertAndRead() {
        try {
            String insertSql = "INSERT INTO ВидСудна (ВидСудна) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, "Тестовый");
            int affectedRows = ps.executeUpdate();
            assertTrue("Запись должна быть добавлена", affectedRows > 0);

            String selectSql = "SELECT * FROM ВидСудна WHERE ВидСудна = ?";
            PreparedStatement selectPs = connection.prepareStatement(selectSql);
            selectPs.setString(1, "Тестовый");
            ResultSet rs = selectPs.executeQuery();
            assertTrue("Запись 'Тестовый' должна существовать", rs.next());

            //Очистка за собой
            String deleteSql = "DELETE FROM ВидСудна WHERE ВидСудна = ?";
            PreparedStatement delPs = connection.prepareStatement(deleteSql);
            delPs.setString(1, "Тестовый");
            delPs.executeUpdate();

        } catch (SQLException e) {
            fail("Ошибка при тестировании CRUD операций: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу конфигурации БД.");
            return;
        }
        try {
            initConnection(args[0]);
            System.out.println("Подключение установлено. Запуск тестов...");

            UnitTest test = new UnitTest();
            test.testInsertAndRead();

            System.out.println("Все тесты прошли успешно!");
        } catch (Exception e) {
            System.out.println("Ошибка при запуске тестов: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
