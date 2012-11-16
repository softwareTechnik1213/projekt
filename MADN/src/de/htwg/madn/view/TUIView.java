package de.htwg.madn.view;

import java.util.Scanner;

import de.htwg.madn.controller.BoardController;
import de.htwg.madn.util.observer.IObserver;

public class TUIView implements IObserver {

    private BoardController boardController;
    private static Scanner SCANNER;

    public TUIView(BoardController bc) {
        this.boardController = bc;
        SCANNER = new Scanner(System.in);
        // watch the controller with this class
        this.boardController.addObserver(this);
        initGame();
    }

	private void initGame() {
		askPlayerCount();
	}

	public boolean iterate() {
        // return true when UI quits, else false
        return false;
    }
    
    public void askPlayerCount() {
		
    	while(true) {
    		 System.out.print("Bitte Spielerzahl angeben (2 bis 4): ");
    		 if(SCANNER.hasNextInt()) {
    			 if (!boardController.setPlayers(SCANNER.nextInt())) {
        			 System.out.println("Fehler: Zahl zwischen 2 und 4 eingeben.");
        		 } else {
        			 break;
        		 }
    			 
    		 }
    	 }

		 
    }
    
    public void showPla

    @Override
    public void update() {
        draw();
    }
    		
    private void draw() {
        System.out.println(boardController.getBoardString());
    }

}
