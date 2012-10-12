package de.htwg_konstanz.ain3.madn.model;

import de.htwg_konstanz.ain3.madn.model.util.FieldType;

public final class Field {

    private Player myPlayer;
    private final FieldType type;

    public Field(FieldType fieldType) {
        this.type = fieldType;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public void setPlayer(Player player) {
        this.myPlayer = player;
    }

    public FieldType getType() {
        return this.type;
    }
}