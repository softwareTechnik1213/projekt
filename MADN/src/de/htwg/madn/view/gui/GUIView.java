package de.htwg.madn.view.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
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
		contentPane.add(statusPanel, BorderLayout.CENTER);
		contentPane.add(gameFieldPanel, BorderLayout.SOUTH);
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.setContentPane(contentPane);
		this.pack();
		this.setResizable(false);
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