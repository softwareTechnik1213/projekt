package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FinishContainerTest {

    private Player p1;
    private FinishContainer fc;

    @Before
    public void setUp() throws Exception {
        p1 = new Player(1, Color.red, "p1");

    }

    @Test
    public void testFinishContainer() {
        fc = new FinishContainer(4);
        fc.setOwner(p1);
        assertSame(p1, fc.getOwner());
    }

    @Test
    public void testSetOwner() {
        fc = new FinishContainer(4);
        fc.setOwner(p1);
        assertSame(p1, fc.getOwner());
    }

    @Test
    public void testGetFieldsList() {
        FinishContainer f = new FinishContainer(0);
        Field field = new Field();
        f.fieldList().add(field);
        assertSame(field, f.fieldList().get(0));
    }

    @Test
    public void testToCharArray() {
        FinishContainer f = new FinishContainer(4);
        char[][] ca = f.toCharArray();
        assertEquals(ca.length, 1);
        assertEquals(ca[0].length, 4);
    }
}
