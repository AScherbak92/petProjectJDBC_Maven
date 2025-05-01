package by.gsu.scherbak.Table_Mapping;

import by.gsu.scherbak.interfaces.Mappable;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MooringMap implements Mappable {
    private int id, idShip;
    private String mooringDate;

    //Конструктор, геттеры, сеттеры
    public MooringMap(int id, int idShip, String mooringDate){
        this.id = id;
        this.idShip = idShip;
        this.mooringDate = mooringDate;
    }

    public MooringMap(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdShip() {
        return idShip;
    }

    public void setIdShip(int idShip) {
        this.idShip = idShip;
    }

    public String getMooringDate() {
        return mooringDate;
    }

    public void setMooringDate(String mooringDate) {
        this.mooringDate = mooringDate;
    }

    //Создание объекта
    @Override
    public void createObject() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите желаемый id для швартовки: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите желаемый id судна для швартовки: ");
        int idShip = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите желаемую дату и время швартовки (в формате yyyy-MM-dd): ");
        String mooringDate = scanner.nextLine();

        this.id = id;
        this.idShip = idShip;
        this.mooringDate = mooringDate;
        System.out.println("Объект швартовки был создан.");
    }

    //Взятие записи из таблицы по id
    @Override
    public void getById(int id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM mooring WHERE id = ?"
        );
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();

        if(set.next()){
            int resultId = set.getInt("id");
            int resultShipId = set.getInt("idShip");
            Timestamp timestamp = set.getTimestamp("mooring_date");
            String resultMooringDate = timestamp.toLocalDateTime().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            System.out.println("Запись была найдена.");

            this.id = resultId;
            this.idShip = resultShipId;
            this.mooringDate = resultMooringDate;
        }
        else{
            System.out.println("Запись с указанным id не была найдена.");

        }
    }
}
