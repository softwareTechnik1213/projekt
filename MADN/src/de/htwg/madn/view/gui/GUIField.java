package de.htwg.madn.view.gui;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public final class GUIField extends JButton {

	private final int id;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;

	public GUIField(final int i) {
		this.id = i;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public int getId() {
		return this.id;
	}

}
