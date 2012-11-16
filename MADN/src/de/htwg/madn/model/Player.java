package de.htwg.madn.model;

import java.awt.Color;

public final class Player {

	private final int id;
	private static final int FIGURES = 4;
	private static final char START_CHAR = 'a';
	private final Color color;
	private final String name;

	public Player(final int x, final Color col, final String name) {
		this.id = x;
		this.color = col;
		this.name = name;
	}

	public char[] getFigureCodes() {
		char start = (char) ((int) START_CHAR + id * FIGURES);
		char codes[] = new char[FIGURES];
		for (int i = 0; i < FIGURES; i++) {
			codes[i] = (char) ((int) start + i);
		}
		return codes;
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
