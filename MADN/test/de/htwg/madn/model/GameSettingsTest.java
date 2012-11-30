package de.htwg.madn.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GameSettingsTest {

	private final int minPlayers = 1;
	private final int maxPlayers = 2;
	private final int figuresPerPlayer = 2;
	private final int publicFieldsCount = 20;
	private final int diceMin = 1;
	private final int diceMax = 6;
	private final int minNumberToExitHome = 6;
	private final int throwsAllowedInHome = 3;
	private final int throwsAllowedInPublic = 1;
	private IGameSettings settings;

	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(minPlayers, maxPlayers, figuresPerPlayer,
				publicFieldsCount, diceMin, diceMax, minNumberToExitHome,
				throwsAllowedInHome, throwsAllowedInPublic);
	}

	@Test
	public void testVerifySettings() {
		try {
			new GameSettings(2, 1, 4, 40, 1, 6, 6, 3, 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			new GameSettings(0, 1, 4, 40, 1, 6, 6, 3, 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			new GameSettings(2, 4, 0, 40, 1, 6, 6, 3, 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			new GameSettings(2, 4, 4, 0, 1, 6, 6, 3, 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			new GameSettings(2, 4, 4, 43, 1, 6, 6, 3, 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			new GameSettings(2, 4, 4, 40, 1, 6, 6, 0, 1);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

		try {
			new GameSettings(2, 4, 4, 40, 1, 6, 6, 3, 0);
			fail("should throw exception");
		} catch (Exception excpected) {
			// ok is excepted, let test pass
		}

	}

	@Test
	public void testGameSettings() {
		assertTrue(settings != null);
	}

	@Test
	public void testGetMinPlayers() {
		assertTrue(settings.getMinPlayers() == minPlayers);
	}

	@Test
	public void testGetMaxPlayers() {
		assertTrue(settings.getMaxPlayers() == maxPlayers);
	}

	@Test
	public void testGetFiguresPerPlayer() {
		assertTrue(settings.getFiguresPerPlayer() == figuresPerPlayer);
	}

	@Test
	public void testGetPublicFieldsCount() {
		assertTrue(settings.getPublicFieldsCount() == publicFieldsCount);
	}

	@Test
	public void testGetDiceMin() {
		assertTrue(settings.getDiceMin() == diceMin);
	}

	@Test
	public void testGetDiceMax() {
		assertTrue(settings.getDiceMax() == diceMax);
	}

	@Test
	public void testGetMinNumberToExitHome() {
		assertTrue(settings.getMinNumberToExitHome() == minNumberToExitHome);
	}

	@Test
	public void testGetThrowsAllowedInHome() {
		assertTrue(settings.getThrowsAllowedInHome() == throwsAllowedInHome);
	}

	@Test
	public void testGetThrowsAllowedInPublic() {
		assertTrue(settings.getThrowsAllowedInPublic() == throwsAllowedInPublic);
	}

}
