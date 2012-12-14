package de.htwg.madn.model;

import java.awt.Color;
import java.util.List;

/**
 * The board which holds a game data.
 */
public interface IBoard {

	/**
	 * Resets the data.
	 */
	void reset();

	/**
	 * Returns the index in the public field to which a figure is set if it leaves home.
	 * @param playerNumber the id of the player
	 * @return exit index
	 */
	int getExitIndexHome(int playerNumber);

	/**
	 * Returns the index in the public field from which the figure can enter its finish area.
	 * @param playerNumber the id of the player
	 * @return finish index
	 */
	int getEntryIndexFinish(int playerNumber);

	/**
	 * Adds a player.
	 * @param color Color
	 * @param name Name
	 * @param isHuman is it a human player?
	 * @return The new player or null if player couldn't be added.
	 */
	Player addPlayer(Color color, String name, boolean isHuman);

	/**
	 * List of all home fields.
	 * @return List of home fields.
	 */
	List<HomeField> getHomeFields();

	/**
	 * List of all finish fields.
	 * @return List of finish fields.
	 */
	List<FinishField> getFinishFields();

	/**
	 * Returns the public field.
	 * @return public field
	 */
	PublicField getPublicField();

	/**
	 * Returns all players.
	 * @return the players
	 */
	List<Player> getPlayers();

	/**
	 * Returns the dice.
	 * @return The dice
	 */
	Dice getDice();

	/**
	 * Returns a figure identified by their letter.
	 * @param player The owner of the figure
	 * @param figureLetter the letter of the figure.
	 * @return the figure or null if no figure was found for this player
	 */
	Figure getFigureForPlayerByLetter(Player player, char figureLetter);

}