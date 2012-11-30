package de.htwg.madn.controller;

import de.htwg.madn.model.Dice;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.HomeField;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.Observable;

// package private
final class MovementController extends Observable {

	private final Dice dice;
	private final IGameSettings settings;
	private String status;
	private final IModelPort model;
	private final AutonomeMovementController autonomeComtroller;

	public MovementController(IModelPort model) {
		this.model = model;
		this.dice = model.getDice();
		this.settings = model.getSettings();
		this.status = "";
		this.autonomeComtroller = new AutonomeMovementController();
	}

	// true= next player, false=player stays
	public boolean throwDice(Player player) {

		boolean setNext = false;
		boolean canMove = false;

		if (isAllowedToThrowDice(player)) {
			status = "Wuerfel: " + dice.throwDice(player) + ".";
			canMove = playerCanMove(player, dice.getLastNumber());
		} else {
			status = "Du darfst nicht wuerfeln.";
		}
		
		if (canMove || hasThrowsLeft(player)) {
			setNext = false;
		} else {
			setNext = true;
			dice.resetThrowsCount();
		}
		
		notifyObservers();
		return setNext;
	}
	
	private boolean hasThrowsLeft(Player player) {
		int diceThrows = dice.getThrowsCount();
		
		if (hasOnlyFiguresInHome(player) && diceThrows >= settings.getThrowsAllowedInHome()) {
			return false;
		}
		if (!hasOnlyFiguresInHome(player) && diceThrows >= settings.getThrowsAllowedInPublic()) {
			return false;
		}
		
		return true;
	}

	// player has the possibility to move at least one figure
	private boolean playerCanMove(Player player, int diceNum) {
		
		for (Figure fig : player.getFigures()) {
			if (figureCanMove(fig, diceNum)) {
				return true;
			}
		}		
		
		return false;		
	}
	
	// figure has the possibility to move at least anywhere
	private boolean figureCanMove(Figure figure, int diceNum) {
		return figureCanLeaveHome(figure, diceNum)
				|| figureCanMoveInPublic(figure, diceNum);
	}
	
	private boolean figureCanMoveInPublic(Figure fig, int fieldsToMove) {
		int nextIndex = (fig.getCurrentFieldIndex() + fieldsToMove) % settings.getPublicFieldsCount();
		int finishEntryIndex = fig.getOwner().getFinishField().getEntryIndex();
		
		if (fig.isAtHomeArea() || fig.isAtFinishArea()) {
			return false;
		}
		
		// check if figure might entry finish field
		if (nextIndex > finishEntryIndex) {
			int finishIndex = nextIndex - finishEntryIndex;
			if (figureCanMoveInFinishArea(fig, finishIndex)) {
				return true;
			}
		}
		
		if (!hasOwnFigureOnField(nextIndex, fig.getOwner())) {
			return true;
		}		
		
		return false;
	}
	
	private boolean hasOwnFigureOnField(int index, Player player) {
		Figure fig = model.getPublicField().getFigure(index);
		return fig != null && fig.getOwner() == player;
	}

	private boolean figureCanMoveInFinishArea(Figure fig, int fieldsToMove) {
		int newIndex = fig.getCurrentFieldIndex() + fieldsToMove;
		return fig.getOwner().getFinishField().getFigure(newIndex) != null;
	}

	private boolean figureCanLeaveHome(Figure fig, int fieldsToMove) {
		return fig.isAtHomeArea() && fieldsToMove >= settings.getMinNumberToExitHome();
	}
	
	
	private boolean isActiveFigure(Figure fig) {
		return !fig.isFinished();
	}
	
	public boolean hasActiveFigures(Player player) {
		for (Figure fig : player.getFigures()) {
			if (isActiveFigure(fig)) {
				return true;
			}
		}
		return false;
	}
		
	private boolean hasOnlyFiguresInHome(Player player) {
		for (Figure fig : player.getFigures()) {
			// has figure in public
			if (!fig.isAtHomeArea() && !fig.isFinished()) {
				return false;
			}
		}
		return true;
	}

