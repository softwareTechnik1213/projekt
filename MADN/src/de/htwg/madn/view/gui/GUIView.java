package de.htwg.madn.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.IGameSettings;

@SuppressWarnings("serial")
public final class GUIView extends JFrame {

	private final IBoardControllerPort boardController;
	private static final int VALID_PUBLIC_COUNT = 40;
	private static final int VALID_MAX_PLAYERS = 4;
	private static final int VALID_FIGURES = 4;
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 1000;
	private GUIControlPanel controlPanel;
	private GUIGameFieldPanel gameFieldPanel;
	private GUIStatusPanel statusPanel;

	public GUIView(IBoardControllerPort boardController) {
		if (!validSettings(boardController.getSettings())) {
			throw new IllegalArgumentException(
					"These Settings are not allowed!");
		}
		this.boardController = boardController;
		this.initGui();
	}

	private void initGui() {
		this.controlPanel = new GUIControlPanel(this);
		this.gameFieldPanel = new GUIGameFieldPanel(this);
		this.statusPanel = new GUIStatusPanel(this);
		
		// contentPane layout
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(controlPanel, BorderLayout.NORTH);
		contentPane.add(gameFieldPanel, BorderLayout.CENTER);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setContentPane(contentPane);
		this.pack();
		this.setVisible(true);
	}

	private boolean validSettings(IGameSettings settings) {
		if (settings.getPublicFieldsCount() != VALID_PUBLIC_COUNT
				|| settings.getMaxPlayers() != VALID_MAX_PLAYERS
				|| settings.getFiguresPerPlayer() != VALID_FIGURES) {
			return false;
		}
		return true;
	}
	
	public IBoardControllerPort getBoardControllerPort() {
		return this.boardController;
	}
	
	public GUIStatusPanel getStatusPanel() {
		return this.statusPanel;
	}
}