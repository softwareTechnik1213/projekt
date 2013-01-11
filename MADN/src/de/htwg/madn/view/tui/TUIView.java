package de.htwg.madn.view.tui;

import java.awt.Color;
import java.util.logging.Logger;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;

public final class TUIView implements IObserver {

	private final IBoardControllerPort boardController;
	private static final Logger log = Logger.getLogger(TUIView.class.getName());
	private final DataToStringConverter stringifyer;

	public TUIView(IBoardControllerPort boardController) {
		this.boardController = boardController;
		// watch the controller with this class
		this.boardController.addObserver(this);
		this.stringifyer = new DataToStringConverter(
				boardController.getSettings());
		draw();
	}

	public boolean handleInput(String line) {
		String[] args;
		String cmd, parm;
		boolean quit = false;

		// split input into a command and an argument part
		args = getCommandAndArgument(line);

		if (args != null) {
			cmd = args[0];
			parm = args[1];
			quit = interpretInput(cmd, parm);
		} else {
			// error empty command!
			log.info("Leere Eingabe!");
			quit = false;
		}
		return quit;
	}

	private boolean interpretInput(String cmd, String parm) {
		if (cmd.equals("q")) {
			quitGame();
			return true;
		} else if (cmd.equals("s")) {
			boardController.startGame();
		} else if (cmd.equals("m") && parm != null) {
			boardController.moveFigure(parm.charAt(0));
			// maybe finished
			if (boardController.gameIsFinished()) {
				return true;
			}
		} else if (cmd.equals("w")) {
			boardController.throwDice();
		} else if (cmd.equals("add") && parm != null) {
			boardController.addPlayer(parm, Color.BLACK, true);
		} else if (cmd.equals("r")) {
			boardController.reset();
		} else {
			// error unknown parameter
			log.info("Falsche Eingabe!");
		}

		return false;
	}

	private void quitGame() {
		printWinners();
		log.info("SPIEL BEENDET");
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
		if (boardController.gameIsFinished()) {
			quitGame();
		}
	}

	private void draw() {
		log.info("\n" + getPlayerSettingString() + "\n" + getBoardString()
				+ "\n" + "STATUS: " + boardController.getStatusString() + "\n"
				+ getCommandsString());
	}

	private String getPlayerSettingString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Spieler-Liste:\n");
		sb.append(stringifyer.getPlayerSettingString(
				boardController.getPlayers(), boardController.getActivePlayer()));
		return sb.toString();
	}

	private String getCommandsString() {
		return "Befehle: 'q' quit | 's' start game | "
				+ "'add SpielerName' Spieler hinzufuegen\n"
				+ "\t 'm Figurbuchstabe' Figur bewegen | 'w' Wuerfeln | 'r' Reset\n";
	}

	private String getBoardString() {
		StringBuilder sb = new StringBuilder();

		sb.append(stringifyer.getBorderString());
		sb.append("\n");

		sb.append(stringifyer.getHomeFieldsString(boardController
				.getModelPort().getHomeFields()));
		sb.append(stringifyer.getPublicFieldsString(boardController
				.getModelPort().getPublicField(), boardController
				.getModelPort()));
		sb.append(stringifyer.getFinishFieldsString(boardController
				.getModelPort().getFinishFields()));

		sb.append(stringifyer.getBorderString());

		return sb.toString();
	}
}
