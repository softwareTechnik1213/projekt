package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.view.TUIView;

public final class Application {
	
	private static final int minPlayers = 2;
	private static final int maxPlayers = 4;
	private static final int figuresPerPlayer = 4;
	private static final int publicFieldsCount = 40;
	private static final int diceMin = 1;
	private static final int diceMax = 6;
	private static final int minNumberToExitHome = 6;
	private static final int throwsAllowedInHome = 3;
	private static final int throwsAllowedInPublic = 1;

	public static void main(String[] args) {

		GameSettings settings = new GameSettings(
				minPlayers,
				maxPlayers,
				figuresPerPlayer,
				publicFieldsCount,
				diceMin,
				diceMax,
				minNumberToExitHome,
				throwsAllowedInHome,
				throwsAllowedInPublic
		);
		Board board = new Board(settings);
		BoardController boardController = new BoardController(settings, board);
		TUIView tui = new TUIView(boardController);
		// active waiting => infinite loop
		tui.iterateAndHandleInput();
	}
}
