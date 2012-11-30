package de.htwg.madn.controller;

import java.awt.Color;
import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObservable;
import de.htwg.madn.util.observer.IObserver;

public interface IBoardController extends IObserver, IObservable {

	public abstract IGameSettings getSettings();

	public abstract void addPlayer(String name, Color col, boolean isHuman);

	public abstract void throwDice();

	public abstract void reset();

	public abstract void moveFigure(char figureLetter);

	public abstract void startGame();

	public abstract void quitGame();

	public abstract Queue<Player> getFinishedPlayersQueue();

	public abstract List<Player> getPlayers();

	public abstract String getStatusString();

	public abstract Player getActivePlayer();

	public abstract String getActivePlayerString();

	IModelPort getModelPort();

}