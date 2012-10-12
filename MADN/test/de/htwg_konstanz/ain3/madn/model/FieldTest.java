package de.htwg_konstanz.ain3.madn.model;

import de.htwg_konstanz.ain3.madn.model.util.FieldType;
import java.awt.Color;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public final class FieldTest {

    @Test
    public void testGetPlayer() {
        Player player = new Player(1, Color.RED, "test player");
        Field field = new Field(FieldType.HOME);
        field.setPlayer(player);

        Player expected = field.getPlayer();

        assertSame(expected, player);
    }

    @Test
    public void testGetType() {
        FieldType expected = FieldType.HOME;
        Field field = new Field(expected);
        FieldType returnType = field.getType();

        assertSame(expected, returnType);
    }
}
