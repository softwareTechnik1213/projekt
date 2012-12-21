package de.htwg.madn.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Dice;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.ModelPort;
import de.htwg.madn.model.Player;

public class MovementControllerTest {

	private Dice dice;
	private IGameSettings settings;
	private IModelPort model;
	private BoardController bc;
	private MovementController mc;

	private static final int MINPLAYERS = 1;
	private static final int MAXPLAYERS = 2;
	private static final int FIGURESPERPLAYER = 1;
	private static final int PUBLICFIELDSCOUNT = 6;
	private static final int DICEMIN = 4;
	private static final int DICEMAX = 4;
	private static final int MINNUMBERTOEXITHOME = 3;
	private static final int THROWSALLOWEDINHOME = 3;
	private static final int THROWSALLOWEDINPUBLIC = 1;

	@Before
	public void setUp() throws Exception {

		reset(THROWSALLOWEDINPUBLIC, THROWSALLOWEDINHOME);
		
	}
	
	private void reset(int throwspublic, int throwshome) {
		settings = new GameSettings(MINPLAYERS, MAXPLAYERS, FIGURESPERPLAYER,
				PUBLICFIELDSCOUNT, DICEMIN, DICEMAX, MINNUMBERTOEXITHOME,
				throwshome, throwspublic);
		model = new ModelPort(settings, new Board(settings));
		
		bc = new BoardController(model);
		mc = new MovementController(model);
		
		this.dice = model.getDice();
		this.settings = model.getSettings();
	}

	@Test
	public void testMovementController() {
		MovementController contr = new MovementController(model);
		assertTrue(contr != null);
	}

	@Test
	public void testThrowDice() {
		bc.addPlayer("test", Color.red, true);
		bc.startGame();
		Player p = bc.getActivePlayer();
		assertTrue(mc.throwDice(p) == false);
		assertTrue(mc.throwDice(p) == false);
		assertTrue(mc.throwDice(p) == false);
		dice.throwDice(p);
		dice.throwDice(p);
		dice.throwDice(p);
		dice.throwDice(p);
	}

	@Test
	public void testMoveFigure() {
		bc.addPlayer("test", Color.red, true);
		bc.addPlayer("test2", Color.red, true);
		bc.startGame();
		Player p = bc.getActivePlayer();
		mc.throwDice(p) ;
		mc.throwDice(p) ;
		mc.throwDice(p) ;
		mc.throwDice(p) ;
		dice.setThrowsCount(1);
		mc.moveFigure(p, p.getFigures().get(0).getLetter());
		mc.throwDice(p) ;
		mc.moveFigure(p, p.getFigures().get(0).getLetter());
		mc.throwDice(p) ;
		mc.throwDice(p) ;
		mc.throwDice(p) ;
		dice.setThrowsCount(-5);
		mc.throwDice(p) ;

	}
	
	@Test
	public void testHasThrowsLeft() {
		
		
	}

	@Test
	public void testGetStatusString() {
		assertTrue (mc.getStatusString() != null);
	}

}
