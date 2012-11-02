package de.htwg.madn.model;

import static org.junit.Assert.assertSame;
import org.junit.Test;

public class PublicContainerTest {

    @Test
    public void testPublicContainer() {
        new PublicContainer(4);
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
        f.toCharArray();

        f = new PublicContainer(20);
        f.toCharArray();
    }
}
