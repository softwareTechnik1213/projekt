package de.htwg.madn.controller;

import de.htwg.madn.model.Dice;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.FinishField;
import de.htwg.madn.model.HomeField;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.model.Player;
import de.htwg.madn.model.PublicField;
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
		} else {
			status = "Du darfst nicht wuerfeln.";
		}

		// has throws left, check if he can move
		if (dice.getThrowsCount() > 0) {
			canMove = playerCanMove(player, dice.getLastNumber());
		}

		// if can move or can throw dice do not change the player
		if (canMove || hasThrowsLeft(player)) {
			setNext = false;
		} else {
			setNext = true;
			status += " Du kannst dich nicht bewegen!";
			dice.resetThrowsCount();
		}

		notifyObservers();
		return setNext;
	}

	private boolean hasThrowsLeft(Player player) {
		int diceThrows = dice.getThrowsCount();

		if (hasOnlyFiguresInHome(player)
				&& diceThrows >= settings.getThrowsAllowedInHome()) {
			return false;
		}
		if (!hasOnlyFiguresInHome(player)
				&& diceThrows >= settings.getThrowsAllowedInPublic()) {
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
		int nextIndex = (fig.getCurrentFieldIndex() + fieldsToMove)
				% settings.getPublicFieldsCount();
		int finishEntryIndex = fig.getOwner().getFinishField().getEntryIndex();

		if (fig.isAtHomeArea() || fig.isAtFinishArea()) {
			return false;
		}

		// check if figure might entry finish field
		if (entersFinishField(fieldsToMove, finishEntryIndex,
				fig.getCurrentFieldIndex())) {
			int stepsToMove = getStepsToFinish(fieldsToMove, finishEntryIndex, fig.getCurrentFieldIndex());
			int finishIndex=fieldsToMove-stepsToMove-1;		
			if(finishIndex<0) {
				throw new IllegalStateException();
			}
			FinishField finishField = fig.getOwner().getFinishField();

			if (finishIndex < finishField.getSize()
					&& finishField.getFigure(finishIndex) == null) {
				return true;
			}
			return false;
		}

		if (!hasOwnFigureOnField(nextIndex, fig.getOwner())) {
			return true;
		}

		return false;
	}
	private int getStepsToFinish (int diceNum, int finishEntryIndex,
				int currentIndex) {

			for (int i = 0; i < diceNum; i++) {
				int fieldIndex = (currentIndex + i) % model.getPublicField().getSize();
				if (fieldIndex == (finishEntryIndex)) {
					return i;
				}
			}

			return -1;
		}

	

	private boolean hasOwnFigureOnField(int index, Player player) {
		Figure fig = model.getPublicField().getFigure(index);
		return fig != null && fig.getOwner() == player;
	}

	private boolean figureCanMoveInFinishArea(Figure fig, int fieldsToMove) {
		if (!fig.isAtFinishArea()) {
			return false;
		}
		int newIndex = fig.getCurrentFieldIndex() + fieldsToMove;
		int lastFreeIndex = getLastFreeFinishIndex(fig.getOwner());
		return newIndex <= lastFreeIndex
				&& fig.getOwner().getFinishField().getFigure(newIndex) != null;
	}

	private boolean figureCanLeaveHome(Figure fig, int fieldsToMove) {
		int destIndex = fig.getOwner().getHomeField().getExitIndex();
		return fig.isAtHomeArea()
				&& fieldsToMove >= settings.getMinNumberToExitHome()
				&& !hasOwnFigureOnField(destIndex, fig.getOwner());
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
		if (dice.getLastThrower() != player || dice.getThrowsCount() == 0) {
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
			Figure kickedAwayFigure = leaveHome(figure);
			String kicked = ".";
			if (kickedAwayFigure != null) {
				kicked = " und hat " + kickedAwayFigure.getLetter()
						+ " weggekickt.";
			}
			status = "Figur " + figure.getLetter()
					+ " hat sein Zuhause verlassen" + kicked;
			// player can throw the dice again after leaving home, so reset it
			dice.resetThrowsCount();
			notifyObservers();
			return false;
		}

		// move in finish area
		if (figureCanMoveInFinishArea(figure, diceNum)) {
			// move in the finish area around and maybe finish!
			int lastFreeIndex = getLastFreeFinishIndex(figure.getOwner());
			int newIndex = figure.getCurrentFieldIndex() + diceNum;
			FinishField finishField = figure.getOwner().getFinishField();
			// finished!
			if (newIndex == lastFreeIndex) {
				figure.setFinished(true);
			}
			finishField.removeFigure(figure.getCurrentFieldIndex());
			finishField.setFigure(newIndex, figure);
			status = "Figur " + figure.getLetter() + " hat sich bewegt!";
			notifyObservers();
			return true;
		}

		// move in public
		if (figureCanMoveInPublic(figure, diceNum)) {
			int newIndex = (figure.getCurrentFieldIndex() + diceNum)
					% settings.getPublicFieldsCount();
			int finishEntryIndex = figure.getOwner().getFinishField()
					.getEntryIndex();
			int lastFreeIndex = getLastFreeFinishIndex(figure.getOwner());
			PublicField publicField = model.getPublicField();
			FinishField finishField = figure.getOwner().getFinishField();
			String kicked = ".";

			// check if figure might entry finish field
			if (entersFinishField(diceNum, finishEntryIndex,
					figure.getCurrentFieldIndex())) {
				int stepsToMove = getStepsToFinish(diceNum, finishEntryIndex, figure.getCurrentFieldIndex());
				int finishIndex=diceNum-stepsToMove-1;		
				if(finishIndex<0) {
					throw new IllegalStateException();
				}
				// finished!
				if (finishIndex == lastFreeIndex) {
					figure.setFinished(true);
				}
				publicField.removeFigure(figure.getCurrentFieldIndex());
				finishField.setFigure(finishIndex, figure);
			} else {
				// move on public
				// kick away other player?
				Figure kickedFigure = kickFigureIfPossible(newIndex);
				publicField.removeFigure(figure.getCurrentFieldIndex());
				publicField.setFigure(newIndex, figure);
				if (kickedFigure != null) {
					kicked = " und hat " + kickedFigure.getLetter()
							+ " weggekickt.";
				}
			}
			status = "Figur " + figure.getLetter() + " hat sich bewegt"
					+ kicked;
			notifyObservers();
			return true;
		}

		return false;
	}

	private boolean entersFinishField(int diceNum, int finishEntryIndex,
			int currentIndex) {

		for (int i = 1; i <= diceNum; i++) {
			int fieldIndex = (currentIndex + i) % model.getPublicField().getSize();
			if (fieldIndex == (finishEntryIndex + 1)
					% model.getPublicField().getSize()) {
				return true;
			}
		}

		return false;
	}

	private int getLastFreeFinishIndex(Player player) {
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

	private Figure leaveHome(Figure figure) {
		PublicField publicField = model.getPublicField();
		HomeField homeField = figure.getOwner().getHomeField();
		int newIndex = homeField.getExitIndex();
		homeField.removeFigure(figure.getCurrentFieldIndex());
		// might kick back enemy
		Figure kickedFigure = kickFigureIfPossible(newIndex);
		figure.setAtHomeArea(false);
		publicField.setFigure(newIndex, figure);
		return kickedFigure;
	}

	private Figure kickFigureIfPossible(int index) {
		Figure fig = model.getPublicField().getFigure(index);
		if (fig != null) {
			resetFigureToHome(fig);
		}
		return fig;
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
		homeField.setFigure(newIndex, fig);
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
