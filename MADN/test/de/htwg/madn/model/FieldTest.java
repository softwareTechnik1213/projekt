package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

public final class FieldTest {

    @Test
    public void testGetOccupier() {

        Field field = new Field();
        Player player = new Player(1, Color.RED, "test player");
        field.setOccupier(player, 'c');

        Player expected = field.getOccupier();

        assertSame(expected, player);

        try {
            field.setOccupier(null, 'H');
        } catch (Exception x) {
            assertNotNull(x);
        }
    }

    @Test
    public void testToChar() {
        Field field = new Field();
        Player player = new Player(1, Color.RED, "test player");
        field.setOccupier(player, 'H');
        assertEquals('H', field.toChar('-'));
        field.removeOccupier();
        assertSame('-', field.toChar('-'));
    }
}
