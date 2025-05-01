package by.gsu.scherbak.Table_DAO;

import by.gsu.scherbak.Table_Mapping.ShipMap;
import by.gsu.scherbak.interfaces.CRUDable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ShipDAO implements CRUDable<ShipMap> {

    //Добавление записи в таблицу ship
    @Override
    public void insertRecords(Connection connection, ShipMap obj) throws SQLException {
        String sql = "INSERT INTO ship (id, ship_name, idType, ship_tonnage) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, obj.getId());
        statement.setString(2, obj.getShipName());
        statement.setInt(3, obj.getIdType());
        statement.setInt(4, obj.getShipTonnage());

        int rows = statement.executeUpdate();

        if(rows > 0){
            System.out.println("Запись была добавлена.");
        }
        else{
            System.out.println("Произошла ошибка при добавлении записи.");
        }
    }

    //Удаление записи из таблицы ship
    @Override
    public void deleteRecords(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id записи для удаления: ");
        int id = scanner.nextInt();

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM ship WHERE id = ?"
        );

        statement.setInt(1, id);

        int rowsAffected = statement.executeUpdate();

        if(rowsAffected > 0){
            System.out.println("Запись успешно удалена!");
        } else {
            System.out.println("Не удалось удалить запись.");
        }
    }

    //Редактирование записи в таблице ship
    @Override
    public void editRecords(Connection connection, ShipMap obj) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новое название для судна: ");
        String newName = scanner.nextLine();
        obj.setShipName(newName);

        System.out.println("Введите новый ID типа: ");
        int newIdType = scanner.nextInt();
        obj.setIdType(newIdType);
        scanner.nextLine();

        System.out.println("Введите новый тоннаж: ");
        int newTonnage = scanner.nextInt();
        obj.setShipTonnage(newTonnage);

        PreparedStatement statement = connection.prepareStatement(
                "UPDATE ship SET ship_name = ?, ship_tonnage = ?, idType = ? WHERE id = ?"
        );

        statement.setString(1, obj.getShipName());
        statement.setInt(2, obj.getShipTonnage());
        statement.setInt(3, obj.getIdType());
        statement.setInt(4, obj.getId());
        statement.executeUpdate();
    }

    //Чтение записей из таблицы ship
    @Override
    public void readRecords(Connection connection) throws SQLException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM ship");
        System.out.println("\nТАБЛИЦА СУДА");
        System.out.println("|-----|----------------------|--------|-------------|");
        System.out.printf("| %-3s | %-20s | %-6s | %-10s |\n", "ID", "Название судна", "ID типа", "Тоннаж");
        System.out.println("|-----|----------------------|--------|-------------|");
        while(set.next()){
            int id = set.getInt("id");
            String shipName = set.getString("ship_name");
            int idType = set.getInt("idType");
            double shipTonnage = set.getDouble("ship_tonnage");

            System.out.printf("| %-3d | %-20s | %-6d | %-11.2f |\n", id, shipName, idType, shipTonnage);
        }
    }

    //Фильтрация записей из тaблицы ship
    @Override
    public void filterRecords(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите тоннаж для фильтрации: ");
        int userInput = scanner.nextInt();

        String sql = "SELECT * FROM ship WHERE ship_tonnage = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userInput);
        ResultSet set = statement.executeQuery();

        System.out.println("\nТАБЛИЦА СУДА");
        System.out.println("|-----|----------------------|--------|-------------|");
        System.out.printf("| %-3s | %-20s | %-6s | %-10s |\n", "ID", "Название судна", "ID типа", "Тоннаж");
        System.out.println("|-----|----------------------|--------|-------------|");
        while(set.next()){
            int id = set.getInt("id");
            String shipName = set.getString("ship_name");
            int idType = set.getInt("idType");
            double shipTonnage = set.getDouble("ship_tonnage");

            System.out.printf("| %-3d | %-20s | %-6d | %-11.2f |\n", id, shipName, idType, shipTonnage);
        }
    }
}
