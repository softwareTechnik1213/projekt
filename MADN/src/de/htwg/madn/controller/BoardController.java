package de.htwg.madn.controller;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Field;
import de.htwg.madn.model.HomeContainer;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable {

    private Board board;
    private String status = "";
    private static final String WELCOME_STRING = "Neue Spiel gestartet.";
    private int lastDiceNumber;
    private Player lastDiceThrowerPlayer;
    private Player activePlayer;
    private List<Player> finishedPlayers;
    private static final int DICE_MIN = 1;
    private static final int DICE_MAX = 6;
    private static final int MIN_PLAYERS = 2;
    private boolean gameIsRunning;

    public BoardController(Board b) {
        board = b;
        reset(); 
    }

    public String getBoardString() {
    	String display;
    	if (activePlayer != null) {
    		display = board.toString() + "\n" 
    				+ "Spieler " + activePlayer.getName() + " ist am Zug.\n" 
    				+ "STATUS: " + status + "\n";
    	} else {
    		display = board.toString() + "\nSTATUS: " + status + "\n";
    	}
        return display;
    }
    
    public void addPlayer(String name, Color col) {
    	int playerId = board.addPlayer(name, col);
    	if (playerId == -1) {
    		status = "Zu viele Spieler. Maximum: " + board.getMaxPlayers();
    	} else {
    		HomeContainer hc = getNextFreeHomeContainer();
    		Player p = board.getPlayerList().get(playerId);
    		hc.setOwner(p);
    		occupyAllFields(hc, p);
    		status = "Spieler " + playerId + ": " + name + " hinzugefuegt.";
    	}
    	
    	notifyObservers();
    }
    
 
	private void occupyAllFields(HomeContainer hc, Player p) {
		char[] figureCodes = p.getFigureCodes();
		int i = 0;
		for (Field f : hc.fieldList()) {
			f.setOccupier(p, figureCodes[i]);
			i++;
		}
	}

	private HomeContainer getNextFreeHomeContainer() {
		for (HomeContainer hc : board.getHomeContainers()) {
			if (hc.getOwner() == null) {
				return hc;
			}
		}
		return null;
	}

	public void reset() {
		board = new Board();
		setInitialState();
		notifyObservers();
	}

	private void setInitialState() {
		finishedPlayers = new LinkedList<Player>();
		lastDiceThrowerPlayer = null;
	    activePlayer = null;
	    status = WELCOME_STRING;
    	gameIsRunning = false;
	}

	public void throwDice() {
		if (isAllowedToThrowDice()) {
			lastDiceNumber = getRandomNumber(DICE_MIN, DICE_MAX);
			lastDiceThrowerPlayer = activePlayer;
			status = "Gewuerfelt: " + lastDiceNumber;
		} else {
			status = "Du hast schon gewuerfelt: " + lastDiceNumber;
		}
		notifyObservers();
	}
	
	private boolean isAllowedToThrowDice() {
		if (activePlayer != null && 
				(lastDiceThrowerPlayer == activePlayer 
				|| playerIsFinished(activePlayer))) {
			return false;
		}
		return true;
	}
	
	/*private boolean hasDiceThrowsLeft(Player player) {
		
	}*/

	public void startGame() {
		if (gameIsRunning) {
			status = "Spiel laeuft schon!";
		} else if (board.getPlayerList().size() < MIN_PLAYERS) {
			status = "Zu wenige Spieler. Mindestens " + MIN_PLAYERS + " benoetigt.";
		} else {
			activePlayer = board.getPlayerList().get(0);
			String name = activePlayer.getName();
			status = "Spiel beginnt. Spieler " + name + " faengt an.";
		}		
		notifyObservers();
	}

	private boolean playerIsFinished(Player player) {
		return finishedPlayers.contains(player);
	}

	private int getRandomNumber(int min, int max) {
		Random rand = new Random();
		return min + Math.abs(rand.nextInt()) % max;
	}
}
