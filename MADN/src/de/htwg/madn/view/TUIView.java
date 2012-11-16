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
		draw();
	}

	public boolean iterate() {
		// return true when UI quits, else false
		return handleInput(SCANNER.next());
	}

	private boolean handleInput(String line) {
		// return true when UI should quit, else false
		boolean quit = false;
		
		if (line.equalsIgnoreCase("q")) {
			// quit
			quit = true;
		} else if (line.equalsIgnoreCase("n")) {
			// new game
		} else if (line.equalsIgnoreCase("add")) {
			// add player
		} else if (line.equalsIgnoreCase("m")) {
			// move figure
		} else if (line.equalsIgnoreCase("w")) {
			// throw dice
		}

		// if the command line has the form 123, set the cell (1,2) to value 3
		if (line.matches("[0-9][0-9][0-9]")) {
			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(line);
			int[] arg = new int[3];
			for (int i = 0; i < arg.length; i++) {
				m.find();
				arg[i] = Integer.parseInt(m.group());
			}
			controller.setValue(arg[0], arg[1], arg[2]);
		}

		return quit;

	}

	public void askPlayerCount() {

		while (true) {
			System.out.print("Bitte Spielerzahl angeben (2 bis 4): ");
			if (SCANNER.hasNextInt()) {
				if (!boardController.setPlayers(SCANNER.nextInt())) {
					System.out
							.println("Fehler: Zahl zwischen 2 und 4 eingeben.");
				} else {
					break;
				}

			}
		}

	}

	/* public void showPla */

	@Override
	public void update() {
		draw();
	}

	private void draw() {
		System.out.println(boardController.getBoardString());
		System.out.println();
		System.out.println();
	}

}
