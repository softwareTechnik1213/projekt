package de.htwg.madn.view.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.Figure;
import de.htwg.madn.util.observer.IObserver;

@SuppressWarnings("serial")
public final class GUIPublicFieldPanel extends JPanel implements ActionListener,
		IObserver {
	
	private final Color emptyColor = this.getForeground();
	private IBoardControllerPort controller; 
	private GUIField[] fields;
	private static final int FIELDS_HGAP = 5;

	public GUIPublicFieldPanel(IBoardControllerPort contr) {
		this.controller = contr;
		controller.addObserver(this);
		this.fields = new GUIField[controller.getModelPort().getPublicField().getSize()];
		initGui();
	}

	private void initGui() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, FIELDS_HGAP, 0));
		this.setBorder(GUIBorderFactory.createPublicBorder());
		
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new GUIField(i);
			fields[i].addActionListener(this);
			fields[i].setOpaque(true);
			this.add(fields[i]);
		}

		// init drawing of fields
		update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GUIField field = (GUIField) e.getSource();
		Figure fig = controller.getModelPort().getPublicField().getFigure(field.getId());
		
		if (fig != null) {
			controller.moveFigure(fig.getLetter());
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < fields.length; i++) {
			Figure fig = controller.getModelPort().getPublicField().getFigure(i);
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

}
