package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.view.TUIView;

public final class Application {
	
	private static final int MINPLAYERS = 2;
	private static final int MAXPLAYERS = 4;
	private static final int FIGURESPERPLAYER = 4;
	private static final int PUBLICFIELDSCOUNT = 40;
	private static final int DICEMIN = 1;
	private static final int DICEMAX = 6;
	private static final int MINNUMBERTOEXITHOME = 6;
	private static final int THROWSALLOWEDINHOME = 3;
	private static final int THROWSALLOWEDINPUBLIC = 1;
	
	private Application() {
		
	}

	public static void main(String[] args) {

		GameSettings settings = new GameSettings(
				MINPLAYERS,
				MAXPLAYERS,
				FIGURESPERPLAYER,
				PUBLICFIELDSCOUNT,
				DICEMIN,
				DICEMAX,
				MINNUMBERTOEXITHOME,
				THROWSALLOWEDINHOME,
				THROWSALLOWEDINPUBLIC
		);
		Board board = new Board(settings);
		BoardController boardController = new BoardController(settings, board);
		TUIView tui = new TUIView(boardController);
		// active waiting => infinite loop
		tui.iterateAndHandleInput();
	}
}
