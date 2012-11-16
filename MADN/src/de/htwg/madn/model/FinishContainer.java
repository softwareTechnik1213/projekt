package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;

public final class FinishContainer implements IRestrictedFieldContainer {

    private List<Field> fields = new LinkedList<Field>();
    private Player owner;

    public FinishContainer(int fieldsPerContainer) {
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
        int size = fields.size();
        char[][] ret = new char[1][size];
        int i = 0;

        for (Field f : fields) {
            ret[0][i] = f.toChar('.');
            i++;
        }
        return ret;
    }

	@Override
	public List<Field> getFieldsListByOccupier(Player p) {
		List<Field> resultList = new LinkedList<Field>();
		for (Field f : fields) {
			if (f.getOccupier() == p) {
				resultList.add(f);
			}
		}
		return resultList;
	}
}
