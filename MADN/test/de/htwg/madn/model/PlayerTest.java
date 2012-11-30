package de.htwg.madn.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public final class PlayerTest {

	private Player player;
	private IBoard board;
		
	@Before
	public void setUp() throws Exception {
		IGameSettings settings = new GameSettings(1, 1, 4,
				40, 1, 6, 6, 3, 1);
		board = new Board(settings);
		player = board.addPlayer(Color.RED, "test dummy", true);
	}
	
	@Test
	public void testConstructor() {
		try {
			new Player(0, Color.RED, "test", null, board.getFinishFields().get(0), 4, true);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}
		
		try {
			new Player(0, Color.RED, "test", board.getHomeFields().get(0), null, 4, true);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}
	}

	@Test
	public void testPlayer() {
		assertTrue(player != null);
	}

	@Test
	public void testGetId() {
		assertTrue(player.getId() == 0);
	}

	@Test
	public void testGetColor() {
		assertTrue(player.getColor() == Color.RED);
	}

	@Test
	public void testGetName() {
		assertTrue(player.getName().equals("test dummy"));
	}

	@Test
	public void testHasFigure() {
		Figure fig = player.getFigures().get(0);
		assertTrue(player.hasFigure(fig));
		Figure unknownFig = new Figure('a', null);
		assertTrue(!player.hasFigure(unknownFig));
	}

	@Test
	public void testGetFinishField() {
		assertTrue(player.getFinishField() != null);
	}

	@Test
	public void testGetHomeField() {
		assertTrue(player.getHomeField() != null);
	}
	
	@Test
	public void testIsHuman() {
		assertTrue(player.isHuman() == true);
	}

}
