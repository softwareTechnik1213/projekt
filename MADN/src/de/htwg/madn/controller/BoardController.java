package de.htwg.madn.controller;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObserver;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable implements IObserver {

	private Board board;
	private String status = "";
	private Player activePlayer;
	// without finished players
	private Queue<Player> activePlayersQueue;
	// finished players
	private Queue<Player> finishedPlayersQueue;
	private boolean gameIsRunning;
	private final GameSettings settings;
	private MovementController movementController;

	public BoardController(GameSettings gameSettings, Board board) {
		this.board = board;
		this.settings = gameSettings;
		init();
		notifyObservers();
	}

	private void init() {
		this.activePlayersQueue = new LinkedList<Player>();
		this.finishedPlayersQueue = new LinkedList<Player>();
		this.activePlayer = null;
		this.status = "Neue Spiel erstellt.";
		this.gameIsRunning = false;
		this.movementController = new MovementController(this.board,
				this.settings);
		this.movementController.addObserver(this);
	}

	@Override
	public void update() {
		status = movementController.getStatusString();
	}

	public Board getBoard() {
		return board;
	}

	public GameSettings getSettings() {
		return settings;
	}

	public void addPlayer(final String name, final Color col, boolean isHuman) {
		Player newPlayer = board.addPlayer(col, name, isHuman);

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
		boolean setNextPlayer = false;
		
		if (!gameIsRunning) {
			status = "Starte zuerst das Spiel";
		} else {
			setNextPlayer = movementController.throwDice(activePlayer);
		}
		
		if (setNextPlayer) {
			setNextActivePlayer();
		}
		notifyObservers();
	}

	public void reset() {
		board.reset();
		init();
		status = "Reset: Neues Spiel erstellt.";
		notifyObservers();
	}

	public void moveFigure(char figureLetter) {
		boolean setNext = movementController.moveFigure(activePlayer,
				figureLetter);
		
		if (setNext) {
			setNextActivePlayer();
		}
		notifyObservers();
	}

	public void startGame() {
		if (gameIsRunning) {
			status = "Spiel laeuft schon!";
		} else if (activePlayersQueue.size() < settings.getMinPlayers()) {
			status = "Zu wenige Spieler. Mindestens "
					+ settings.getMaxPlayers() + " benoetigt.";
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
			return;
		}
		// push to tail of queue
		activePlayersQueue.add(activePlayer);
		// current player is a bot
		if (!activePlayer.isHuman()) {
			// publish current state, as we will now do all with the cpu
			notifyObservers();
			// autonomeRun guarantees that the player made a move if possible
			movementController.startAutonomeRun(activePlayer);
			setNextActivePlayer();
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
