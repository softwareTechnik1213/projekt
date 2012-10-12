package de.htwg_konstanz.ain3.madn.model;

import de.htwg_konstanz.ain3.madn.model.util.FieldType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class FieldTest {

    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        Field instance = null;
        Player expResult = null;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetPlayer() {
        System.out.println("setPlayer");
        Player player = null;
        Field instance = null;
        instance.setPlayer(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetType() {
        System.out.println("getType");
        Field instance = null;
        FieldType expResult = null;
        FieldType result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
