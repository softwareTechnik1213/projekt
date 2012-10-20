package de.htwg.madn.view;

import de.htwg.madn.controller.BoardController;

public class TUIView implements IUserInterface {

    private BoardController boardController;

    public TUIView(BoardController bc) {
        this.boardController = bc;
        // watch the controller with this class
        this.boardController.addObserver(this);
        // initial draw
        draw();
    }

    @Override
    public boolean iterate() {
        // return true when UI quits, else false
        return false;
    }

    @Override
    public void update() {
        draw();
    }

    private void draw() {
        System.out.println(boardController.getBoardString());
    }

}
