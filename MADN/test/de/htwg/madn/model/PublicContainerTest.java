package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;

public class PublicContainerTest {

    private Player p1;
    private PublicContainer pc;

    @Before
    public void setUp() throws Exception {
        p1 = new Player(1, Color.red, "p1");

    }

    @Test
    public void testPublicContainer() {
        pc = new PublicContainer(4);
    }

    @Test
    public void testGetFieldsList() {
        PublicContainer f = new PublicContainer(0);
        Field field = new Field();
        f.fieldList().add(field);
        assertSame(field, f.fieldList().get(0));

    }

    @Test
    public void testToCharArray() {
        PublicContainer f = new PublicContainer(40);
        char[][] ca = f.toCharArray();

        f = new PublicContainer(20);
        char[][] ce = f.toCharArray();
    }
}
