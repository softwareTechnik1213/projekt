package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public class FinishContainer implements RestrictedFieldContainer {

	private List<Field> fields = new LinkedList<Field>();
	private Player owner;
	
	public FinishContainer(Player o) {
		this.owner = o;
	}
	
	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public List<Field> getFieldsList() {
		return fields;
	}

}
