package de.htwg.madn.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.AbstractSpecialField;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.IObserver;

@SuppressWarnings("serial")
public abstract class GUISpecialFieldPanelAbstract extends JPanel implements
		ActionListener, IObserver {

	protected GUIField[] fields;
	protected JTextField nameFld;
	protected IBoardControllerPort controller;
	protected static final int NAME_SIZE = 5;
	protected final Color emptyColor = this.getBackground();
	/**
	 * Model link to the special field
	 */
	protected AbstractSpecialField specialField;
	protected static final Color ACTIVE_COLOR = Color.ORANGE;
	protected static final Color INACTIVE_COLOR = Color.WHITE;

	public GUISpecialFieldPanelAbstract(IBoardControllerPort contr,	AbstractSpecialField sp) {
		this.specialField = sp;
		this.controller = contr;
		contr.addObserver(this);
		this.fields = new GUIField[controller.getSettings().getFiguresPerPlayer()];
		initGui();
	}

	protected abstract void initGui();

	@Override
	public final void update() {
		Player fieldOwner = specialField.getOwner();

		if (fieldOwner != null) {
			nameFld.setText(fieldOwner.getName());
		} else {
			nameFld.setText(null);
		}

		if (fieldOwner != null && fieldOwner == controller.getActivePlayer()) {
			nameFld.setBackground(ACTIVE_COLOR);
		} else {
			nameFld.setBackground(INACTIVE_COLOR);
		}

		for (int i = 0; i < fields.length; i++) {
			Figure fig = specialField.getFigure(i);
			if (fig == null) {
				fields[i].setText("");
				fields[i].setBackground(emptyColor);
				fields[i].setEnabled(false);
			} else {
				fields[i].setText(String.valueOf(fig.getLetter()));
				fields[i].setBackground(fig.getOwner().getColor());
				if (!fig.isFinished()) {
					fields[i].setEnabled(true);
				} else {
					fields[i].setEnabled(false);
				}
			}
		}
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		GUIField field = (GUIField) e.getSource();
		Figure fig = specialField.getFigure(field.getId());

		if (fig != null) {
			controller.moveFigure(fig.getLetter());
		}
	}

}
