package de.htwg.madn.controller;

import org.junit.Test;
import static org.junit.Assert.*;
import de.htwg.madn.model.Board;
import de.htwg.madn.util.observer.Observable;

public class BoardControllerTest extends Observable {

    @Test
    public void testBoardController() {
        BoardController bc = new BoardController(new Board());
        assertNotNull(bc);
    }

    @Test
    public void getBoardStringTest() {
        BoardController bc = new BoardController(new Board());
        assertNotNull(bc.getBoardString());
    }
}
