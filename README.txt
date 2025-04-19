В файл конфигурации ( у меня configuration.properties ) вставить свой пароль и путь

Скрипт createDB создаёт БД с нужными таблицами и полями

Строка для компиляции основной программы -
javac -cp ".;mysql-connector-j-9.2.0.jar" Main.java DBConfiguration.java CRUD.java

Строка для запуска основной программы - 
java -cp ".;mysql-connector-j-9.2.0.jar" Main.java <configuration file>

Unit тесты
javac -cp ".;junit-4.13.2.jar;mysql-connector-j-9.2.0.jar" UnitTest.java
java -cp ".;junit-4.13.2.jar;mysql-connector-j-9.2.0.jar" UnitTest <configuration file>
