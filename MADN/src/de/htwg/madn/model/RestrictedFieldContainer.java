package de.htwg.madn.model;

public interface RestrictedFieldContainer extends FieldContainer {
	int MAX_FIELDS = 4;

	Player getOwner();
}
