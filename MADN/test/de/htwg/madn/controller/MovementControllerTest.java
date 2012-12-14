package de.htwg.madn.controller;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Dice;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.ModelPort;

public class MovementControllerTest {

	private Dice dice;
	private IGameSettings settings;
	private IModelPort model;
	private BoardController boardController;

	private static final int MINPLAYERS = 1;
	private static final int MAXPLAYERS = 2;
	private static final int FIGURESPERPLAYER = 1;
	private static final int PUBLICFIELDSCOUNT = 6;
	private static final int DICEMIN = 6;
	private static final int DICEMAX = 6;
	private static final int MINNUMBERTOEXITHOME = 1;
	private static final int THROWSALLOWEDINHOME = 3;
	private static final int THROWSALLOWEDINPUBLIC = 1;

	@Before
	public void setUp() throws Exception {

		settings = new GameSettings(MINPLAYERS, MAXPLAYERS, FIGURESPERPLAYER,
				PUBLICFIELDSCOUNT, DICEMIN, DICEMAX, MINNUMBERTOEXITHOME,
				THROWSALLOWEDINHOME, THROWSALLOWEDINPUBLIC);
		model = new ModelPort(settings, new Board(settings));
		
		boardController = new BoardController(model);
	
		this.dice = model.getDice();
		this.settings = model.getSettings();
	}

	@Test
	public void testMovementController() {
		fail("Not yet implemented");
	}

	@Test
	public void testThrowDice() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasActiveFigures() {
		fail("Not yet implemented");
	}

	@Test
	public void testMoveFigure() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartAutonomeRun() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatusString() {
		fail("Not yet implemented");
	}

}
