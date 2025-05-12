package by.gsu.scherbak.Table_DAO;

import by.gsu.scherbak.Table_Mapping.MooringMap;
import by.gsu.scherbak.interfaces.CRUDable;

import java.sql.*;
import java.util.Scanner;

public class MooringDAO implements CRUDable<MooringMap> {

    //Добавление записи в таблицу mooring
    @Override
    public void insertRecords(Connection connection, MooringMap obj) throws SQLException {
        int rows;
        String sql = "INSERT INTO mooring (id, idShip, mooring_date) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, obj.getId());
        statement.setInt(2, obj.getIdShip());
        statement.setDate(3, Date.valueOf(obj.getMooringDate()));

        rows = statement.executeUpdate();

        if(rows > 0){
            System.out.println("Запись была добавлена.");
        } else{
            System.out.println("Произошла ошибка при добавлении записи.");
        }
    }

    //Удаление записи из таблицы mooring
    @Override
    public void deleteRecords(Connection connection) throws SQLException {
        int id;
        int rowsAffected;
        Scanner scanner = new Scanner(System.in);
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mooring WHERE id = ?"
        );

        System.out.println("Введите id записи для удаления: ");
        id = scanner.nextInt();

        statement.setInt(1, id);

        rowsAffected = statement.executeUpdate();

        if(rowsAffected > 0){
            System.out.println("Запись успешно удалена!");
        } else {
            System.out.println("Не удалось удалить запись.");
        }
    }

    //Редактирование записи в таблице mooring
    @Override
    public void editRecords(Connection connection, MooringMap obj) throws SQLException {
        int newIdShip;
        String newDate;
        Scanner scanner = new Scanner(System.in);
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE mooring SET idShip = ?, mooring_date = ? WHERE id = ?"
        );

        System.out.println("Введите новое ID судна: ");
        newIdShip = scanner.nextInt();
        obj.setIdShip(newIdShip);
        scanner.nextLine();

        System.out.println("Введите новую дату в формате yyyy-MM-dd: ");
        newDate = scanner.nextLine();
        obj.setMooringDate(newDate);

        statement.setInt(1, obj.getIdShip());
        statement.setString(2, obj.getMooringDate());
        statement.setInt(3, obj.getId());
        statement.executeUpdate();
    }

    //Чтение записей из таблицы mooring
    @Override
    public void readRecords(Connection connection) throws SQLException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM mooring");
        System.out.println("\nТАБЛИЦА ШВАРТОВОК");
        System.out.println("|-----|--------|---------------------|");
        System.out.printf("| %-3s | %-6s | %-19s |\n", "ID", "ID судна", "Дата швартовки");
        System.out.println("|-----|--------|---------------------|");
        while(set.next()){
            int id = set.getInt("id");
            int shipId = set.getInt("idShip");
            Timestamp mooringDate = set.getTimestamp("mooring_date");

            System.out.printf("| %-3d | %-6d | %-19s |\n", id, shipId, mooringDate.toString());
        }
    }

    //Фильтрация записей из тaблицы mooring
    @Override
    public void filterRecords(Connection connection) throws SQLException {
        String userInput;
        Scanner scanner = new Scanner(System.in);
        String sql = "SELECT * FROM mooring WHERE mooring_date = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set;

        System.out.println("Введите дату для фильтрации: ");
        userInput = scanner.nextLine();

        statement.setString(1, userInput);
        set = statement.executeQuery();

        System.out.println("\nТАБЛИЦА ШВАРТОВОК");
        System.out.println("|-----|--------|---------------------|");
        System.out.printf("| %-3s | %-6s | %-19s |\n", "ID", "ID судна", "Дата швартовки");
        System.out.println("|-----|--------|---------------------|");
        while(set.next()){
            int id = set.getInt("id");
            int shipId = set.getInt("idShip");
            Timestamp mooringDate = set.getTimestamp("mooring_date");

            System.out.printf("| %-3d | %-6d | %-19s |\n", id, shipId, mooringDate.toString());
        }
    }
}
