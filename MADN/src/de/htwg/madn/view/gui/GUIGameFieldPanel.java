package de.htwg.madn.view.gui;

import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.FinishField;
import de.htwg.madn.model.HomeField;

@SuppressWarnings("serial")
public final class GUIGameFieldPanel extends JPanel {

	private IBoardControllerPort controller;
	private GUISpecialFieldPanel[] homeFields;
	private GUIPublicFieldPanel publicField;
	private GUISpecialFieldPanel[] finishFields;
	private static final int PLAYERS = 4;
	private static final int FIGURES = 4;
	private static final int PUBLIC_COUNT = 40;

	public GUIGameFieldPanel(GUIView guiView) {
		this.controller = guiView.getBoardControllerPort();
		this.publicField = new GUIPublicFieldPanel(controller, PUBLIC_COUNT);
		this.homeFields = new GUIHomeFieldPanel[PLAYERS];
		this.finishFields = new GUIFinishFieldPanel[PLAYERS];
		
		for (int i = 0; i < PLAYERS; i++) {
			HomeField homeFieldModel = controller.getModelPort().getHomeFields().get(i);
			FinishField finishFieldModel = controller.getModelPort().getFinishFields().get(i);
			homeFields[i] = new GUIHomeFieldPanel(controller, FIGURES, homeFieldModel);
			finishFields[i] = new GUIFinishFieldPanel(controller, FIGURES, finishFieldModel);
		}
		
		initGui();
	}

	private void initGui() {
		for (GUISpecialFieldPanel hf : homeFields) {
			this.add(hf);
		}
		this.add(publicField);
		for (GUISpecialFieldPanel ff : finishFields) {
			this.add(ff);
		}
	}

}
