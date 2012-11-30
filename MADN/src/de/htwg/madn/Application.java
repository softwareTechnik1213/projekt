package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.controller.IBoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.ModelPort;
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
				
		IGameSettings settings = new GameSettings(
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
		
		IModelPort model = new ModelPort(settings, new Board(settings));
		
		IBoardController boardController = new BoardController(model);
		TUIView tui = new TUIView(boardController);
		// active waiting => infinite loop
		tui.iterateAndHandleInput();
	}
}
