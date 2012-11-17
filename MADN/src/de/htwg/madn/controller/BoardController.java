package de.htwg.madn.controller;

import java.awt.Color;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.GameRules;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable {

	private Board board;
	private String status = "";
	private Player activePlayer;
	// without finished players
	private Deque<Player> activePlayersQueue;
	private boolean gameIsRunning;
	private final GameRules rules;

	public BoardController(GameRules gameRules) {
		board = new Board(
				gameRules.minPlayers, 
				gameRules.maxPlayers,
				gameRules.figuresPerPlayer,
				gameRules.publicFieldsCount,
				gameRules.diceMin,
				gameRules.diceMax
				);
		activePlayersQueue = new LinkedList<Player>();
		activePlayer = null;
		status = "Neue Spiel gestartet.";
		gameIsRunning = false;
		this.rules = gameRules;
		notifyObservers();
	}

	public Board getBoard() {
		return board;
	}
	
	public GameRules getRules() {
		return rules;
	}

	public void addPlayer(final String name, final Color col) {
		Player newPlayer = board.addPlayer(col, name);

		if (newPlayer == null) {
			status = "Maximale Anzahl Spieler erreicht: "
					+ rules.maxPlayers;
		} else {
			activePlayersQueue.push(newPlayer);
			status = "Spieler " + newPlayer.getId() + " \""
					+ newPlayer.getName() + "\" hinzugefuegt.";
		}

		notifyObservers();
	}

	public void throwDice() {
		if (isAllowedToThrowDice(activePlayer)) {
			status = "Wuerfel: " + board.getDice().throwDice(activePlayer);
		} else if (board.getDice().getThrowsCount() > 0) {
			status = "Du hast schon gewuerfelt: "
					+ board.getDice().getLastNumber();
		} else {
			status = "Du darfst nich wuerfeln";
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

		// no more figures in home field -> max 1 Throw allowed
		if (player.getHomeField().isEmpty()
				&& numberOfThrows < rules.throwsAllowedInPublic) {
			return true;
		}

		// has figures in home field -> max 3 Throws allowed
		if (!player.getHomeField().isEmpty()
				&& numberOfThrows < rules.throwsAllowedInHome) {
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

		Figure figure = getFigureForPlayerByLetter(activePlayer, figureLetter);

		if (figure == null) {
			status = "Diese Figur gehoert dir nicht!";
			notifyObservers();
			return;
		}

		// MOVE FIGURE ACCORDING TO RULES

		// player is finished ..
		if (figure.isFinished()) {
			status = "Du bist schon fertig!";
			setNextActivePlayer();
			notifyObservers();
			return;
		}
		
		// CAN ONLY MOVE OUT HOME? THEN SPECIAL RULE -> NEXT PLAYER IF NOT 6

		// move out of home - error: check if player has a full HOME field
		if (figure.isAtHomeArea()
				&& board.getDice().getLastNumber() >= rules.minNumberToExitHome) {
			figure.getOwner().getHomeField()
					.removeFigure(figure.getCurrentFieldIndex());
			board.getPublicField().setFigure(
					figure.getOwner().getHomeField().getExitIndex(), figure);
			figure.setAtHomeArea(false);
			status = "Figur " + figure.getLetter() + " wurde heraus bewegt.";

		} else if (!figure.isAtHomeArea()) {
			// move on public fields
			
			int currentIndex = figure.getCurrentFieldIndex();
			int newIndex = (currentIndex + board.getDice().getLastNumber()) % rules.publicFieldsCount;
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

	private Figure getFigureForPlayerByLetter(Player player, char figureLetter) {
		for (Figure figure : player.getFigures()) {
			if (figure.getLetter() == figureLetter) {
				return figure;
			}
		}
		return null;
	}

	public void startGame() {
		if (gameIsRunning) {
			status = "Spiel laeuft schon!";
		} else if (activePlayersQueue.size() < rules.minPlayers) {
			status = "Zu wenige Spieler. Mindestens " + rules.maxPlayers
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
		// get from tail and remove
		activePlayer = activePlayersQueue.pollLast();
		// and then push to head of queue
		activePlayersQueue.push(activePlayer);
	}

	public List<Player> getPlayerList() {
		return board.getPlayers();
	}

	public String getStatusString() {
		return status;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public String getActivePlayerString() {
		String ret = "";
		if (activePlayer != null) {
			ret = "Spieler " + activePlayer.getName() + " ist am Zug.";
		}
		return ret;
	}
}
