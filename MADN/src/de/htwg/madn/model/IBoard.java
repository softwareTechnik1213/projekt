package de.htwg.madn.model;

import java.awt.Color;
import java.util.List;

public interface IBoard {

	public abstract void reset();

	public abstract int getExitIndexHome(int playerNumber);

	public abstract int getEntryIndexFinish(int playerNumber);

	public abstract Player addPlayer(Color color, String name, boolean isHuman);

	public abstract List<HomeField> getHomeFields();

	public abstract List<FinishField> getFinishFields();

	public abstract PublicField getPublicField();

	public abstract List<Player> getPlayers();

	public abstract Dice getDice();

	public abstract Figure getFigureForPlayerByLetter(Player player,
			char figureLetter);

}