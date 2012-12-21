package de.htwg.madn.view.tui;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.ModelPort;

public class TUIViewTest {

	private static final int MINPLAYERS = 1;
	private static final int MAXPLAYERS = 1;
	private static final int FIGURESPERPLAYER = 1;
	private static final int PUBLICFIELDSCOUNT = 2;
	private static final int DICEMIN = 1;
	private static final int DICEMAX = 1;
	private static final int MINNUMBERTOEXITHOME = 1;
	private static final int THROWSALLOWEDINHOME = 3;
	private static final int THROWSALLOWEDINPUBLIC = 1;
	private static final Scanner SCANNER = new Scanner(System.in);
	private TUIView tui;
	private IBoardControllerPort boardController;
	private IModelPort model;
	private IGameSettings settings;

	@Before
	public void setUp() throws Exception {
		settings = new GameSettings(MINPLAYERS, MAXPLAYERS,
				FIGURESPERPLAYER, PUBLICFIELDSCOUNT, DICEMIN, DICEMAX,
				MINNUMBERTOEXITHOME, THROWSALLOWEDINHOME, THROWSALLOWEDINPUBLIC);

		model = new ModelPort(settings, new Board(settings));

		boardController = new BoardController(model);
		tui = new TUIView(boardController);
		// active waiting => infinite loop
			
	}


	@Test
	public void testHandleInput() {
		assertTrue(!tui.handleInput("add p1"));
		assertTrue(!tui.handleInput("add"));
		assertTrue(!tui.handleInput("w"));
		assertTrue(!tui.handleInput("s"));
		assertTrue(!tui.handleInput("m a"));
		assertTrue(!tui.handleInput("m"));
		assertTrue(!tui.handleInput("r"));
		
		assertTrue(!tui.handleInput("asdasdas"));
		assertTrue(!tui.handleInput(""));
		assertTrue(!tui.handleInput("  "));
		
		assertTrue(tui.handleInput("q"));
	}
	
	@Test
	public void finish() {
		assertTrue(!tui.handleInput("add p1"));
		assertTrue(!tui.handleInput("add"));		
		assertTrue(!tui.handleInput("s"));
		assertTrue(!tui.handleInput("w"));
		assertTrue(!tui.handleInput("m a"));
		assertTrue(!tui.handleInput("w"));
		assertTrue(!tui.handleInput("m a"));
		assertTrue(!tui.handleInput("w"));
		assertTrue(!tui.handleInput("m a"));
		
	}
	


}
