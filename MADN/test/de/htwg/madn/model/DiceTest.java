package de.htwg.madn.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public final class DiceTest {

	private Dice dice;
	private Player thrower;
	private IBoard board;
	private IGameSettings settings;
	private int min;
	private int max;

	@Before
	public void setUp() throws Exception {
		min = 1;
		max = 6;
		dice = new Dice(min, max);
		settings = new GameSettings(1, 1, 40, 40, 1, 6, 6, 3, 1);
		board = new Board(settings);
		thrower = new Player(0, Color.RED, "Test",
				board.getHomeFields().get(0), board.getFinishFields().get(0),
				settings.getFiguresPerPlayer(), true);
	}

	@Test
	public void testDice() {
		assertTrue(dice != null);
	}

	@Test
	public void testThrowDice() {
		assertTrue(dice.throwDice(thrower) >= settings.getDiceMin());
		assertTrue(dice.throwDice(thrower) <= settings.getDiceMax());
	}

	@Test
	public void testGetLastNumber() {
		try {
			dice.getLastNumber();
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		int lastNumber = dice.throwDice(thrower);
		assertTrue(lastNumber == dice.getLastNumber());
	}

	@Test
	public void testGetLastThrower() {
		dice.throwDice(thrower);
		assertTrue(thrower == dice.getLastThrower());
	}

	@Test
	public void testGetThrowsCount() {
		dice.throwDice(thrower);
		assertTrue(1 == dice.getThrowsCount());
	}

	@Test
	public void testResetThrowsCount() {
		dice.throwDice(thrower);
		dice.resetThrowsCount();
		assertTrue(0 == dice.getThrowsCount());
	}

}
