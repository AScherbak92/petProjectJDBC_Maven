package by.gsu.scherbak;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {

        try (Connection connection = DBConfiguration.establishConnection()) {
            System.out.println("Успешное подключение к БД!");
            while(true){
                System.out.println("--МЕНЮ--\n1 - Добавление записи в таблицу.\n2 - Чтение записей." +
                        "\n3 - Удаление записейю\n4 - Изменение записей." +
                        "\n5 - Фильтрация записей.\n0 - Выход из программы");
                Scanner scanner = new Scanner(System.in);
                int userChoice = scanner.nextInt();

                }
            } catch (SQLException ex) {
            System.out.println("Что-то пошло не так с подключением к БД...");
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
