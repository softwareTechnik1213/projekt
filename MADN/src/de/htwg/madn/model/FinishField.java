package de.htwg.madn.model;

public final class FinishField {
	private final int entryIndex;
	private Player owner;
	private final Figure[] fields;
	
	public FinishField(final int entryIndex,final int fieldsCount) {
		this.entryIndex = entryIndex;
		fields = new Figure[fieldsCount];
	}
	
	public void setOwner(Player owner) {
		if (this.owner == null) {
			return;
		}
		this.owner = owner;
	}

	public final int getEntryIndex() {
		return entryIndex;
	}

	public final Player getOwner() {
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
		boolean empty = true;
		for (Figure fig : fields) {
			if (fig != null) {
				empty = false;
				break;
			}
		}
		return empty;
	}
	
	private void checkLegalFieldAccess(final int fieldIndex) {
		if (fieldIndex < 0 || fieldIndex > fields.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
}
