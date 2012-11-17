package de.htwg.madn.view;

import java.awt.Color;
import java.util.Scanner;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.FinishField;
import de.htwg.madn.model.HomeField;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObserver;

public class TUIView implements IObserver {

	private BoardController boardController;
	private static final Scanner SCANNER = new Scanner(System.in);

	public TUIView(BoardController bc) {
		this.boardController = bc;
		// watch the controller with this class
		this.boardController.addObserver(this);
		draw();
	}

	public boolean iterate() {
		// return true when UI quits, else false
		return handleInput(SCANNER.nextLine());
	}

	private boolean handleInput(String line) {
		// return true when UI should quit, else false
		boolean quit = false;
		String[] args;
		String cmd, parm;

		// split input into a command and an argument part
		args = getCommandAndArgument(line);

		if (args != null) {
			cmd = args[0];
			parm = args[1];
			quit = interpretInput(cmd, parm);
		} else {
			// error empty command!
			System.out.println("Leere Eingabe!");
			printPrompt();
		}

		return quit;
	}

	private boolean interpretInput(String cmd, String parm) {
		boolean quit = false;

		if (cmd.equals("q")) {
			// quit
			System.out.println("SPIEL BEENDET.");
			quit = true;
		} else if (cmd.equals("s")) {
			// start game
			boardController.startGame();
		} else if (cmd.equals("m") && parm != null && !parm.isEmpty()) {
			// move figure
			boardController.moveFigure(parm.charAt(0));
		} else if (cmd.equals("w")) {
			// throw dice
			boardController.throwDice();
		} else if (cmd.equals("add") && parm != null && !parm.isEmpty()) {
			// add player
			boardController.addPlayer(parm, Color.BLACK);
		} else {
			// error unknown parameter
			System.out.println("Falsche Eingabe!");
			printPrompt();
		}
		return quit;
	}

	private String[] getCommandAndArgument(String line) {
		String[] words = null;
		String[] ret = new String[2];

		if (line == null || line.isEmpty()) {
			return null;
		}

		words = line.split(" ");
		if (words.length == 0) {
			return null;
		}
		ret[0] = words[0].toLowerCase();
		// parameter like: add NAME
		if (words.length > 1) {
			ret[1] = words[1];
		}
		return ret;
	}

	@Override
	public void update() {
		draw();
	}

	private void draw() {
		System.out.println(getPlayerSettingString());
		System.out.println(getBoardString());
		System.out.println("STATUS: " + boardController.getStatusString());
		System.out.println(boardController.getActivePlayerString());
		System.out.println();
		printCommands();
		printPrompt();
	}

	private String getPlayerSettingString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Spieler-Liste:\n");
		for (Player p : boardController.getPlayerList()) {
			sb.append(p.getId()).append(": ").append(p.getName())
					.append(" hat Figuren: ");
			for (Figure fig : p.getFigures()) {
				sb.append(fig.getLetter()).append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private void printPrompt() {
		System.out.print(">>> ");
	}

	private void printCommands() {
		System.out.println(getCommands());
	}

	private String getCommands() {
		return "Befehle: 'q' quit | 's' start game | "
				+ "'add SpielerName' Spieler hinzufuegen\n"
				+ "'m Figurbuchstabe' Figur bewegen | 'w' Wuerfeln\n";
	}

	private String getBoardString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < boardController.getBoard().getPublicFieldsCount(); i++) {
			sb.append('-');
		}
		sb.append("\n");

		for (HomeField hf : boardController.getBoard().getHomeFields()) {
			sb.append(getHomeFieldString(hf)).append("      ");
		}
		sb.append("\n");

		for (int i = 0; i < boardController.getBoard().getPublicFieldsCount(); i++) {
			Figure fig = boardController.getBoard().getPublicField()
					.getFigure(i);
			if (fig != null) {
				sb.append(fig.getLetter());
			} else if (isSpecialPublicField(i)) {
				sb.append("*");
			} else {
				sb.append("_");
			}
		}
		sb.append("\n");

		for (FinishField ff : boardController.getBoard().getFinishFields()) {
			sb.append(getFinishFieldString(ff)).append("      ");
		}
		sb.append("\n");

		for (int i = 0; i < boardController.getBoard().getPublicFieldsCount(); i++) {
			sb.append('-');
		}

		return sb.toString();
	}

	private boolean isSpecialPublicField(int i) {
		Board board = boardController.getBoard();

		for (HomeField homeField : board.getHomeFields()) {
			if (homeField.getExitIndex() == i) {
				return true;
			}
		}

		for (FinishField finishField : board.getFinishFields()) {
			if (finishField.getEntryIndex() == i) {
				return true;
			}
		}

		return false;
	}

	private String getHomeFieldString(HomeField homeField) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < boardController.getBoard().getFiguresPerPlayer(); i++) {
			Figure fig = homeField.getFigure(i);
			if (fig == null) {
				sb.append("O");
			} else {
				sb.append(fig.getLetter());
			}
		}
		return sb.toString();
	}

	private String getFinishFieldString(FinishField finishField) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < boardController.getBoard().getFiguresPerPlayer(); i++) {
			Figure fig = finishField.getFigure(i);
			if (fig == null) {
				sb.append("#");
			} else {
				sb.append(fig.getLetter());
			}
		}
		return sb.toString();
	}
}
