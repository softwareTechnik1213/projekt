package de.htwg.madn.model;


public final class Figure {
	private final char letter;
	private final Player owner;
	private boolean atHomeArea = true;
	private boolean atFinishArea = false;
	private boolean finished = false;
	private int currentFieldIndex = -1;

	public Figure(final char letter, final Player owner) {
		this.letter = letter;
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public char getLetter() {
		return this.letter;
	}
	
	public boolean isAtHomeArea() {
		return atHomeArea;
	}

	public void setAtHomeArea(final boolean atHomeArea) {
		this.atHomeArea = atHomeArea;
	}

	public boolean isAtFinishArea() {
		return atFinishArea;
	}

	public void setAtFinishArea(final boolean atFinishArea) {
		this.atFinishArea = atFinishArea;
	}
	
	public int getCurrentFieldIndex() {
		return currentFieldIndex;
	}
	
	public void setCurrentFieldIndex(final int newIndex) {
		currentFieldIndex = newIndex;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(final boolean finished) {
		this.finished = finished;
	}
}
