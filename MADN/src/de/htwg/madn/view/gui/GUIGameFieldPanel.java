package de.htwg.madn.view.gui;

import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;

@SuppressWarnings("serial")
public final class GUIGameFieldPanel extends JPanel {

	private IBoardControllerPort controller;
	private GUIHomeField[] homeFields;
	private GUIPublicField publicField;
	private GUIFinishField[] finishFields;
	private static final int PLAYERS = 4;
	private static final int FIGURES = 4;
	private static final int PUBLIC_COUNT = 40;

	public GUIGameFieldPanel(GUIView guiView) {
		this.controller = guiView.getBoardControllerPort();
		this.publicField = new GUIPublicField(PUBLIC_COUNT);
		this.homeFields = new GUIHomeField[PLAYERS];
		this.finishFields = new GUIFinishField[PLAYERS];
		for (int i = 0; i < PLAYERS; i++) {
			homeFields[i] = new GUIHomeField(FIGURES);
			finishFields[i] = new GUIFinishField(FIGURES);
		}
		initGui();
	}

	private void initGui() {
		for (GUIHomeField hf : homeFields) {
			this.add(hf);
			// watch the controller
			controller.addObserver(hf);
		}
		for (GUIFinishField ff : finishFields) {
			this.add(ff);
			// watch the controller
			controller.addObserver(ff);
		}
		this.add(publicField);
		controller.addObserver(publicField);
	}

}
