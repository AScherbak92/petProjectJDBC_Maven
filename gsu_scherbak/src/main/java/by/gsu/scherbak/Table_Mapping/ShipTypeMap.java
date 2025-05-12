package by.gsu.scherbak.Table_Mapping;

import by.gsu.scherbak.interfaces.Mappable;

import java.sql.*;
import java.util.Scanner;

public class ShipTypeMap implements Mappable {
    private int id;
    private String typeName;

    //Конструктор, геттеры, сеттеры
    public ShipTypeMap(int id, String typeName){
        this.id = id;
        this.typeName = typeName;
    }

    public ShipTypeMap(){
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Создание объекта
    @Override
    public void createObject() {
        Scanner scanner = new Scanner(System.in);
        int id;
        String typeName;

        System.out.println("Введите желаемый id для типа судна: ");
        id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите желаемое название вида судна: ");
        typeName = scanner.nextLine();

        this.id = id;
        this.typeName = typeName;
        System.out.println("Объект вида судна был создан.");
    }

    //Взятие записи из таблицы по id
    @Override
    public void getById(int id, Connection connection) throws SQLException {
        ResultSet set;
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM shipType WHERE id = ?"
        );

        statement.setInt(1, id);
        set = statement.executeQuery();

        if (set.next()){
            int resultId = set.getInt("id");
            String resultName = set.getString("type_name");
            System.out.println("Запись была найдена.");

            this.id = resultId;
            this.typeName = resultName;
        } else{
            System.out.println("Запись с указанным id не была найдена.");

        }
    }
}
