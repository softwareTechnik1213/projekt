package de.htwg.madn.model;

import java.awt.Color;
import java.util.List;

public interface IModelPort {
		
	void reset();

	IGameSettings getSettings();

	Dice getDice();

	Player addPlayer(Color col, String name, boolean isHuman);

	List<Player> getPlayers();

	PublicField getPublicField();

	Figure getFigureForPlayerByLetter(Player player, char figureLetter);

	List<HomeField> getHomeFields();

	List<FinishField> getFinishFields();
	
}
