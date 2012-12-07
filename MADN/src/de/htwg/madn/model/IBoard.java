package de.htwg.madn.model;

import java.awt.Color;
import java.util.List;

public interface IBoard {

	void reset();

	int getExitIndexHome(int playerNumber);

	int getEntryIndexFinish(int playerNumber);

	Player addPlayer(Color color, String name, boolean isHuman);

	List<HomeField> getHomeFields();

	List<FinishField> getFinishFields();

	PublicField getPublicField();

	List<Player> getPlayers();

	Dice getDice();

	Figure getFigureForPlayerByLetter(Player player, char figureLetter);

}