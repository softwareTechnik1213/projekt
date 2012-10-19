package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public class HomeContainer implements RestrictedFieldContainer {

	private List<Field> fields = new LinkedList<Field>();

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	private Player owner;
	
	public HomeContainer(Player o) {
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
