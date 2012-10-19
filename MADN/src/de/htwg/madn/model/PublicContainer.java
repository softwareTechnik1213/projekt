package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public class PublicContainer implements FieldContainer {
	
	private List<Field> fields = new LinkedList<Field>();
	
//	public PublicContainer(List<Field> f) {
//		if (f == null) {
//			throw new IllegalArgumentException();
//		}
//		fields.addAll(f);
//	}
	
	
	
	@Override
	public List<Field> getFieldsList() {
		return fields;
	}

}