	private boolean isAllowedToThrowDice(Player player) {
		Player lastThrower = dice.getLastThrower();
		int numberOfThrows = dice.getThrowsCount();

		// no previous thrower? then throw the dice!
		if (lastThrower == null) {
			return true;
		}
		
		// has figures in home field
		if (hasOnlyFiguresInHome(player)) {
			if (numberOfThrows < settings.getThrowsAllowedInHome()) {
				return true;
			}
			return false;
		}

		// public movement
		if (numberOfThrows < settings.getThrowsAllowedInPublic()) {
			return true;
		}

		return false;
	}
	
	
	public boolean moveFigure(Player player, char figureLetter) {
		if (dice.getLastThrower() != player) {
			status = "Du solltest zuerst wuerfeln!";
			notifyObservers();
			return false;
		}

		Figure figure = model.getFigureForPlayerByLetter(player, figureLetter);

		if (figure == null) {
			status = "Diese Figur gehoert dir nicht!";
			notifyObservers();
			return false;
		}

		int diceNum = dice.getLastNumber();
		
		// FINISHED
		if (figure.isFinished()) {
			status = "Figur ist schon fertig!";
			notifyObservers();
			dice.resetThrowsCount();
			return true;
		}
		
		// cannot move
		if (!figureCanMove(figure, diceNum)) {
			status = "Diese Figur kann sich nicht bewegen!";
			notifyObservers();
			return false;
		}
		
		// leave home
		if (figureCanLeaveHome(figure, diceNum)) {
			// leaveHome(figure);
			status = "Figure hat sein Zuhause verlassen!";
			notifyObservers();
			return true;
		}
		
		// move in finish area
		if (figureCanMoveInFinishArea(figure, diceNum)) {
			// move in the finish area around and maybe finish!
			status = "Figure hat Ziel betreten verlassen!";
			notifyObservers();
			return true;
		}
		
		// move in public
		if (figureCanMoveInPublic(figure, diceNum)) {
			// move into finish?
			// kick away other player?
			status = "Figure kickt X weg!";
			notifyObservers();
			return true;
		}
		
		return false;

		
		
		
		
		
		
		
		/*

		// HOME
		if (figure.isAtHomeArea()) {
			// can leave home
			if (diceNum >= settings.getMinNumberToExitHome()) {
				int exitIndex = player.getHomeField().getExitIndex();
				int currentIndex = figure.getCurrentFieldIndex();

				Figure figAtExit = board.getPublicField().getFigure(exitIndex);

				// kick away enemy
				if (figAtExit != null && figAtExit.getOwner() != player) {
					resetFigureToHome(figAtExit);
				}

				figure.setAtHomeArea(false);
				player.getHomeField().removeFigure(currentIndex);
				board.getPublicField().setFigure(exitIndex, figure);
				status = figure.getLetter() + " hat sein Zuhause verlassen.";
			} else {
				status = "Zug nicht erlaubt.";
				notifyObservers();
				return false;
			}

			dice.resetThrowsCount();
			notifyObservers();
			return true;
		}

		// FINISH
		if (figure.isAtFinishArea()) {
			int finishIndex = autonomeComtroller
					.getLastFreeFinishFieldIndex(player);
			int currentIndex = figure.getCurrentFieldIndex();
			int nextIndex = currentIndex + diceNum;
			// can move or finish
			if (finishIndex == nextIndex) {
				// finished
				figure.setAtFinishArea(false);
				figure.setFinished(true);
				player.getFinishField().removeFigure(currentIndex);
				player.getFinishField().setFigure(finishIndex, figure);
				status = figure.getLetter() + " ist im Ziel.";

			} else if (nextIndex < finishIndex) {
				// move in finish area
				board.getPublicField().removeFigure(currentIndex);
				board.getPublicField().setFigure(nextIndex, figure);
				status = figure.getLetter()
						+ " ist in den Zielbereich gelaufen.";
			} else {
				status = "Zug nicht erlaubt.";
				notifyObservers();
				return false;
			}

			dice.resetThrowsCount();
			notifyObservers();
			return true;
		}

		// MOVE IN PUBLIC

		int currentIndex = figure.getCurrentFieldIndex();
		int newIndex = (currentIndex + diceNum)
				% settings.getPublicFieldsCount();
		int finishEntry = player.getFinishField().getEntryIndex();
		int stepsToFinishArea = finishEntry - currentIndex;
		int lastFreeFinishIndex = autonomeComtroller
				.getLastFreeFinishFieldIndex(player);
		int destIndexInFinish = diceNum - stepsToFinishArea;

		// CHECK IF MIGHT GO TO FINISH FIELD...
		if (diceNum > stepsToFinishArea
				&& destIndexInFinish <= lastFreeFinishIndex) {
			// move into finish area
			if (destIndexInFinish == lastFreeFinishIndex) {
				figure.setFinished(true);
				status = figure.getLetter() + " ist im Ziel.";
			} else {
				figure.setAtFinishArea(true);
				status = figure.getLetter()
						+ " ist in den Zielbereich gelaufen.";
			}

			board.getPublicField().removeFigure(currentIndex);
			player.getFinishField().setFigure(destIndexInFinish, figure);

			dice.resetThrowsCount();
			notifyObservers();
			return true;

		} else if (autonomeComtroller.hasEnemyFigureAtIndex(player, newIndex)) {
			resetFigureToHome(board.getPublicField().getFigure(newIndex));
			board.getPublicField().removeFigure(currentIndex);
			board.getPublicField().setFigure(newIndex, figure);
			dice.resetThrowsCount();
			status = figure.getLetter() + " wurde bewegt.";
			notifyObservers();
			return true;
		} else {
			board.getPublicField().removeFigure(currentIndex);
			board.getPublicField().setFigure(newIndex, figure);
			dice.resetThrowsCount();
			status = figure.getLetter() + " wurde bewegt.";
			notifyObservers();
			return true;
		}*/
	}

