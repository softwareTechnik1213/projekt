package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public class HomeField implements RestrictedField {

	private List<Field> fields = new LinkedList<Field>();
	private Player owner;
	
	public HomeField(Player o, List<Field> f) {
		if (o == null || f == null) {
			throw new IllegalArgumentException();
		}
		if (f.size() > RestrictedField.MAX_FIELDS) {
			throw new IllegalArgumentException("exceeding max fields limit");
		}
		fields.addAll(f);
		this.owner = o;
	}
	
	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public List<Field> getFields() {
		return fields;
	}

}
