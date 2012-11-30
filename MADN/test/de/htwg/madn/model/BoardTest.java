package de.htwg.madn.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public final class BoardTest {

	private IBoard board;
	private IGameSettings settings;
	private int figuresPerPlayer = 1;
	private int maxPlayers = 1;
	private int minPlayers = 1;
	private int publicFieldsCount = 2;

	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(minPlayers, maxPlayers, figuresPerPlayer,
				publicFieldsCount, 1, 6, 6, 3, 1);
		board = new Board(settings);
	}

	@Test
	public void testBoard() {
		assertTrue(board != null);
	}
	
	@Test
	public void testReset() {
		board.reset();
		assertTrue(board.getPlayers().size() == 0);
	}

	@Test
	public void testGetExitIndexHome() {
		assertTrue(board.getExitIndexHome(0) == 0);
		assertTrue(board.getExitIndexHome(1) == 1 * publicFieldsCount
				/ maxPlayers);
	}

	@Test
	public void testGetEntryIndexFinish() {
		assertTrue(board.getEntryIndexFinish(0) == (board.getExitIndexHome(0)
				+ publicFieldsCount - 1)
				% publicFieldsCount);
		assertTrue(board.getEntryIndexFinish(4) == (board.getExitIndexHome(4)
				+ publicFieldsCount - 1)
				% publicFieldsCount);
	}

	@Test
	public void testAddPlayer() {
		assertTrue(board.addPlayer(Color.RED, "test", true) != null);
		assertTrue(board.getPlayers().size() == 1);
		for (int i = 0; i < maxPlayers; i++) {
			board.addPlayer(Color.RED, "test", true);
		}
		assertTrue(board.addPlayer(Color.RED, "test", true) == null);
	}

	@Test
	public void testGetHomeFields() {
		assertTrue(board.getHomeFields().size() == maxPlayers);
	}

	@Test
	public void testGetFinishFields() {
		assertTrue(board.getFinishFields().size() == maxPlayers);
	}

	@Test
	public void testGetPublicField() {
		assertTrue(board.getPublicField().getSize() == publicFieldsCount);
	}

	@Test
	public void testGetPlayers() {
		board.addPlayer(Color.RED, "test", true);
		assertTrue(board.getPlayers().size() == 1);
	}

	@Test
	public void testGetDice() {
		assertTrue(board.getDice() != null);
	}

	@Test
	public void testGetFigureForPlayerByLetter() {
		Player player = board.addPlayer(Color.RED, "test", true);
		assertTrue(board.getFigureForPlayerByLetter(player, 'a') != null);
		assertTrue(board.getFigureForPlayerByLetter(player, 'z') == null);

		try {
			board.getFigureForPlayerByLetter(player, '5');
			fail("should throw exception");
		} catch (Exception expected) {
			// this is exactly what we were expecting so
			// let's just ignore it and let the test pass
		}
		
		try {
			board.getFigureForPlayerByLetter(null, 'a');
			fail("should throw exception");
		} catch (Exception expected) {
			// this is exactly what we were expecting so
			// let's just ignore it and let the test pass
		}
		
		
	}

}
