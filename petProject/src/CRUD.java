import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CRUD {
    //Метод добавления записей в таблицу
    public static void insertInto(Connection connection) throws SQLException {
        //Переменные
        String table, sql;
        ArrayList<String> values = new ArrayList<>();
        PreparedStatement statement;
        int userChoice;
        Scanner scanner = new Scanner(System.in);

        //Вводы пользователя
        System.out.println("Введите номер таблицы, в которую надо вставить запись\n" +
                "1 - Вид судна\n2 - Судно\n3 - Швартовка\nВвод: ");
        userChoice = scanner.nextInt();
        switch(userChoice){
            case (1):
                //Формирование подготовленного запроса
                table = "ВидСудна";
                sql = "INSERT INTO " + table + " (ВидСудна) VALUES (?)";;
                statement = connection.prepareStatement(sql);

                //Ввод данных для запроса
                System.out.println("Введите новый вид судна, который хотите добавить\n" +
                        "Ввод не должен превышать 20 символов\nВвод: ");
                scanner.nextLine();
                values.add(0, scanner.nextLine());
                if(values.get(0).length() > 20){
                    System.out.println("Длина строки не должна превышать 20 символов.");
                    break;
                }

                //Подставление значений в запрос
                statement.setString(1, values.get(0));
                System.out.println("Запись успешно добавлена!");

                statement.executeUpdate();
                values.clear();
                break;
            case (2):
                //Формирование подготовленного запроса
                table = "Судно";
                sql = "INSERT INTO " + table + " (НазваниеСудна, idВида, ТоннажСудна) VALUES (?,?,?)";
                statement = connection.prepareStatement(sql);

                //Ввод данных для запроса
                System.out.println("Введите название для новго судна - ");
                scanner.nextLine();
                values.add(0, scanner.nextLine());
                System.out.println("Введите вид судна, который хотите присвоить новому судну." +
                        "Доступные id: ");
                //Вывод и формирование списка доступных для выбора id видов судна и их названия
                ArrayList<Integer> allowedId = allowedId("ВидСудна", connection);//Для предотвращения ввода несуществующего id
                while(true){
                    System.out.println("Ввод: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    if(allowedId.contains(id)){
                        values.add(1, Integer.toString(id));
                        break;
                    }
                    else{
                        scanner.nextLine();
                        System.out.println("Введён неверный id, попробуйте ещё раз.");
                    }
                }
                System.out.println("Введите тоннаж для вашего судна: ");
                while (true){
                    if (scanner.hasNextInt()){
                        values.add(2, Integer.toString(scanner.nextInt()));
                        scanner.nextLine();
                        break;
                    }
                    else {
                        scanner.nextLine();
                        System.out.println("Ошибка: введите числовое значение тоннажа.");
                    }
                }

                //Подставление значений в запрос
                statement.setString(1, values.get(0));
                statement.setInt(2, Integer.parseInt(values.get(1)));
                statement.setInt(3, Integer.parseInt(values.get(2)));
                System.out.println("Запись успешно добавлена!");

                statement.executeUpdate();
                values.clear();
                break;
            case (3):
                //Формирование подготовленного запроса
                table = "Швартовка";
                sql = "INSERT INTO " + table + " (idСудна, ДатаШвартовки) VALUES (?,?)";
                statement = connection.prepareStatement(sql);

                //Ввод данных для запроса
                System.out.println("Введите судно, которое хотите добавить в таблицу Швартовка." +
                        "Доступные id: ");
                //Вывод и формирование списка доступных для выбора id судов и их названия
                allowedId = allowedId("Судно", connection);//Для предотвращения ввода несуществующего id
                while(true){
                    System.out.println("Ввод: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    if(allowedId.contains(id)){
                        values.add(0, Integer.toString(id));
                        break;
                    }
                    else{
                        scanner.nextLine();
                        System.out.println("Введён неверный id, попробуйте ещё раз.");
                    }
                }
                System.out.println("Введите дату швартовки (формат YYYY-MM-DD): ");
                String date;
                while(true){
                    date = scanner.nextLine().trim();
                    if(date.matches("\\d{4}-\\d{2}-\\d{2}")){
                        break;
                    }
                    else{
                        System.out.println("Неверно введён формат даты. Формат YYYY-MM-DD");
                    }
                }

                //Подставление значений в запрос
                statement.setInt(1, Integer.parseInt(values.get(0)));
                statement.setDate(2, Date.valueOf(date));

                statement.executeUpdate();
                System.out.println("Запись успешно добавлена!");
                values.clear();
                break;
            default:
                System.out.println("Такой таблицы не существует.");
                break;
        }
    }

    //Метод для чтения записей
    public static void readRecords(Connection connection) throws SQLException {
        //Переменные
        int userChoice;
        Scanner scanner = new Scanner(System.in);

        //Вводы пользователя
        System.out.println("Введите номер таблицы, в которой хотите прочитать запись\n" +
                "1 - Вид судна\n2 - Судно\n3 - Швартовка\n4 - Все таблицы\nВвод: ");
        userChoice = scanner.nextInt();
        switch(userChoice){
            case (1):
                showRecords("ВидСудна", connection);
                break;
            case (2):
                showRecords("Судно", connection);
                break;
            case (3):
                showRecords("Швартовка", connection);
                break;
            case (4):
                showRecords("ВидСудна", connection);
                showRecords("Судно", connection);
                showRecords("Швартовка", connection);
                break;
        }
    }

    //Метод удаления записей
    public static void deleteRecords(Connection connection) throws SQLException {
        //Переменные
        String sql, table = "ВидСудна";
        int userChoice;
        Scanner scanner = new Scanner(System.in);

        //Вводы пользователя
        System.out.println("Выберите таблицу, из которой требуется удалить запись." +
                "\n1 - Вид судна\n2 - Судно\n3 - Швартовка\nВвод: ");
        userChoice = scanner.nextInt();

        switch(userChoice){
            case (1):
                table = "ВидСудна";
                break;
            case(2):
                table = "Судно";
                break;
            case(3):
                table = "Швартовка";
                break;
            default:
                System.out.println("Такой таблицы не существует. По умолчанию выбрана таблица Вид судна");
                break;
        }

        System.out.println("Текущие записи в таблице: ");
        showRecords(table, connection);
        System.out.println("Введите id записи для удаления: ");
        ArrayList<Integer> allowedId = allowedId(table, connection);//Для предотвращения ввода несуществующего id
        PreparedStatement statement = null;
        while (true) {
            System.out.print("Ввод: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (allowedId.contains(id)) {
                sql = "DELETE FROM " + table + " WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, id); // Подставляем id в запрос

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Запись успешно удалена!");
                } else {
                    System.out.println("Не удалось удалить запись.");
                }
                break;
            } else {
                System.out.println("Введён неверный id, попробуйте ещё раз.");
            }
        }
    }

    //Метод изменения данных из таблицы
    public static void changeRecords(Connection connection) throws SQLException {
        //Переменные
        String sql, table = "ВидСудна";
        int userChoice;
        Scanner scanner = new Scanner(System.in);

        //Вводы пользователя
        System.out.println("Выберите таблицу, в которой требуется изменить запись." +
                "\n1 - Вид судна\n2 - Судно\n3 - Швартовка\nВвод: ");
        userChoice = scanner.nextInt();

        switch(userChoice){
            case (1):
                table = "ВидСудна";
                break;
            case(2):
                table = "Судно";
                break;
            case(3):
                table = "Швартовка";
                break;
            default:
                System.out.println("Такой таблицы не существует. По умолчанию выбрана таблица Вид судна");
                break;
        }

        System.out.println("Выберите id записи, которая подлежит изменению");
        ArrayList<Integer> allowedId = allowedId(table, connection);//Для предотвращения ввода несуществующего id

        int idToUpdate = -1;
        while (true) {
            System.out.print("Ввод: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (allowedId.contains(id)) {
                idToUpdate = id;
                break;
            } else {
                System.out.println("Введён неверный id, попробуйте ещё раз.");
            }
        }
        PreparedStatement statement;

        switch(table){
            case ("ВидСудна"):
                System.out.println("Введите новое название вида судна:");
                String newType = scanner.nextLine();
                sql = "UPDATE ВидСудна SET ВидСудна = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, newType);
                statement.setInt(2, idToUpdate);
                break;
            case ("Судно"):
                System.out.println("Введите новое название судна:");
                String name = scanner.nextLine();
                System.out.println("Введите id вида судна:");
                int typeId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Введите тоннаж судна:");
                int tonnage = scanner.nextInt();
                scanner.nextLine();
                sql = "UPDATE Судно SET НазваниеСудна = ?, idВида = ?, ТоннажСудна = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setInt(2, typeId);
                statement.setInt(3, tonnage);
                statement.setInt(4, idToUpdate);
                break;
            case ("Швартовка"):
                System.out.println("Введите id судна:");
                int shipId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Введите дату швартовки в формате YYYY-MM-DD:");
                String dateInput = scanner.nextLine();
                java.sql.Date dockingDate = java.sql.Date.valueOf(dateInput);
                sql = "UPDATE Швартовка SET idСудна = ?, ДатаШвартовки = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, shipId);
                statement.setDate(2, dockingDate);
                statement.setInt(3, idToUpdate);
                break;
            default:
                System.out.println("Произошла ошибка");
                return;
        }
        int rows = statement.executeUpdate();
        if (rows > 0) {
            System.out.println("Запись была обновлена.");
        } else {
            System.out.println("Ошибка при обновлении записи :(");
        }
    }

    //Метод фильтрации записей
    public static void filterRecords(Connection connection) throws SQLException {
        //Переменные
        int userChoice;
        Scanner scanner = new Scanner(System.in);

        //Вводы пользователя
        System.out.println("Выберите таблицу, в которой сделать фильтрацию записей." +
                "\n1 - Вид судна\n2 - Судно\n3 - Швартовка\nВвод: ");
        userChoice = scanner.nextInt();

        switch(userChoice){
            case (1):
                filter(connection, "ВидСудна", "ВидСудна");
                break;
            case (2):
                filter(connection, "Судно", "НазваниеСудна");
                break;
            case (3):
                filter(connection, "Швартовка", "ДатаШвартовки");
                break;
        }
    }

    public static void filter(Connection connection, String table, String column) throws SQLException {
        //Параметр column принимает значение, по которому должна происходить фильтрация
        //Переменные
        String sql = "SELECT * FROM " + table + " WHERE " + column + " = ?";

        System.out.println("Доступные записи из таблицы " + table);
        showRecords(table, connection);
        System.out.println("Введите существующее в таблице " + column);

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userInput);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Результаты фильтрации:");
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        }
    }

    //Метод  вывода данных из таблицы
    public static void showRecords(String table, Connection connection) throws SQLException {
        //Переменные
        String sql;
        ResultSet resultSet;

        switch(table){
            case ("ВидСудна"):
                resultSet = connection.createStatement().executeQuery("SELECT * FROM ВидСудна");
                System.out.println("\nТаблица ВидСудна");
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.printf("ID = %d.| Название вида - %s\n", id, resultSet.getString(2));
                }
                break;
            case ("Судно"):
                resultSet = connection.createStatement().executeQuery("SELECT * FROM Судно");
                System.out.println("\nТаблица Судно");
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.printf("ID судна - %d.| Название судна - %s | ID вида - %d | Тоннаж - %d\n"
                            , id, resultSet.getString(2)
                    ,resultSet.getInt(3), resultSet.getInt(4));
                }
                break;
            case("Швартовка"):
                resultSet = connection.createStatement().executeQuery("SELECT * FROM Швартовка");
                System.out.println("\nТаблица Швартовка");
                while(resultSet.next()){
                    java.sql.Date dockingDate = resultSet.getDate(3);
                    System.out.printf("ID швартовки - %d. | ID судна - %d | Дата швартовки - %s\n"
                            , resultSet.getInt(1)
                            ,resultSet.getInt(2), dockingDate.toString());
                }
                break;
        }
    }

    //Метод добавления в список допустимых id из переданной таблицы
    public static ArrayList<Integer> allowedId(String table, Connection connection) throws SQLException {
        //Переменные
        ArrayList<Integer> list = new ArrayList<>();
        ResultSet resultSet;

        resultSet = connection.createStatement().executeQuery("SELECT * FROM " + table);

        while(resultSet.next()){
            int id = resultSet.getInt(1);
            System.out.printf("%d. %s\n", id, resultSet.getString(2));
            list.add(id);
        }
        return list;
    }
}
