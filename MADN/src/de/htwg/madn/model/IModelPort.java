package de.htwg.madn.model;

import java.awt.Color;
import java.util.List;

/**
 * Port to the model layer.
 */
public interface IModelPort {
		
	/**
	 * Resets the model.
	 */
	void reset();

	/**
	 * Returns the game settings currently set
	 * @return Game settings
	 */
	IGameSettings getSettings();

	/**
	 * Returns the dice.
	 * @return The dice
	 */
	Dice getDice();

	/**
	 * Adds a player.
	 * @param color Color
	 * @param name Name
	 * @param isHuman is it a human player?
	 * @return The new player or null if player couldn't be added.
	 */
	Player addPlayer(Color col, String name, boolean isHuman);

	/**
	 * Returns all players.
	 * @return the players
	 */
	List<Player> getPlayers();

	/**
	 * Returns the public field.
	 * @return public field
	 */
	PublicField getPublicField();

	/**
	 * Returns a figure identified by their letter.
	 * @param player The owner of the figure
	 * @param figureLetter the letter of the figure.
	 * @return the figure or null if no figure was found for this player
	 */
	Figure getFigureForPlayerByLetter(Player player, char figureLetter);

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
	
}
