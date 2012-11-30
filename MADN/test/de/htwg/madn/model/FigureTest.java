package de.htwg.madn.model;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public final class FigureTest {

	private Figure figure;
	private Player owner;
	private char letter;

	@Before
	public void setUp() throws Exception {
		IGameSettings settings = new GameSettings(1, 1, 4,
				40, 1, 6, 6, 3, 1);
		IBoard board = new Board(settings);
		owner = board.addPlayer(Color.RED, "test dummy", true);
		letter = 'a';
		figure = new Figure(letter, owner);
	}

	@Test
	public void testFigure() {
		assertTrue(figure != null);
	}

	@Test
	public void testGetOwner() {
		assertTrue(figure.getOwner() == owner);
	}

	@Test
	public void testGetLetter() {
		assertTrue(figure.getLetter() == letter);
	}

	@Test
	public void testIsAtHomeArea() {
		figure.setAtHomeArea(true);
		assertTrue(figure.isAtHomeArea());
	}

	@Test
	public void testIsAtFinishArea() {
		figure.setAtFinishArea(true);
		assertTrue(figure.isAtFinishArea());
	}

	@Test
	public void testGetCurrentFieldIndex() {
		int i = 5;
		figure.setCurrentFieldIndex(i);
		assertTrue(figure.getCurrentFieldIndex() == i);
	}

	@Test
	public void testIsFinished() {
		figure.setFinished(true);
		assertTrue(figure.isFinished());
	}

}
