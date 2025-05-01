package by.gsu.scherbak.Table_Mapping;

import by.gsu.scherbak.interfaces.Mappable;

import java.sql.*;
import java.util.Scanner;

public class ShipMap implements Mappable {
    private int id, idType, shipTonnage;
    private String shipName;

    //Конструктор, геттеры, сеттеры
    public ShipMap(int id, int idType, int shipTonnage, String shipName){
        this.id = id;
        this.idType = idType;
        this.shipTonnage = shipTonnage;
        this.shipName = shipName;
    }

    public ShipMap(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public int getShipTonnage() {
        return shipTonnage;
    }

    public void setShipTonnage(int shipTonnage) {
        this.shipTonnage = shipTonnage;
    }

    //Создание объекта
    @Override
    public void createObject() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите желаемый id для судна: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите желаемый id типа вида для судна: ");
        int idType = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите желаемый тоннаж для судна: ");
        int shipTonnage = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите желаемое название для судна: ");
        String shipName = scanner.nextLine();

        this.id = id;
        this.idType = idType;
        this.shipTonnage = shipTonnage;
        this.shipName = shipName;
        System.out.println("Объект судна был создан");
    }

    //Взятие записи из таблицы по id
    @Override
    public void getById(int id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM ship WHERE id = ?"
        );
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();

        if (set.next()){
            int resultId = set.getInt("id");
            int resultTypeId = set.getInt("idType");
            int resultTonnage = set.getInt("ship_tonnage");
            String resultName = set.getString("ship_name");
            System.out.println("Запись была найдена.");

            this.id = resultId;
            this.idType = resultTypeId;
            this.shipName = resultName;
            this.shipTonnage = resultTonnage;
        }
        else{
            System.out.println("Запись с указанным id не была найдена.");

        }
    }
}
