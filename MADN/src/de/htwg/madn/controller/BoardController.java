package de.htwg.madn.controller;

import java.awt.Color;

import de.htwg.madn.model.Board;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable {

    private Board board;
    private String status;

    public BoardController(Board b) {
        board = b;
        status = "";
    }

    public String getBoardString() {
        return board.toString() + "\n" + status + "\n";
    }
    
    public void addPlayer(String name, Color col) {
    	int playerId = board.addPlayer(name, col);
    	if (playerId == -1) {
    		status = "Zu viele Spieler. Maximum: " + board.getMaxPlayers();
    	} else {
    		status = "Spieler " + playerId + ": " + name + " hinzugefuegt.";
    	}
    	
    	notify();
    }
}
