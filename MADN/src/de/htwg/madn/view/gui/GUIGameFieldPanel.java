package de.htwg.madn.view.gui;

import java.awt.FlowLayout;
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
			HomeField homeFieldModel = controller.getModelPort()
					.getHomeFields().get(i);
			FinishField finishFieldModel = controller.getModelPort()
					.getFinishFields().get(i);
			homeFields[i] = new GUIHomeFieldPanel(controller, homeFieldModel);
			finishFields[i] = new GUIFinishFieldPanel(controller,
					finishFieldModel);
		}

		initGui();
	}

	private void initGui() {
		this.setLayout(new GridLayout(3, 1));

		JPanel homePanel = new JPanel(new GridLayout(1, homeFields.length));
		JPanel finishPanel = new JPanel(new GridLayout(1, finishFields.length));

		for (GUISpecialFieldPanelAbstract hf : homeFields) {
			JPanel hfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			hfPanel.add(hf);
			homePanel.add(hfPanel);
		}

		for (GUISpecialFieldPanelAbstract ff : finishFields) {
			JPanel ffPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			ffPanel.add(ff);
			finishPanel.add(ffPanel);
		}

		this.add(homePanel);
		this.add(publicPanel);
		this.add(finishPanel);
	}
}
