package de.htwg.madn.controller;

import java.awt.Color;
import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObservable;
import de.htwg.madn.util.observer.IObserver;

/**
 * The port to the controller layer.
 */
public interface IBoardControllerPort extends IObserver, IObservable {

	/**
	 * Returns the game settings
	 * @return game settings
	 */
	IGameSettings getSettings();

	/**
	 * Adds a player to the board.
	 * @param name Name of the player
	 * @param col Color of the player
	 * @param isHuman is it a human player?
	 */
	void addPlayer(String name, Color col, boolean isHuman);

	/**
	 * Throws the dice.
	 */
	void throwDice();

	/**
	 * Resets the whole game.
	 */
	void reset();

	/**
	 * Move a figure.
	 * @param figureLetter The letter by which the figure is identified
	 * @return 
	 */
	void moveFigure(char figureLetter);

	/**
	 * Starts the game.
	 */
	void startGame();

	/**
	 * Returns the queue of finished players.
	 * @return The queue of finished players.
	 */
	Queue<Player> getFinishedPlayersQueue();

	/**
	 * Returns the list of active players in the game.
	 * @return list of active players in the game.
	 */
	List<Player> getPlayers();

	/**
	 * Returns the status string.
	 * @return Status string
	 */
	String getStatusString();

	/**
	 * Returns the active player who's turn it is.
	 * @return active player
	 */
	Player getActivePlayer();

	/**
	 * String representation of the active player.
	 * @return String of the active player
	 */
	String getActivePlayerString();

	/**
	 * Returns the port to the model layer
	 * @return Port to the model layer
	 */
	IModelPort getModelPort();

	/**
	 * Checks if the game is finished.
	 * @return true if game is finished, otherwise false
	 */
	boolean gameIsFinished();

}