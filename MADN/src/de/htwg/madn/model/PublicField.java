package de.htwg.madn.model;

public final class PublicField {
	private final Figure[] fields;

	public PublicField(final int fieldsCount) {
		fields = new Figure[fieldsCount];
	}

	public void removeFigure(final int fieldIndex) {
		checkLegalFieldAccess(fieldIndex);
		fields[fieldIndex].setCurrentFieldIndex(-1);
		fields[fieldIndex] = null;
	}

	public void setFigure(final int fieldIndex, Figure figure) {
		checkLegalFieldAccess(fieldIndex);
		fields[fieldIndex] = figure;
		figure.setCurrentFieldIndex(fieldIndex);
	}
	
	public Figure getFigure(final int fieldIndex) {
		checkLegalFieldAccess(fieldIndex);
		return fields[fieldIndex];
	}
	
	public int getSize() {
		return fields.length;
	}
	
	private void checkLegalFieldAccess(final int fieldIndex) {
		if (fieldIndex < 0 || fieldIndex > fields.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
}
