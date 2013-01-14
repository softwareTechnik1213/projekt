package de.htwg.madn.controller;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.apache.log4j.PropertyConfigurator;
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
import de.htwg.madn.view.tui.TUIView;

public class MovementControllerTest {

	private Dice dice;
	private IGameSettings settings;
	private IModelPort model;
	private BoardController bc;
	private MovementController mc;

	private static final int MINPLAYERS = 1;
	private static final int MAXPLAYERS = 2;
	private static final int FIGURESPERPLAYER = 2;
	private static final int PUBLICFIELDSCOUNT = 6;
	private static final int DICEMIN = 1;
	private static final int DICEMAX = 1;
	private static final int MINNUMBERTOEXITHOME = 1;
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
		mc.throwDice(p);
		mc.throwDice(p);
		mc.throwDice(p);
		mc.throwDice(p);
		dice.setThrowsCount(1);
		mc.moveFigure(p, p.getFigures().get(0).getLetter());
		mc.throwDice(p);
		mc.moveFigure(p, p.getFigures().get(0).getLetter());
		mc.throwDice(p);
		mc.throwDice(p);
		mc.throwDice(p);
		dice.setThrowsCount(-5);
		mc.throwDice(p);

	}
	
	@Test
	public void testGetLastFreeFinishIndex() {
		bc.addPlayer("test", Color.red, true);
		bc.addPlayer("test2", Color.red, true);
		bc.startGame();
		
		Figure f1 = bc.getPlayers().get(0).getFigures().get(0);
		Figure f2 = bc.getPlayers().get(0).getFigures().get(0);
		
		// move f1 out
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		// move f2 out
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		
		for (int i = 0; i < 7; i++) {
			bc.throwDice();	
			bc.moveFigure(f1.getLetter());
			bc.throwDice();	
			bc.moveFigure(f2.getLetter());
		}
		
		

	}

	@Test
	public void testKickFigureIfPossible() {
		bc.addPlayer("test", Color.red, true);
		bc.addPlayer("test2", Color.red, true);
		bc.startGame();

		Player p1 = model.getPlayers().get(0);
		
		Figure toKick = model.getHomeFields().get(1).getFigure(1);
		model.getHomeFields().get(1).removeFigure(1);
		model.getPublicField().setFigure(0, toKick);

		bc.throwDice();
		Figure kicker = p1.getFigures().get(0);
		bc.moveFigure(kicker.getLetter());

		assertTrue(model.getPublicField().getFigure(0) == kicker);
		assertTrue(model.getHomeFields().get(1).getFigure(1) == toKick);
	}

	@Test
	public void testHasThrowsLeft() {
		bc.addPlayer("test", Color.red, true);
		Figure fig = bc.getPlayers().get(0).getFigures().get(0);
		
		bc.startGame();
		
		bc.throwDice();
		bc.moveFigure(fig.getLetter());
		bc.throwDice();
		bc.moveFigure(fig.getLetter());
		bc.throwDice();
		bc.throwDice();
		bc.throwDice();
		bc.throwDice();
	}
	
	@Test
	public void testCheckMarkAllFinished() {
		bc.addPlayer("test", Color.red, true);
		Figure f1 = bc.getPlayers().get(0).getFigures().get(0);
		Figure f2 = bc.getPlayers().get(0).getFigures().get(1);
		
		bc.startGame();
		
		// move all out
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();

		
		bc.reset();
		
		bc.addPlayer("testCheckMarkAll", Color.red, true);
		Player player = bc.getPlayers().get(0);
		f1 = player.getFigures().get(0);
		f2 = player.getFigures().get(1);
		
		bc.startGame();
		
		PropertyConfigurator.configure("log4j.properties");
		new TUIView(bc);
		
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		bc.throwDice();
		bc.moveFigure(f1.getLetter());
		bc.throwDice();
		bc.moveFigure(f2.getLetter());
		
		
		
		assertTrue(f1.isFinished());
		assertTrue(f2.isFinished());
	}

	@Test
	public void testGetStatusString() {
		assertTrue(mc.getStatusString() != null);
	}

}
