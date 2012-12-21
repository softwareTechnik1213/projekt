package de.htwg.madn.view.tui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.ModelPort;
import de.htwg.madn.model.Player;

public class DataToStringConverterTest {
	
	private static final int MINPLAYERS = 1;
	private static final int MAXPLAYERS = 1;
	private static final int FIGURESPERPLAYER = 1;
	private static final int PUBLICFIELDSCOUNT = 2;
	private static final int DICEMIN = 1;
	private static final int DICEMAX = 1;
	private static final int MINNUMBERTOEXITHOME = 1;
	private static final int THROWSALLOWEDINHOME = 3;
	private static final int THROWSALLOWEDINPUBLIC = 1;
	GameSettings settings;
	IModelPort model;
	IBoardControllerPort bc;
	private DataToStringConverter ds;

	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(MINPLAYERS, MAXPLAYERS, FIGURESPERPLAYER,
				PUBLICFIELDSCOUNT, DICEMIN, DICEMAX, MINNUMBERTOEXITHOME,
				THROWSALLOWEDINHOME, THROWSALLOWEDINPUBLIC);
		model = new ModelPort(settings, new Board(settings));
		bc = new BoardController(model);
		ds = new DataToStringConverter(settings);
	}


	@Test
	public void testGetPublicFieldsString() {
		bc.addPlayer("p1", Color.RED, true);			
		bc.startGame();
		bc.throwDice();
		bc.moveFigure('a');
		String ret = ds.getPublicFieldsString(model.getPublicField(), model);
		assertTrue(ret.contains("a"));
	}

	@Test
	public void testGetFinishFieldsString() {
		bc.addPlayer("p1", Color.RED, true);			
		bc.startGame();
		bc.throwDice();
		bc.moveFigure('a');
		bc.throwDice();
		bc.moveFigure('a');
		bc.throwDice();
		bc.moveFigure('a');
		String ret = ds.getFinishFieldsString(model.getFinishFields());
		assertTrue(ret.contains("a"));
	
	}

}
