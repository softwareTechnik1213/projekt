package de.htwg.madn.model;

public interface IGameSettings {

	int getMinPlayers();

	int getMaxPlayers();

	int getFiguresPerPlayer();

	int getPublicFieldsCount();

	int getDiceMin();

	int getDiceMax();

	int getMinNumberToExitHome();

	int getThrowsAllowedInHome();

	int getThrowsAllowedInPublic();

}