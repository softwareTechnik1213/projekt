package de.htwg.madn.controller;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable implements IBoardControllerPort {

	private String status = "";
	private Player activePlayer;
	// without finished players
	private Queue<Player> activePlayersQueue;
	// finished players
	private Queue<Player> finishedPlayersQueue;
	private boolean gameIsRunning;
	private final IGameSettings settings;
	private MovementController movementController;
	private IModelPort model;

	public BoardController(IModelPort model) {
		this.model = model;
		this.settings = model.getSettings();
		init();
		notifyObservers();
	}

	private void init() {
		this.activePlayersQueue = new LinkedList<Player>();
		this.finishedPlayersQueue = new LinkedList<Player>();
		this.activePlayer = null;
		this.status = "Neue Spiel erstellt.";
		this.gameIsRunning = false;
		this.movementController = new MovementController(model);
		this.movementController.addObserver(this);
	}



	@Override
	public void update() {
		status = movementController.getStatusString();
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getBoard()
	 */
	@Override
	public IModelPort getModelPort() {
		return model;
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getSettings()
	 */
	@Override
	public IGameSettings getSettings() {
		return settings;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#addPlayer(java.lang.String, java.awt.Color, boolean)
	 */
	@Override
	public void addPlayer(final String name, final Color col, boolean isHuman) {
		Player newPlayer = model.addPlayer(col, name, isHuman);

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


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#throwDice()
	 */
	@Override
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


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#reset()
	 */
	@Override
	public void reset() {
		model.reset();
		init();
		status = "Reset: Neues Spiel erstellt.";
		notifyObservers();
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#moveFigure(char)
	 */
	@Override
	public void moveFigure(char figureLetter) {
		boolean setNext = movementController.moveFigure(activePlayer,
				figureLetter);
		
		if (setNext) {
			setNextActivePlayer();
		}
		notifyObservers();
	}
	
	private void handleFinishedPlayer(Player player) {
		if (player == null) {
			return;
		}
		if (!movementController.hasActiveFigures(player)) {
			finishedPlayersQueue.add(player);
			activePlayersQueue.remove(player);
		}
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#startGame()
	 */
	@Override
	public void startGame() {
		if (gameIsRunning) {
			status = "Spiel laeuft schon!";
		} else if (activePlayersQueue.size() < settings.getMinPlayers()) {
			status = "Zu wenige Spieler. Mindestens "
					+ settings.getMinPlayers() + " benoetigt.";
		} else {
			setNextActivePlayer();
			status = "Spiel beginnt.";
			gameIsRunning = true;
		}
		notifyObservers();
	}

	private void setNextActivePlayer() {
		// reset dice
		model.getDice().resetThrowsCount();
		// check and maybe remove a finished player
		handleFinishedPlayer(activePlayer);
		// get from head and remove
		activePlayer = activePlayersQueue.poll();
		// no more Players!
		if (activePlayer == null) {
			
			return;
		}
		// push to tail of queue
		activePlayersQueue.add(activePlayer);
		// current player is a bot
		/*if (!activePlayer.isHuman()) {
			// publish current state, as we will now do all with the cpu
			notifyObservers();
			// autonomeRun guarantees that the player made a move if possible
			movementController.startAutonomeRun(activePlayer);
			
			setNextActivePlayer();
		}*/
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#quitGame()
	 */
	@Override
	public void quitGame() {
		System.exit(0);
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getFinishedPlayersQueue()
	 */
	@Override
	public Queue<Player> getFinishedPlayersQueue() {
		return finishedPlayersQueue;
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getPlayers()
	 */
	@Override
	public List<Player> getPlayers() {
		return model.getPlayers();
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getStatusString()
	 */
	@Override
	public String getStatusString() {
		return status;
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getActivePlayer()
	 */
	@Override
	public Player getActivePlayer() {
		return activePlayer;
	}


	/* (non-Javadoc)
	 * @see de.htwg.madn.controller.IBoardController#getActivePlayerString()
	 */
	@Override
	public String getActivePlayerString() {
		if (activePlayer != null) {
			return activePlayer.getName();
		}
		return null;
	}

	@Override
	public boolean gameIsFinished() {
		return activePlayersQueue.isEmpty() && gameIsRunning;
	}
}
