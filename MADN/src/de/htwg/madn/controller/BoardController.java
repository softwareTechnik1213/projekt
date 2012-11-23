package de.htwg.madn.controller;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable {

	private Board board;
	private String status = "";
	private Player activePlayer;
	// without finished players
	private Queue<Player> activePlayersQueue;
	// finished players
	private Queue<Player> finishedPlayersQueue;
	private boolean gameIsRunning;
	private final GameSettings settings;

	public BoardController(GameSettings gameSettings, Board board) {
		this.board = board;
		this.settings = gameSettings;
		this.activePlayersQueue = new LinkedList<Player>();
		this.finishedPlayersQueue = new LinkedList<Player>();
		this.activePlayer = null;
		this.status = "Neue Spiel gestartet.";
		this.gameIsRunning = false;
		notifyObservers();
	}

	public Board getBoard() {
		return board;
	}
	
	public GameSettings getSettings() {
		return settings;
	}

	public void addPlayer(final String name, final Color col) {
		Player newPlayer = board.addPlayer(col, name);

		if (newPlayer == null) {
			status = "Maximale Anzahl Spieler erreicht: "
					+ settings.getMaxPlayers();
		} else {
			activePlayersQueue.add(newPlayer);
			status = "Spieler " + newPlayer.getId() + " \""
					+ newPlayer.getName() + "\" hinzugefuegt.";
		}

		notifyObservers();
	}

	public void throwDice() {
		if (isAllowedToThrowDice(activePlayer)) {
			status = "Wuerfel: " + board.getDice().throwDice(activePlayer);
		} else if (board.getDice().getThrowsCount() > 0) {
			// has thrown and exceeded his maximum throws
			status = "Du hast schon gewuerfelt: "
					+ board.getDice().getLastNumber();
		} else {
			status = "Du darfst nicht wuerfeln";
		}
		notifyObservers();
	}

	private boolean isAllowedToThrowDice(Player player) {
		Player lastThrower = board.getDice().getLastThrower();
		int numberOfThrows = board.getDice().getThrowsCount();
		// no player or now previous thrower? then throw the dice!
		if (player == null || lastThrower == null) {
			return true;
		}

		// no more figures in home field -> max X Throw allowed
		if (player.getHomeField().isEmpty()
				&& numberOfThrows < settings.getThrowsAllowedInPublic()) {
			return true;
		}

		// has figures in home field -> max Y Throws allowed
		if (!player.getHomeField().isEmpty()
				&& numberOfThrows < settings.getThrowsAllowedInHome()) {
			return true;
		}

		return false;
	}

	public void moveFigure(char figureLetter) {
		if (board.getDice().getLastThrower() != activePlayer) {
			status = "Du solltest zuerst wuerfeln!";
			notifyObservers();
			return;
		}

		Figure figure = board.getFigureForPlayerByLetter(activePlayer, figureLetter);

		if (figure == null) {
			status = "Diese Figur gehoert dir nicht!";
			notifyObservers();
			return;
		}

		// MOVE FIGURE ACCORDING TO RULES

		// player is finished .. has more figures??
		if (figure.isFinished()) {
			status = "Du bist schon fertig!";
			setNextActivePlayer();
			notifyObservers();
			return;
		}
		
		// CAN ONLY MOVE OUT HOME? THEN SPECIAL RULE -> NEXT PLAYER IF NOT 6
		// save get..get..get Vars local for better readability

		// move out of home - error: check if player has a full HOME field
		if (figure.isAtHomeArea()
				&& board.getDice().getLastNumber() >= settings.getMinNumberToExitHome()) {
			figure.getOwner().getHomeField()
					.removeFigure(figure.getCurrentFieldIndex());
			board.getPublicField().setFigure(
					figure.getOwner().getHomeField().getExitIndex(), figure);
			figure.setAtHomeArea(false);
			status = "Figur " + figure.getLetter() + " wurde heraus bewegt.";

		} else if (!figure.isAtHomeArea()) {
			// move on public fields
			
			int currentIndex = figure.getCurrentFieldIndex();
			int newIndex = (currentIndex + board.getDice().getLastNumber()) % settings.getPublicFieldsCount();
			// CHECK IF MIGHT GO TO FINISH FIELD...
			board.getPublicField().removeFigure(currentIndex);
			board.getPublicField().setFigure(newIndex, figure);
			status = "Figur " + figure.getLetter() + " wurde bewegt.";
			
		} else {
			status = "Bewegung nicht moeglich";
		}

		setNextActivePlayer();
		notifyObservers();
	}

	public void startGame() {
		if (gameIsRunning) {
			status = "Spiel laeuft schon!";
		} else if (activePlayersQueue.size() < settings.getMinPlayers()) {
			status = "Zu wenige Spieler. Mindestens " + settings.getMaxPlayers()
					+ " benoetigt.";
		} else {
			setNextActivePlayer();
			status = "Spiel beginnt.";
			gameIsRunning = true;
		}
		notifyObservers();
	}

	private void setNextActivePlayer() {
		// reset dice
		board.getDice().resetThrowsCount();
		// get from head and remove
		activePlayer = activePlayersQueue.poll();
		// no more Players!
		if (activePlayer == null) {
			quitGame();
		} else {
			// push to tail of queue
			activePlayersQueue.add(activePlayer);
		}
	}
	
	public void quitGame() {
		System.exit(0);
	}
	
	public Queue<Player> getFinishedPlayersQueue() {
		return finishedPlayersQueue;
	}

	public List<Player> getPlayers() {
		return board.getPlayers();
	}

	public String getStatusString() {
		return status;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public String getActivePlayerString() {
		if (activePlayer != null) {
			return activePlayer.getName();
		}
		return null;
	}
}
