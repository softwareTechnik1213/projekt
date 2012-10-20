package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public final class PublicContainer implements IFieldContainer {

    private List<Field> fields = new LinkedList<Field>();

    public PublicContainer(int fieldsPerContainer) {
        for (int i = 0; i < fieldsPerContainer; i++) {
            fields.add(new Field());
        }
    }

    @Override
    public List<Field> fieldList() {
        return fields;
    }
}
