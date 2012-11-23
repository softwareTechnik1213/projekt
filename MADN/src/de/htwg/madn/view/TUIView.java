package de.htwg.madn.view;

import java.awt.Color;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.FinishField;
import de.htwg.madn.model.HomeField;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObserver;

public class TUIView implements IObserver {

	private final BoardController boardController;
	private static Logger log;
	private static final Scanner SCANNER = new Scanner(System.in);

	public TUIView(BoardController bc) {
		this.boardController = bc;
		// watch the controller with this class
		this.boardController.addObserver(this);
		setupLogger();
		draw();
	}

	private void setupLogger() {
		log = Logger.getLogger(TUIView.class.getName());
		// disable root logger handler
		log.setUseParentHandlers(false);
		// add owner behavior
		log.addHandler(getLogHandler());
	}
	
	private Handler getLogHandler() {
		return new Handler() {
			@Override
			public void publish(LogRecord record) {
				System.out.println(record.getMessage());
			}

			public void flush() {
			}

			public void close() {
			}

		};
	}

	public void iterateAndHandleInput() {
		while (true) {
			handleInput(SCANNER.nextLine());
		}
	}

	private void handleInput(String line) {
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
			log.info("Leere Eingabe!");
		}
	}

	private void interpretInput(String cmd, String parm) {
		if (cmd.equals("q")) {
			quitGame();
		} else if (cmd.equals("s")) {
			boardController.startGame();
		} else if (cmd.equals("m") && parm != null && !parm.isEmpty()) {
			boardController.moveFigure(parm.charAt(0));
		} else if (cmd.equals("w")) {
			boardController.throwDice();
		} else if (cmd.equals("add") && parm != null && !parm.isEmpty()) {
			boardController.addPlayer(parm, Color.BLACK);
		} else if (cmd.equals("r")) {
			boardController.reset();
		}  else {
			// error unknown parameter
			log.info("Falsche Eingabe!");
		}
	}

	private void quitGame() {
		printWinners();
		log.info("SPIEL BEENDET");
		boardController.quitGame();
	}

	private void printWinners() {
		Queue<Player> finishedPlayers = boardController
				.getFinishedPlayersQueue();
		StringBuilder sb = new StringBuilder();
		sb.append("Platzierung\tSpielername\n");
		for (int i = 0; i < finishedPlayers.size(); i++) {
			sb.append(i + 1).append("\t")
					.append(finishedPlayers.poll().getName()).append("\n");
		}
		log.info(sb.toString());
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
		log.info("");
		log.info(getPlayerSettingString());
		log.info(getBoardString());
		log.info(getStatusAndPlayerString());
		log.info("");
		log.info(getCommandsString());
	}
	
	private String getStatusAndPlayerString() {
		String statusAndPlayer = "STATUS: " + boardController.getStatusString();
		String activePlayer = boardController.getActivePlayerString();
		
		if (activePlayer != null) {
			statusAndPlayer += " Spieler " + activePlayer + " ist am Zug.";
		}	
		return statusAndPlayer;
	}

	private String getPlayerSettingString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Spieler-Liste:\n");
		appendPlayerInfo(sb);
		return sb.toString();
	}
	
	private void appendPlayerInfo(StringBuilder sb) {
		for (Player p : boardController.getPlayers()) {
			sb.append(p.getId()).append(": ").append(p.getName())
					.append(" hat Figuren: ");
			appendFiguresForPlayer(p, sb);
			sb.append("\n");
		}
	}
	
	private void appendFiguresForPlayer(Player player, StringBuilder sb) {
		for (Figure fig : player.getFigures()) {
			sb.append(fig.getLetter()).append(" ");
		}
	}

	private String getCommandsString() {
		return "Befehle: 'q' quit | 's' start game | "
				+ "'add SpielerName' Spieler hinzufuegen\n"
				+ "\t 'm Figurbuchstabe' Figur bewegen | 'w' Wuerfeln | 'r' Reset\n";
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
		appendCorner(sb);
		for (int i = 0; i < boardController.getSettings()
				.getPublicFieldsCount(); i++) {
			sb.append('-');
		}
		appendCorner(sb);
	}

	private void appendHomeFields(StringBuilder sb) {
		appendVerticalBorder(sb);
		for (HomeField hf : boardController.getBoard().getHomeFields()) {
			sb.append(getHomeFieldString(hf));
			appendDistanceBetweenSpecialFields(sb);
		}
		appendVerticalBorder(sb);
		sb.append("\n");
	}

	private void appendDistanceBetweenSpecialFields(StringBuilder sb) {
		int publicFieldsCount = boardController.getSettings()
				.getPublicFieldsCount();
		int numberSpecialFields = boardController.getSettings().getMaxPlayers();
		int numberFigures = boardController.getSettings().getFiguresPerPlayer();

		int distance = publicFieldsCount / numberSpecialFields - numberFigures;

		for (int i = 0; i < distance; i++) {
			sb.append(' ');
		}
	}

	private void appendFinishFields(StringBuilder sb) {
		appendVerticalBorder(sb);
		for (FinishField ff : boardController.getBoard().getFinishFields()) {
			sb.append(getFinishFieldString(ff));
			appendDistanceBetweenSpecialFields(sb);
		}
		appendVerticalBorder(sb);
		sb.append("\n");
	}

	private void appendPublicFields(StringBuilder sb) {
		int publicFieldsCount = boardController.getSettings()
				.getPublicFieldsCount();
		
		appendVerticalBorder(sb);
		for (int i = 0; i < publicFieldsCount; i++) {
			Figure fig = boardController.getBoard().getPublicField()
					.getFigure(i);
			
			appendFigureLetterInPublicField(sb, i, fig);
		}
		appendVerticalBorder(sb);
		sb.append("\n");
	}
	
	private void appendFigureLetterInPublicField(StringBuilder sb, int index, Figure fig) {
		if (fig != null) {
			sb.append(fig.getLetter());
		} else if (isSpecialPublicFieldHomeExit(index)) {
			sb.append("*");
		} else if (isSpecialPublicFieldFinishEntry(index)) {
			sb.append("/");
		} else {
			sb.append("_");
		}
	}
	
	private void appendVerticalBorder(StringBuilder sb) {
		sb.append('|');
	}
	
	private void appendCorner(StringBuilder sb) {
		sb.append('+');
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
		int figuresPerPlayer = boardController.getSettings()
				.getFiguresPerPlayer();

		for (int i = 0; i < figuresPerPlayer; i++) {
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
		int figuresPerPlayer = boardController.getSettings()
				.getFiguresPerPlayer();

		for (int i = 0; i < figuresPerPlayer; i++) {
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
