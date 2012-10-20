package de.htwg.madn.model;

import java.awt.Color;

public final class Player {

    private final int id;
    private final Color color;
    private final String name;
    private final int figures;

    public Player(final int x, final Color col, final String name, int fig) {
        this.id = x;
        this.color = col;
        this.name = name;
        this.figures = fig;
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
