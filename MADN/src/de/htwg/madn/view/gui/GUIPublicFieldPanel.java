package de.htwg.madn.view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.PublicField;
import de.htwg.madn.util.observer.IObserver;

@SuppressWarnings("serial")
public final class GUIPublicFieldPanel extends JPanel implements ActionListener,
		IObserver {
	
	private final Color emptyColor = this.getBackground();
	private PublicField publicField;
	private IBoardControllerPort controller; 
	private GUIField[] fields;

	public GUIPublicFieldPanel(IBoardControllerPort contr) {
		this.controller = contr;
		controller.addObserver(this);
		this.publicField = controller.getModelPort().getPublicField();
		this.fields = new GUIField[publicField.getSize()];
		initGui();
	}

	private void initGui() {
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new GUIField(i);
			fields[i].addActionListener(this);
			fields[i].setOpaque(true);
			this.add(fields[i]);
		}

		this.setLayout(new GridLayout(1, 0));

		this.setBorder(GUISpecialFieldBorderFactory.createPublicBorder());

		// init drawing of fields
		update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GUIField field = (GUIField) e.getSource();
		Figure fig = publicField.getFigure(field.getId());
		
		if (fig != null) {
			controller.moveFigure(fig.getLetter());
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < fields.length; i++) {
			Figure fig = publicField.getFigure(i);
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

}
