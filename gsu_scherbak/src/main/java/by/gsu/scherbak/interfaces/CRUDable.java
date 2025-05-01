package by.gsu.scherbak.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface CRUDable<T> {
    public void insertRecords(Connection connection, T obj) throws SQLException;
    public void deleteRecords(Connection connection) throws SQLException;
    public void editRecords(Connection connection, T obj) throws SQLException;
    public void readRecords(Connection connection) throws SQLException;
    public void filterRecords(Connection connection) throws SQLException;
}
