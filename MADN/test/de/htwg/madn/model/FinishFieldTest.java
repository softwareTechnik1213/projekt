package de.htwg.madn.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public final class FinishFieldTest {

	private Player owner;
	private IBoard board;
	private IGameSettings settings;
	private FinishField finishField;
	
	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(1, 1, 4, 40, 1, 6, 6, 3, 1);
		board = new Board(settings);
		owner = new Player(0, Color.RED, "Test",
				board.getHomeFields().get(0), board.getFinishFields().get(0),
				settings.getFiguresPerPlayer(), true);
		finishField = board.getFinishFields().get(0);
	}

	@Test
	public void testFinishField() {
		assertTrue(finishField != null);
	}

	@Test
	public void testSetOwner() {
		finishField.setOwner(null);
		finishField.setOwner(owner);
		assertTrue(finishField.getOwner() == owner);
	}
	
	@Test
	public void testGetEntryIndex() {
		assertTrue(finishField.getEntryIndex() == settings.getPublicFieldsCount() - 1);
	}
	
	@Test
	public void testGetExitIndex() {
		try {
			finishField.getExitIndex();
			fail("should throw exception");
		} catch (Exception expected) {
			// ok is excepted, let test pass
		}
	}

	@Test
	public void testRemoveFigure() {
		Figure fig = owner.getFigures().get(0);
		finishField.setFigure(0, fig);
		finishField.removeFigure(0);
		assertTrue(finishField.getFigure(0) == null);
		
		try {
			finishField.getFigure(settings.getFiguresPerPlayer() + 1);
			fail("should throw exception");
		} catch (Exception expected) {
			// ok is excepted, let test pass
		}
		
		try {
			finishField.getFigure(-1);
			fail("should throw exception");
		} catch (Exception expected) {
			// ok is excepted, let test pass
		}
	}

	@Test
	public void testIsEmpty() {
		assertTrue(finishField.isEmpty());
		finishField.setFigure(0, owner.getFigures().get(0));
		assertTrue(!finishField.isEmpty());
	}
	
	@Test
	public void testGetSize() {
		assertTrue(finishField.getSize() == 4);
	}

}