	private void resetFigureToHome(Figure fig) {
		int currentIndex = fig.getCurrentFieldIndex();
		HomeField homeField = fig.getOwner().getHomeField();
		int newIndex = 0;

		for (int i = 0; i < homeField.getSize(); i++) {
			if (homeField.getFigure(i) == null) {
				newIndex = i;
				break;
			}
		}

		model.getPublicField().removeFigure(currentIndex);
		model.getPublicField().setFigure(newIndex, fig);
		fig.setAtFinishArea(false);
		fig.setAtHomeArea(true);
	}

	public void startAutonomeRun(Player player) {
		autonomeComtroller.startAutonomeRun(player);
	}

	public String getStatusString() {
		return status;
	}

	/**
	 * Autonome Movements = Bot Controller
	 * 
	 * 
	 */
	private final class AutonomeMovementController {

		public void startAutonomeRun(Player player) {

			boolean didMove = false;

			while (isAllowedToThrowDice(player)) {
				throwDice(player);

				if (didMove = maybeKickOtherPlayer(player)) {
					break;
				}
				if (didMove = maybeFinishFigure(player)) {
					break;
				}
				if (didMove = maybeMoveFigureOutHome(player)) {
					break;
				}
				if (didMove = moveFigureOnPublic(player)) {
					break;
				}
			}

			if (!didMove) {
				status = "\"" + player.getName()
						+ "\" kann sich nicht bewegen.";
			} else {
				status = "\"" + player.getName() + "\" HAT SICH BEWEGT.";
			}

			notifyObservers();
		}

		// return true if figure was moved
		private boolean maybeKickOtherPlayer(Player player) {
			int diceNum = dice.getLastNumber();

			for (Figure fig : player.getFigures()) {
				// cant kick other figure in finish area or if already finished
				if (fig.isFinished() || fig.isAtFinishArea()) {
					continue;
				}
				// where we could move
				int destIndex = fig.getCurrentFieldIndex() + diceNum;

				// check if we are at home and could leave it to kick enemy
				int homeExitIndex = player.getHomeField().getExitIndex();
				if (figureCanLeaveHome(fig, diceNum)
						&& hasEnemyFigureAtIndex(player, homeExitIndex)) {
					moveFigure(player, fig.getLetter());
					return true;
				}

				// has enemy figure at this index?
				if (hasEnemyFigureAtIndex(player, destIndex)) {
					moveFigure(player, fig.getLetter());
					return true;
				}
			}

			return false;
		}

		private boolean hasEnemyFigureAtIndex(Player player, int index) {
			Figure enemy = model.getPublicField().getFigure(index);
			// there is a figure we could kick and doesn't belongs us
			if (enemy != null && enemy.getOwner() != player) {
				return true;
			}
			return false;
		}

		private boolean figureCanLeaveHome(Figure fig, int diceNumber) {
			return fig.isAtHomeArea()
					&& diceNumber >= settings.getMinNumberToExitHome();
		}

		// return true if figure was moved
		private boolean moveFigureOnPublic(Player player) {
			for (Figure fig : player.getFigures()) {
				if (!fig.isFinished() & !fig.isAtFinishArea()
						&& !fig.isAtHomeArea()) {
					moveFigure(player, fig.getLetter());
					return true;
				}
			}
			return false;
		}

		// return true if figure was moved
		private boolean maybeMoveFigureOutHome(Player player) {
			for (Figure fig : player.getFigures()) {
				if (fig.isAtHomeArea()
						&& dice.getLastNumber() > settings
								.getMinNumberToExitHome()) {
					moveFigure(player, fig.getLetter());
					return true;
				}
			}
			return false;
		}

		// finish figure
		// return true if figure was moved
		private boolean maybeFinishFigure(Player player) {
			int diceNum = dice.getLastNumber();

			for (Figure fig : player.getFigures()) {
				if (fig.isFinished() || fig.isAtHomeArea()) {
					continue;
				}

				if (fig.isAtFinishArea()) {
					int destIndex = diceNum + fig.getCurrentFieldIndex();
					if (destIndex == getLastFreeFinishFieldIndex(player)) {
						moveFigure(player, fig.getLetter());
						return true;
					}
				}
			}

			return false;
		}

		// might return -1 if no one are free
		private int getLastFreeFinishFieldIndex(Player player) {
			// default the last field
			int lastFreeIndex = player.getFinishField().getSize() - 1;

			for (int i = 0; i < player.getFinishField().getSize(); i++) {
				Figure fig = player.getFinishField().getFigure(i);
				if (fig != null) {
					lastFreeIndex = i;
				}
			}
			if (player.getFinishField().getFigure(lastFreeIndex) != null) {
				lastFreeIndex = -1;
			}
			return lastFreeIndex;
		}

	}

}
