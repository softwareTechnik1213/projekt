package de.htwg.madn;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.ModelPort;
import de.htwg.madn.view.gui.GUIView;
import de.htwg.madn.view.tui.TUIView;

public final class Application {
	
	private static final int MINPLAYERS = 1;
	private static final int MAXPLAYERS = 2;
	private static final int FIGURESPERPLAYER = 4;
	private static final int PUBLICFIELDSCOUNT = 20;
	private static final int DICEMIN = 1;
	private static final int DICEMAX = 6;
	private static final int MINNUMBERTOEXITHOME = 6;
	private static final int THROWSALLOWEDINHOME = 3;
	private static final int THROWSALLOWEDINPUBLIC = 1;
	private static final Scanner SCANNER = new Scanner(System.in);
	
	private Application() {
		 
	}

	public static void main(String[] args) {
				
		PropertyConfigurator.configure("log4j.properties");
		
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
		
		IBoardControllerPort boardController = new BoardController(model);
		
		new GUIView(boardController);
		
		TUIView tui = new TUIView(boardController);
		// active waiting => infinite loop
		boolean quit = false;
		while (!quit) {
			quit = tui.handleInput(SCANNER.nextLine());
		}
	}
}
