package de.htwg.madn.view.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.AbstractSpecialField;

@SuppressWarnings("serial")
public final class GUIHomeFieldPanel extends GUISpecialFieldPanel {

	public GUIHomeFieldPanel(IBoardControllerPort contr, int figures,
			AbstractSpecialField sp) {
		super(contr, figures, sp);

	}

	@Override
	protected void initGui() {
		nameFld = new JTextField(NAME_SIZE);
		nameFld.setEditable(false);
		nameFld.setOpaque(true);
		
		JPanel namePanel = new JPanel();
		namePanel.add(nameFld);

		JPanel fieldPanel = new JPanel(new GridLayout(2, 2));
		
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new GUIField(i);
			fields[i].addActionListener(this);
			fields[i].setOpaque(true);
			fieldPanel.add(fields[i]);
		}
		
		this.setLayout(new GridLayout(2, 1));
		
		this.add(namePanel);
		this.add(fieldPanel);
		
		// init drawing of fields
		update();
	}

}
