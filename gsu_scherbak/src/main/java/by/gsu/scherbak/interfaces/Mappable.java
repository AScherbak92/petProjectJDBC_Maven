package by.gsu.scherbak.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface Mappable{
    public void createObject();
    public void getById(int id, Connection connection) throws SQLException;
}
