package de.htwg.madn.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class HomeFieldTest {

	private Player owner;
	private IBoard board;
	private IGameSettings settings;
	private HomeField homeField;

	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(1, 1, 40, 40, 1, 6, 6, 3, 1);
		board = new Board(settings);
		owner = new Player(0, Color.RED, "Test", board.getHomeFields().get(0),
				board.getFinishFields().get(0), settings.getFiguresPerPlayer(), true);
		homeField = board.getHomeFields().get(0);
	}

	@Test
	public void testFinishField() {
		assertTrue(homeField != null);
	}

	@Test
	public void testSetOwner() {
		homeField.setOwner(null);
		homeField.setOwner(owner);
		assertTrue(homeField.getOwner() == owner);
	}
	
	@Test
	public void testGetEntryIndex() {
		try {
			homeField.getEntryIndex();
			fail("should throw exception");
		} catch (Exception expected) {
			// ok is excepted, let test pass
		}
	}

	@Test
	public void testGetExitIndex() {
		assertTrue(homeField.getExitIndex() == 0);
	}

	@Test
	public void testRemoveFigure() {
		Figure fig = owner.getFigures().get(0);
		homeField.setFigure(0, fig);
		homeField.removeFigure(0);
		assertTrue(homeField.getFigure(0) == null);

		try {
			homeField.getFigure(settings.getFiguresPerPlayer() + 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			homeField.getFigure(-1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}
	}

	@Test
	public void testIsEmpty() {
		assertTrue(!homeField.isEmpty());
		homeField.setFigure(0, owner.getFigures().get(0));
		for (int i = 0; i < settings.getFiguresPerPlayer(); i++) {
			homeField.removeFigure(i);
		}
		assertTrue(homeField.isEmpty());
	}
	
	@Test
	public void testGetSize() {
		assertTrue(homeField.getSize() == 40);
	}

}
