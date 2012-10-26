package de.htwg.madn.model;

public interface IRestrictedFieldContainer extends IFieldContainer {

    int MAX_FIELDS = 4;

    void setOwner(Player o);

    Player getOwner();
}
