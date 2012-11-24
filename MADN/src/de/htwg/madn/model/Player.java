package de.htwg.madn.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class Player {

	private final int id;
	private final Color color;
	private final String name;
	private final List<Figure> figures;
	private final FinishField finishField;
	private final HomeField homeField;
	private final boolean isHuman;
	private static final char FIGURE_LETTER_START = 'a';

	public Player(final int x, final Color color, final String name,
			final HomeField homeField, final FinishField finishField,
			final int figuresCount, boolean isHuman) {
		
		if (homeField == null || finishField == null) {
			throw new IllegalArgumentException(
					"homeField and finishField cant be null");
		}
		
		this.id = x;
		this.color = color;
		this.name = name;
		this.homeField = homeField;
		this.finishField = finishField;
		this.homeField.setOwner(this);
		this.finishField.setOwner(this);
		this.isHuman = isHuman;
		this.figures = new LinkedList<Figure>();

		addFigures(figuresCount);
		occupyHomeField();
	}
	
	private void addFigures(int figuresCount) {
		for (int i = 0; i < figuresCount; i++) {
			char letter = (char) (FIGURE_LETTER_START + id * figuresCount + i);
			figures.add(new Figure(letter, this));
		}
	}

	private void occupyHomeField() {
		for (int i = 0; i < figures.size(); i++) {
			homeField.setFigure(i, figures.get(i));
		}
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

	public boolean hasFigure(Figure figureCheck) {
		for (Figure fig : this.figures) {
			if (fig == figureCheck) {
				return true;
			}
		}
		return false;
	}

	public List<Figure> getFigures() {
		return figures;
	}

	public FinishField getFinishField() {
		return finishField;
	}

	public HomeField getHomeField() {
		return homeField;
	}

	public boolean isHuman() {
		return isHuman;
	}
}
