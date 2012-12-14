package de.htwg.madn.model;

public final class FinishField extends AbstractSpecialField {
	private final int entryIndex;
	
	public FinishField(final int entryIndex, final int fieldsCount) {
		super(fieldsCount);
		this.entryIndex = entryIndex;
	}
	
	@Override
	public int getEntryIndex() {
		return entryIndex;
	}

	@Override
	public int getExitIndex() {
		throw new UnsupportedOperationException("finish field has no exit index");
	}
	
}
