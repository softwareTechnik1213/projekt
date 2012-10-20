package de.htwg.madn;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.model.Board;
import de.htwg.madn.view.IUserInterface;
import de.htwg.madn.view.TUIView;

public final class Application {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // board with 4 players, 3 figures per player and 28 public fields
        BoardController boardController = new BoardController(new Board(4, 3, 28));
        IUserInterface ui = new TUIView(boardController);
        // IUserInterface ui = new GUIView(boardController) -> easy switch of UI

        // continue until the user decides to quit
        boolean quit = false;
        while (!quit) {
            quit = ui.iterate();
        }
    }
}
