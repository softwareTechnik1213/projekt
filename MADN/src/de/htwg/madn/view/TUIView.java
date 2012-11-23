package de.htwg.madn.view;

import java.awt.Color;
import java.util.Queue;
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

	public void iterateAndHandleInput() {
		while (true) {
			handleInput(SCANNER.nextLine());
		}
	}

	private void handleInput(String line) {;
		String[] args;
		String cmd, parm;

		// split input into a command and an argument part
		args = getCommandAndArgument(line);

		if (args != null) {
			cmd = args[0];
			parm = args[1];
			interpretInput(cmd, parm);
		} else {
			// error empty command!
			System.out.println("Leere Eingabe!");
			printPrompt();
		}
	}

	private void interpretInput(String cmd, String parm) {
		if (cmd.equals("q")) {
			// quit
			quitGame();
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
	}

	private void quitGame() {
		printWinners();
		System.out.println("SPIEL BEENDET");
		boardController.quitGame();
	}

	private void printWinners() {
		Queue<Player> finishedPlayers = boardController.getFinishedPlayersQueue();
		StringBuilder sb = new StringBuilder();
		sb.append("Platzierung\tSpielername\n");
		for (int i = 0; i < finishedPlayers.size(); i++) {
			sb.append(i + 1)
				.append("\t")
				.append(finishedPlayers.poll().getName())
				.append("\n");
		}
		System.out.println(sb.toString());
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
		System.out.print("STATUS: " + boardController.getStatusString());
		String activePlayer = boardController.getActivePlayerString();
		if (activePlayer != null) {
			System.out.print(", Spieler " + activePlayer + " ist am Zug.");
		}
		System.out.println();
		System.out.println();
		printCommands();
		printPrompt();
	}

	private String getPlayerSettingString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Spieler-Liste:\n");
		for (Player p : boardController.getPlayers()) {
			sb.append(p.getId()).append(": ").append(p.getName())
					.append(" hat Figuren: ");
			for (Figure fig : p.getFigures()) {
				sb.append(fig.getLetter()).append(" ");
			}
			sb.append("\n");
		}
		// cut away the last newline
		return sb.substring(0, sb.length() - 1);
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

		appendBoardBorder(sb);
		sb.append("\n");
		appendHomeFields(sb);
		appendPublicFields(sb);
		appendFinishFields(sb);
		appendBoardBorder(sb);

		return sb.toString();
	}

	private void appendBoardBorder(StringBuilder sb) {
		for (int i = 0; i < boardController.getSettings().getPublicFieldsCount(); i++) {
			sb.append('-');
		}
	}

	private void appendHomeFields(StringBuilder sb) {
		for (HomeField hf : boardController.getBoard().getHomeFields()) {
			sb.append(getHomeFieldString(hf));
			appendDistanceBetweenSpecialFields(sb);
		}
		sb.append("\n");
	}

	private void appendDistanceBetweenSpecialFields(StringBuilder sb) {
		int publicFieldsCount = boardController.getSettings().getPublicFieldsCount();
		int numberSpecialFields = boardController.getSettings().getMaxPlayers();
		int numberFigures = boardController.getSettings().getFiguresPerPlayer();

		int distance = publicFieldsCount / numberSpecialFields - numberFigures;

		for (int i = 0; i < distance; i++) {
			sb.append(' ');
		}
	}

	private void appendFinishFields(StringBuilder sb) {
		for (FinishField ff : boardController.getBoard().getFinishFields()) {
			sb.append(getFinishFieldString(ff));
			appendDistanceBetweenSpecialFields(sb);
		}
		sb.append("\n");
	}

	private void appendPublicFields(StringBuilder sb) {
		for (int i = 0; i < boardController.getSettings().getPublicFieldsCount(); i++) {
			Figure fig = boardController.getBoard().getPublicField()
					.getFigure(i);
			if (fig != null) {
				sb.append(fig.getLetter());
			} else if (isSpecialPublicFieldHomeExit(i)) {
				sb.append("*");
			} else if (isSpecialPublicFieldFinishEntry(i)) {
				sb.append("/");
			} else {
				sb.append("_");
			}
		}
		sb.append("\n");
	}

	private boolean isSpecialPublicFieldHomeExit(int i) {
		Board board = boardController.getBoard();

		for (HomeField homeField : board.getHomeFields()) {
			if (homeField.getExitIndex() == i) {
				return true;
			}
		}

		return false;
	}

	private boolean isSpecialPublicFieldFinishEntry(int i) {
		Board board = boardController.getBoard();

		for (FinishField finishField : board.getFinishFields()) {
			if (finishField.getEntryIndex() == i) {
				return true;
			}
		}

		return false;
	}

	private String getHomeFieldString(HomeField homeField) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < boardController.getSettings().getFiguresPerPlayer(); i++) {
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
		for (int i = 0; i < boardController.getSettings().getFiguresPerPlayer(); i++) {
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
