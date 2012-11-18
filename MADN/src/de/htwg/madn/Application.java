package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.view.TUIView;

public final class Application {

	private Application() {
		throw new UnsupportedOperationException("static class");
	}

	public static void main(String[] args) {

		GameSettings rules = new GameSettings(2, 4, 4, 40, 1, 6, 6, 3, 1);
		
		BoardController boardController = new BoardController(rules);
		TUIView tui = new TUIView(boardController);

		// continue until the user decides to quit
		boolean quit = false;
		while (!quit) {
			quit = tui.iterate();
		}
	}
}
