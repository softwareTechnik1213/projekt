package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;

public class HomeContainerTest {

    private Player p1;
    private HomeContainer hc;

    @Before
    public void setUp() throws Exception {
        p1 = new Player(1, Color.red, "p1");

    }

    @Test
    public void testFinishContainer() {
        hc = new HomeContainer(4);
        hc.setOwner(p1);
        assertSame(p1, hc.getOwner());
    }

    @Test
    public void testSetOwner() {
        hc = new HomeContainer(4);
        hc.setOwner(p1);
        assertSame(p1, hc.getOwner());
    }

    @Test
    public void testGetFieldsList() {
        HomeContainer f = new HomeContainer(0);
        Field field = new Field();
        f.fieldList().add(field);
        assertSame(field, f.fieldList().get(0));

    }

    @Test
    public void testToCharArray() {
        HomeContainer f = new HomeContainer(4);
        char[][] ca = f.toCharArray();
        assertEquals(ca.length, 2);
        assertEquals(ca[0].length, 2);
        assertEquals(ca[1].length, 2);
    }
}
