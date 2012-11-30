package de.htwg.madn.view;

import java.awt.Color;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;

public class TUIView implements IObserver {

	private final IBoardControllerPort boardController;
	private static Logger log;
	private static final Scanner SCANNER = new Scanner(System.in);
	private final DataToStringConverter stringifyer;

	public TUIView(IBoardControllerPort boardController) {
		this.boardController = boardController;
		// watch the controller with this class
		this.boardController.addObserver(this);
		this.stringifyer = new DataToStringConverter(boardController.getSettings());
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
			boardController.addPlayer(parm, Color.BLACK, true);
		} else if (cmd.equals("addbot") && parm != null && !parm.isEmpty()) {
			boardController.addPlayer(parm, Color.BLACK, false);
		} else if (cmd.equals("r")) {
			boardController.reset();
		} else {
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
		String win = stringifyer.getWinnersString(boardController
				.getFinishedPlayersQueue());

		log.info(win);
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
		log.info("STATUS: " + boardController.getStatusString());
		log.info("");
		log.info(getCommandsString());
	}

	private String getPlayerSettingString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Spieler-Liste:\n");
		sb.append(stringifyer.getPlayerSettingString(boardController.getPlayers(), boardController.getActivePlayer()));
		return sb.toString();
	}

	private String getCommandsString() {
		return "Befehle: 'q' quit | 's' start game | "
				+ "'add SpielerName' Spieler hinzufuegen\n"
				+ "\t 'm Figurbuchstabe' Figur bewegen | 'w' Wuerfeln | 'r' Reset\n"
				+ "\t 'addbot SpielerName' Computer Spieler hinzufuegen\n";
	}

	private String getBoardString() {
		StringBuilder sb = new StringBuilder();

		sb.append(stringifyer.getBorderString());
		sb.append("\n");

		sb.append(stringifyer.getHomeFieldsString(boardController.getModelPort()
				.getHomeFields()));
		sb.append(stringifyer.getPublicFieldsString(boardController.getModelPort()
				.getPublicField(), boardController.getModelPort()));
		sb.append(stringifyer.getFinishFieldsString(boardController.getModelPort().getFinishFields()));

		sb.append(stringifyer.getBorderString());

		return sb.toString();
	}
}
