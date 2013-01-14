package de.htwg.madn.view.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.FinishField;
import de.htwg.madn.model.HomeField;

@SuppressWarnings("serial")
public final class GUIGameFieldPanel extends JPanel {

	private IBoardControllerPort controller;
	private GUISpecialFieldPanelAbstract[] homeFields;
	private GUIPublicFieldPanel publicPanel;
	private GUISpecialFieldPanelAbstract[] finishFields;

	public GUIGameFieldPanel(GUIView guiView) {
		this.controller = guiView.getBoardControllerPort();
		int playerMax = controller.getSettings().getMaxPlayers();
		this.publicPanel = new GUIPublicFieldPanel(controller);
		this.homeFields = new GUIHomeFieldPanel[playerMax];
		this.finishFields = new GUIFinishFieldPanel[playerMax];
		
		for (int i = 0; i < playerMax; i++) {
			HomeField homeFieldModel = controller.getModelPort().getHomeFields().get(i);
			FinishField finishFieldModel = controller.getModelPort().getFinishFields().get(i);
			homeFields[i] = new GUIHomeFieldPanel(controller, homeFieldModel);
			finishFields[i] = new GUIFinishFieldPanel(controller, finishFieldModel);
		}
		
		initGui();
	}

	private void initGui() {
		
		int numberSpecialFields = controller.getSettings().getMaxPlayers();
		int numberPublicFields = controller.getSettings().getPublicFieldsCount();
		
		this.setLayout(new GridLayout(3, 1));
		
		JPanel homePanel = new JPanel(new GridBagLayout());
		JPanel finishPanel = new JPanel(new GridLayout(1, numberSpecialFields));
		
		int x = 0;
		
		for (GUISpecialFieldPanelAbstract hf : homeFields) {
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = x;
			homePanel.add(hf, c);
			x++;
		}
		
		x = 0;
		
		for (GUISpecialFieldPanelAbstract ff : finishFields) {
			finishPanel.add(ff);
		}
		
		this.add(homePanel);
		this.add(publicPanel);
		this.add(finishPanel);
	}

}
