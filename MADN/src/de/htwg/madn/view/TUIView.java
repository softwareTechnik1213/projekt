package de.htwg.madn.view;

import de.htwg.madn.controller.BoardController;

public class TUIView implements IUserInterface {

    private BoardController boardController;

    public TUIView(BoardController bc) {
        this.boardController = bc;
        // watcht the controller with this class
        this.boardController.addObserver(this);
    }

    private void draw() {
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
}
