package by.gsu.scherbak.Table_Mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTypeMapTest {

    private ShipTypeMap testObject;

    @BeforeEach
    void setUp() {
        testObject = new ShipTypeMap();
    }

    @Test
    void getTypeName() {
        testObject.setTypeName("TestName");
        assertEquals("TestName", testObject.getTypeName());
    }

    @Test
    void getId() {
        testObject.setId(15);
        assertEquals(15, testObject.getId());
    }
}