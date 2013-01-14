package de.htwg.madn.view.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.AbstractSpecialField;

@SuppressWarnings("serial")
public final class GUIHomeFieldPanel extends GUISpecialFieldPanelAbstract {

	private static final int HGAP = 5;
	private static final int VGAP = 5;

	public GUIHomeFieldPanel(IBoardControllerPort contr, AbstractSpecialField sp) {
		super(contr, sp);

	}

	@Override
	protected void initGui() {
		nameFld = new JTextField(NAME_SIZE);
		nameFld.setEditable(false);
		nameFld.setOpaque(true);

		JPanel namePanel = new JPanel();
		namePanel.add(nameFld);

		JPanel fieldPanel = new JPanel(new GridLayout(1, 0, HGAP, VGAP));

		for (int i = 0; i < fields.length; i++) {
			fields[i] = new GUIField(i);
			fields[i].addActionListener(this);
			fields[i].setOpaque(true);
			fieldPanel.add(fields[i]);
		}

		this.setLayout(new GridLayout(2, 1));

		this.add(namePanel);
		this.add(fieldPanel);

		this.setBorder(GUISpecialFieldBorderFactory.createSpecialBorder());

		// init drawing of fields
		update();
	}

}
