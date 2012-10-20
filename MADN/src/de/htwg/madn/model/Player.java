package de.htwg.madn.model;

import java.awt.Color;

public final class Player {

    private final int id;
    private final Color color;
    private final String name;

    public Player(final int x, final Color col, final String name) {
        this.id = x;
        this.color = col;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
