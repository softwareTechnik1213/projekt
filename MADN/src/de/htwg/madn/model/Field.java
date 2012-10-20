package de.htwg.madn.model;

public final class Field {

    private Player occupier;
    private char figureCode;

    public Player getOccupier() {
        return occupier;
    }

    public void setOccupier(Player player, char figureCode) {
        this.occupier = player;
        this.figureCode = figureCode;
    }

    public char toChar(char c) {
        return occupier == null ? c : figureCode;
    }

}