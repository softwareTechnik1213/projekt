package de.htwg.madn.controller;

import de.htwg.madn.model.Board;
import de.htwg.madn.util.observer.Observable;

public final class BoardController extends Observable {

    private Board board;

    public BoardController(Board b) {
        board = b;
    }
}
