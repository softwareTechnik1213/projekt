package de.htwg.madn.model;

public interface IGameSettings {

	public abstract int getMinPlayers();

	public abstract int getMaxPlayers();

	public abstract int getFiguresPerPlayer();

	public abstract int getPublicFieldsCount();

	public abstract int getDiceMin();

	public abstract int getDiceMax();

	public abstract int getMinNumberToExitHome();

	public abstract int getThrowsAllowedInHome();

	public abstract int getThrowsAllowedInPublic();

}