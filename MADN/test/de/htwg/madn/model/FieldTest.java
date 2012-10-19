package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.assertSame;
import org.junit.Test;

import de.htwg.madn.model.Field;
import de.htwg.madn.model.Player;

public final class FieldTest {

    @Test
    public void testGetPlayer() {
        Player player = new Player(1, Color.RED, "test player");
        Field field = new Field(Field.Type.HOME);
        field.setPlayer(player);

        Player expected = field.getPlayer();

        assertSame(expected, player);
    }

    @Test
    public void testGetType() {
        Field.Type expected = Field.Type.HOME;
        Field field = new Field(expected);
        Field.Type returnType = field.getType();

        assertSame(expected, returnType);
    }
}
