package de.htwg.madn.model;

import java.util.List;

public interface IFieldContainer {

    List<Field> fieldList();
    List<Field> getFieldsListByOccupier(Player p);
}
