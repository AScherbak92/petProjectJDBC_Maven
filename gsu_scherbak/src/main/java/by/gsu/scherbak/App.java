package by.gsu.scherbak;

import by.gsu.scherbak.Database.DBConfiguration;
import by.gsu.scherbak.Table_DAO.MooringDAO;
import by.gsu.scherbak.Table_DAO.ShipDAO;
import by.gsu.scherbak.Table_DAO.ShipTypeDAO;
import by.gsu.scherbak.Table_Mapping.MooringMap;
import by.gsu.scherbak.Table_Mapping.ShipMap;
import by.gsu.scherbak.Table_Mapping.ShipTypeMap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {

        try (Connection connection = DBConfiguration.establishConnection()) {
            //Создание DAO для таблиц
            ShipTypeDAO shipTypeTable = new ShipTypeDAO();
            ShipDAO shipTable = new ShipDAO();
            MooringDAO mooringTable = new MooringDAO();

            System.out.println("Успешное подключение к БД!");

            //Основное меню
            while(true){
                int userChoice;
                Scanner scanner = new Scanner(System.in);

                System.out.println("--МЕНЮ--\n1 - Добавление записи в таблицу." +
                        "\n2 - Чтение записей из всех таблиц." +
                        "\n3 - Удаление записей\n4 - Изменение записей." +
                        "\n5 - Фильтрация записей.\n0 - Выход из программы");
                userChoice = scanner.nextInt();
                scanner.nextLine();
                switch(userChoice){
                    //Добавление записей в таблицу
                    case 1:
                        int insertTableChoice;

                        System.out.println("Выберите в какую таблицу добавить запись:" +
                                "\n1 - Вид судна.\n2 - Суда.\n3 - Швартовка.");
                        insertTableChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch(insertTableChoice){
                            case 1:
                                ShipTypeMap shipTypeObj = new ShipTypeMap();
                                shipTypeObj.createObject();
                                shipTypeTable.insertRecords(connection, shipTypeObj);
                                break;
                            case 2:
                                ShipMap shipObj = new ShipMap();
                                shipObj.createObject();
                                shipTable.insertRecords(connection, shipObj);
                                break;
                            case 3:
                                MooringMap mooringObj = new MooringMap();
                                mooringObj.createObject();
                                mooringTable.insertRecords(connection, mooringObj);
                                break;
                            }
                        break;

                    //Чтение записей из всех таблиц
                    case 2:
                        mooringTable.readRecords(connection);
                        shipTable.readRecords(connection);
                        shipTypeTable.readRecords(connection);
                        break;

                    //Удаление записей из таблицы
                    case 3:
                        int deleteTableChoice;

                        System.out.println("Выберите из какой таблицы удалить запись:" +
                                "\n1 - Вид судна.\n2 - Суда.\n3 - Швартовка.");
                        deleteTableChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch(deleteTableChoice){
                            case 1:
                                shipTypeTable.deleteRecords(connection);
                                break;
                            case 2:
                                shipTable.deleteRecords(connection);
                                break;
                            case 3:
                                mooringTable.deleteRecords(connection);
                                break;
                            }

                    //Изменение записи
                    case 4:
                        int changeTableChoice;

                        System.out.println("Выберите в какой таблице изменить запись:" +
                                "\n1 - Вид судна.\n2 - Суда.\n3 - Швартовка.");
                        changeTableChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch(changeTableChoice){
                            case 1:
                                int chooseTypeId;
                                ShipTypeMap shipTypeObj = new ShipTypeMap();

                                System.out.println("Выберите id записи для изменения: ");
                                chooseTypeId = scanner.nextInt();
                                shipTypeObj.getById(chooseTypeId, connection);
                                shipTypeTable.editRecords(connection, shipTypeObj);
                                break;
                            case 2:
                                int chooseShipId;
                                ShipMap shipObj = new ShipMap();

                                System.out.println("Выберите id записи для изменения: ");
                                chooseShipId = scanner.nextInt();
                                shipObj.getById(chooseShipId, connection);
                                shipTable.editRecords(connection, shipObj);
                                break;
                            case 3:
                                int chooseMooringId;
                                MooringMap MooringObj = new MooringMap();

                                System.out.println("Выберите id записи для изменения: ");
                                chooseMooringId = scanner.nextInt();
                                MooringObj.getById(chooseMooringId, connection);
                                mooringTable.editRecords(connection, MooringObj);
                                break;
                        }

                    //Фильтрация записей таблицы
                    case 5:
                        int filterTableChoice;

                        System.out.println("Выберите в какой таблице осуществить фильтрацию:" +
                                "\n1 - Вид судна (по виду судна).\n2 - Суда (по тоннажу).\n" +
                                "3 - Швартовка (по дате).");
                        filterTableChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch(filterTableChoice){
                            case 1:
                                shipTypeTable.filterRecords(connection);
                                break;
                            case 2:
                                shipTable.filterRecords(connection);
                                break;
                            case 3:
                                mooringTable.filterRecords(connection);
                                break;
                        }
                    }
                }
            } catch (SQLException ex) {
            System.out.println("Что-то пошло не так с подключением к БД...");
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
