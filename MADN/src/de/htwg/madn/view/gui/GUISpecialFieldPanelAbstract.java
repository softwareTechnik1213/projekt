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
	protected final int id;
	protected final Color emptyColor = this.getForeground();
	private char type;
	protected static final Color ACTIVE_COLOR = Color.YELLOW.brighter();
	protected static final Color INACTIVE_COLOR = Color.WHITE;

	public GUISpecialFieldPanelAbstract(IBoardControllerPort contr, int id,
			char type) {
		this.id = id;
		this.type = type;
		this.controller = contr;
		contr.addObserver(this);
		this.fields = new GUIField[controller.getSettings()
				.getFiguresPerPlayer()];
		initGui();
	}

	protected abstract void initGui();

	@Override
	public final void update() {

		Player fieldOwner = getSpecialField().getOwner();

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
			Figure fig = getSpecialField().getFigure(i);
			if (fig == null) {
				fields[i].setText("");
				fields[i].setForeground(emptyColor);
				fields[i].setEnabled(false);
			} else {
				fields[i].setText(String.valueOf(fig.getLetter()));
				fields[i].setForeground(fig.getOwner().getColor());
				if (!fig.isFinished()) {
					fields[i].setEnabled(true);
				} else {
					fields[i].setEnabled(false);
				}
			}
		}
	}

	protected final AbstractSpecialField getSpecialField() {
		AbstractSpecialField specialField;

		if (type == 'f') {
			specialField = controller.getModelPort().getFinishFields().get(id);
		} else {
			specialField = controller.getModelPort().getHomeFields().get(id);
		}
		
		return specialField;
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		GUIField field = (GUIField) e.getSource();
		Figure fig = getSpecialField().getFigure(field.getId());

		if (fig != null) {
			controller.moveFigure(fig.getLetter());
		}
	}

}
