package by.gsu.scherbak.Table_DAO;

import by.gsu.scherbak.Table_Mapping.ShipTypeMap;
import by.gsu.scherbak.interfaces.CRUDable;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ShipTypeDAO implements CRUDable<ShipTypeMap> {

    //Добавление записи в таблицу shipType
    @Override
    public void insertRecords(Connection connection, ShipTypeMap obj) throws SQLException {
        String sql = "INSERT INTO shipType (id, type_name) VALUES (?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, obj.getId());
        statement.setString(2, obj.getTypeName());

        int rows = statement.executeUpdate();

        if(rows > 0){
            System.out.println("Запись была добавлена.");
        }
        else{
            System.out.println("Произошла ошибка при добавлении записи.");
        }
    }

    //Удаление записи из таблицы shipType
    @Override
    public void deleteRecords(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id записи для удаления: ");
        int id = scanner.nextInt();

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM shipType WHERE id = ?"
        );

        statement.setInt(1, id);

        int rowsAffected = statement.executeUpdate();

        if(rowsAffected > 0){
            System.out.println("Запись успешно удалена!");
        } else {
            System.out.println("Не удалось удалить запись.");
        }
    }

    //Редактирование записи в таблице shipType
    @Override
    public void editRecords(Connection connection, ShipTypeMap obj) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новое название для вида судна: ");

        String newName = scanner.nextLine();
        obj.setTypeName(newName);

        PreparedStatement statement = connection.prepareStatement(
                "UPDATE shipType SET type_name = ? WHERE id = ?"
        );

        System.out.println("ID = " + obj.getId());


        statement.setString(1, obj.getTypeName());
        statement.setInt(2, obj.getId());
        statement.executeUpdate();
    }

    //Чтение записей из таблицы shipType
    @Override
    public void readRecords(Connection connection) throws SQLException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM shipType");
        System.out.println("\nТАБЛИЦА ВИД СУДНА");
        System.out.println("|---|------------------------------------|");
        System.out.println(" id  Название вида");
        System.out.println("|___|____________________________________|");
        while(set.next()){
            System.out.printf("|%-1d  |%-36s|\n",
                    set.getInt(1), set.getString(2));
        }
    }

    //Фильтрация записей из тaблицы shipType
    @Override
    public void filterRecords(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название вида для фильтрации: ");
        String userInput = scanner.nextLine();

        String sql = "SELECT * FROM shipType WHERE type_name = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userInput);
        ResultSet set = statement.executeQuery();

        System.out.println("\nТАБЛИЦА ВИД СУДНА");
        System.out.println("|---|------------------------------------|");
        System.out.println(" id  Название вида");
        System.out.println("|___|____________________________________|");
        while(set.next()){
            System.out.printf("|%-1d  |%-36s|\n",
                    set.getInt(1), set.getString(2));
        }
    }
}
