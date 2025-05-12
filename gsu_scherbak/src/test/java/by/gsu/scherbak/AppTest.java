package by.gsu.scherbak;

import by.gsu.scherbak.Database.DBConfiguration;
import by.gsu.scherbak.Table_DAO.MooringDAO;
import by.gsu.scherbak.Table_DAO.ShipDAO;
import by.gsu.scherbak.Table_DAO.ShipTypeDAO;
import by.gsu.scherbak.Table_Mapping.ShipMap;
import by.gsu.scherbak.Table_Mapping.ShipTypeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class AppTest {

    private ShipTypeMap testShipType;
    private ShipMap testShip;

    @BeforeEach
    void setUp() {
        testShipType = new ShipTypeMap(15, "TEST");
    }

    @Test
    void main() throws SQLException, IOException {
        try (Connection connection = DBConfiguration.establishConnection()){
            ShipDAO shipTable = new ShipDAO();
            ShipTypeDAO typeTable = new ShipTypeDAO();
            PreparedStatement deleteShip = connection.prepareStatement("DELETE FROM ship WHERE id = ?");
            PreparedStatement deleteType = connection.prepareStatement("DELETE FROM shipType WHERE id = ?");

            typeTable.insertRecords(connection, testShipType);
            testShip = new ShipMap(7, 15, 1000, "TEST SHIP");
            shipTable.insertRecords(connection, testShip);

            deleteShip.setInt(1, 7);
            deleteShip.executeUpdate();

            deleteType.setInt(1, 15);
            deleteType.executeUpdate();
        }
    }
}