package de.htwg.madn.view.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.view.tui.DataToStringConverter;

@SuppressWarnings("serial")
public final class GUIView extends JFrame {

	private final IBoardControllerPort boardController;
	private final DataToStringConverter stringifyer;
	private static final int VALID_PUBLIC_COUNT = 40;
	private static final int VALID_MAX_PLAYERS = 4;
	private static final int VALID_FIGURES = 4;
	private JPanel controlPanel;
	private JPanel gameFieldPanel;

	public GUIView(IBoardControllerPort boardController) {
		if (!validSettings(boardController.getSettings())) {
			throw new IllegalArgumentException(
					"These Settings are not allowed!");
		}
		this.boardController = boardController;
		this.stringifyer = new DataToStringConverter(
				boardController.getSettings());
		this.initGui();
	}

	private void initGui() {
		this.controlPanel = new GUIControlPanel(this);
		this.gameFieldPanel = new GUIGameFieldPanel(this);
		
		// contentPane layout
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(controlPanel, BorderLayout.NORTH);
		contentPane.add(gameFieldPanel, BorderLayout.CENTER);
		
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
	
	public JPanel getControlPanel() {
		return this.controlPanel;
	}
	
	public JPanel getGameFieldPanel() {
		return this.gameFieldPanel;
	}
	
	public DataToStringConverter getStringifyer() {
		return this.stringifyer;
	}
}