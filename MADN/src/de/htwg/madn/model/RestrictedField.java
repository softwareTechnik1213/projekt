package de.htwg.madn.model;

import java.util.List;

public interface RestrictedField {
	int MAX_FIELDS = 4;

	Player getOwner();

	List<Field> getFields();
}
