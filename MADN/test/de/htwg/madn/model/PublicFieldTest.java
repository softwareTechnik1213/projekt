package de.htwg.madn.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class PublicFieldTest {

	private Player occupier;
	private IBoard board;
	private IGameSettings settings;
	private PublicField publicField;
	private int size;

	@Before
	public void setUp() throws Exception {
		size = 40;
		settings = new GameSettings(1, 1, 2, size, 1, 6, 6, 3, 1);
		board = new Board(settings);
		occupier = new Player(0, Color.RED, "Test", board.getHomeFields().get(0),
				board.getFinishFields().get(0), settings.getFiguresPerPlayer(), true);
		publicField = board.getPublicField();
	}

	@Test
	public void testPublicField() {
		assertTrue(publicField != null);
	}

	@Test
	public void testRemoveFigure() {
		Figure fig = occupier.getFigures().get(0);
		publicField.setFigure(0, fig);
		publicField.removeFigure(0);
		assertTrue(publicField.getFigure(0) == null);

		try {
			publicField.getFigure(size + 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			publicField.getFigure(-1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}
	}

	@Test
	public void testGetSize() {
		assertTrue(publicField.getSize() == size);
	}

}
