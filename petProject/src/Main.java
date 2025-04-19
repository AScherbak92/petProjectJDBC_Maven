//import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class Main {

    public static void main(String[] args) {
        String configFile = args[0];

        try (Connection connection = DBConfiguration.establishConnection(configFile)) {
            System.out.println("Успешное подключение к БД!");
            while(true){
                System.out.println("--МЕНЮ--\n1 - Добавление записи в таблицу.\n2 - Чтение записей." +
                        "\n3 - Удаление записейю\n4 - Изменение записей." +
                        "\n5 - Фильтрация записей.\n0 - Выход из программы");
                Scanner scanner = new Scanner(System.in);
                int userChoice = scanner.nextInt();
                switch(userChoice){
                    case (1):
                        CRUD.insertInto(connection);
                        break;
                    case (2):
                        CRUD.readRecords(connection);
                        break;
                    case(3):
                        CRUD.deleteRecords(connection);
                        break;
                    case(4):
                        CRUD.changeRecords(connection);
                        break;
                    case(5):
                        CRUD.filterRecords(connection);
                        break;
                    case(0):
                        return;
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("Что-то пошло не так с подключением к БД...");
            e.printStackTrace();
        }
    }
}