package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.assertSame;
import org.junit.Test;

import de.htwg.madn.model.Field;
import de.htwg.madn.model.Player;

public final class FieldTest {

	@Test
	public void testGetOccupier() {
		Player player = new Player(1, Color.RED, "test player");
		Field field = new Field();
		field.setOccupier(player);

		Player expected = field.getOccupier();

		assertSame(expected, player);
	}

}
