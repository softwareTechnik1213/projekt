package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.view.TUIView;

public final class Application {

	public static void main(String[] args) {

		GameSettings settings = new GameSettings(2, 4, 4, 40, 1, 6, 6, 3, 1);
		Board board = new Board(settings);
		BoardController boardController = new BoardController(settings, board);
		TUIView tui = new TUIView(boardController);
		// active waiting => infinite loop
		tui.iterateAndHandleInput();
	}
}
