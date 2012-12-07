package de.htwg.madn.controller;

import java.awt.Color;
import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObservable;
import de.htwg.madn.util.observer.IObserver;

public interface IBoardControllerPort extends IObserver, IObservable {

	IGameSettings getSettings();

	void addPlayer(String name, Color col, boolean isHuman);

	void throwDice();

	void reset();

	void moveFigure(char figureLetter);

	void startGame();

	void quitGame();

	Queue<Player> getFinishedPlayersQueue();

	List<Player> getPlayers();

	String getStatusString();

	Player getActivePlayer();

	String getActivePlayerString();

	IModelPort getModelPort();

}