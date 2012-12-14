package de.htwg.madn.model;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class ModelPortTest {

	private IGameSettings settings;
	private int figuresPerPlayer = 1;
	private int maxPlayers = 1;
	private int minPlayers = 1;
	private int publicFieldsCount = 2;
	private IModelPort model;

	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(minPlayers, maxPlayers, figuresPerPlayer,
				publicFieldsCount, 1, 6, 6, 3, 1);
		model = new ModelPort(settings, new Board(settings));
	}
	@Test
	public void testModelPort() {
		model.reset();
		model = new ModelPort(settings, new Board(settings));
		assertTrue(model!=null);
		
	}
	@Test
	public void testReset() {
		model = new ModelPort(settings, new Board(settings));
		assertTrue(model!=null);		
	}
	@Test
	public void testGetSettings() {
			assertTrue(model.getSettings()!=null);
	}
	@Test
	public void testGetDice() {
		assertTrue(model.getDice()!=null);
	}
	@Test
	public void testGetPlayers() {
		model.addPlayer(Color.red,"testplayer",true);
		assertTrue(!model.getPlayers().isEmpty());
	}
	@Test
	public void getPublicField() {		
		assertTrue(model.getPublicField()!=null);
	}
	@Test
	public void testetFigureForPlayerByLetter() {
		Player player = model.addPlayer(Color.red,"testplayer",true);
		assertTrue(model.getFigureForPlayerByLetter(player, player.getFigures().get(0).getLetter())!=null);
	}

	@Test
	public void testGetHomeFields() {
		assertTrue(model.getHomeFields()!=null);
	}

	@Test
	public void testGetFinishFields() {
		assertTrue(model.getFinishFields()!=null);
	}

}
