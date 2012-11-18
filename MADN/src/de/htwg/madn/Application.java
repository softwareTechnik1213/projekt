package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.view.TUIView;

public final class Application { 

	private Application() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BoardController boardController = new BoardController(new Board());
		TUIView tui = new TUIView(boardController);

		// continue until the user decides to quit
		boolean quit = false;
		while (!quit) {
			quit = tui.iterate();
		}
	}
}
