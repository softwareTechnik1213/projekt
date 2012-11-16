package de.htwg.madn.view;

import java.awt.Color;
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
		draw();
	}

	public boolean iterate() {
		// return true when UI quits, else false
		return handleInput(SCANNER.nextLine());
	}

	private boolean handleInput(String line) {

		// return true when UI should quit, else false
		boolean quit = false;
		String[] words = null;
		String cmd = null;
		String parm = null;

		if (line == null || line.isEmpty()) {
			return quit;
		}

		words = line.split(" ");
		if (words.length == 0) {
			return quit;
		}
		cmd = words[0].toLowerCase();
		// parameter like: add NAME
		if (words.length > 1) {
			parm = words[1];
		}

		if (cmd.equals("q")) {
			// quit
			System.out.println("SPIEL BEENDET.");
			quit = true;
		} else if (cmd.equals("n")) {
			// new game
			System.out.println("Neues Spiel wird gestartet:\n");
			boardController.reset();
		} else if (cmd.equals("m")) {
			// move figure
		} else if (cmd.equals("w")) {
			// throw dice
		} else if (cmd.equals("add") && parm != null && !parm.isEmpty()) {
			// add player
			boardController.addPlayer(parm, Color.BLACK);
		} else {
			// error unknown parameter
			System.out.println("Falsche Eingabe!");
		}
		
		return quit;
	}

	@Override
	public void update() {
		draw();
	}

	private void draw() {
		System.out.println(boardController.getBoardString());
		printCommands();
		System.out.print(">>> ");
	}

	private void printCommands() {
		System.out.println(getCommands());
	}

	private String getCommands() {
		return "Befehle: 'q' quit | 'n' new | 'add SpielerName' Spieler hinzufuegen"
				+ " | 'm Figurbuchstabe' Figur bewegen | 'w' Wuerfeln\n";
	}

}
