package de.htwg.madn.model;

public final class HomeField {
	private final int exitIndex;
	private Player owner;
	private final Figure[] fields;
	
	public HomeField(final int exitIndex, final int fieldsCount) {
		this.exitIndex = exitIndex;
		fields = new Figure[fieldsCount];
	}

	public int getExitIndex() {
		return exitIndex;
	}
	
	public void setOwner(Player owner) {
		if (owner == null) {
			return;
		}
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}
	
	public void removeFigure(final int fieldIndex) {
		checkLegalFieldAccess(fieldIndex);
		fields[fieldIndex].setCurrentFieldIndex(-1);
		fields[fieldIndex] = null;
	}
	
	public void setFigure(final int fieldIndex, final Figure figure) {
		checkLegalFieldAccess(fieldIndex);
		fields[fieldIndex] = figure;
		figure.setCurrentFieldIndex(fieldIndex);
	}
	
	public Figure getFigure(final int fieldIndex) {
		checkLegalFieldAccess(fieldIndex);
		return fields[fieldIndex];
	}

	public boolean isEmpty() {
		for (Figure fig : fields) {
			if (fig != null) {
				return false;
			}
		}
		return true;
	}
	
	private void checkLegalFieldAccess(final int fieldIndex) {
		if (fieldIndex < 0 || fieldIndex > fields.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
}
