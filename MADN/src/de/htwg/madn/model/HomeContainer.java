package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public final class HomeContainer implements IRestrictedFieldContainer {

    private List<Field> fields = new LinkedList<Field>();
    private Player owner;

    public HomeContainer(int fieldsPerContainer) {
        for (int i = 0; i < fieldsPerContainer; i++) {
            fields.add(new Field());
        }
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player o) {
        this.owner = o;
    }

    @Override
    public List<Field> fieldList() {
        return fields;
    }

    public char[][] toCharArray() {
        char[][] ret = new char[2][2];
        char c = 'O';

        ret[0][0] = fields.get(0).toChar(c);
        ret[0][1] = fields.get(1).toChar(c);
        ret[1][0] = fields.get(3).toChar(c);
        ret[1][1] = fields.get(2).toChar(c);

        return ret;
    }
}
