package de.htwg.madn.model;

public final class Field {

    public enum Type {

        HOME, PUBLIC, FINISH
    };
    private Player myPlayer;
    private final Type type;

    public Field(Type fieldType) {
        this.type = fieldType;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public void setPlayer(Player player) {
        this.myPlayer = player;
    }

    public Type getType() {
        return this.type;
    }
}