package de.htwg.madn.view.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;

@SuppressWarnings("serial")
public final class GUIView extends JFrame {

	private final IBoardControllerPort boardController;
	private GUIStatusPanel statusPanel;

	public GUIView(IBoardControllerPort boardController) {
		this.boardController = boardController;
		this.initGui();
	}

	private void initGui() {
		GUIControlPanel controlPanel = new GUIControlPanel(this);
		GUIGameFieldPanel gameFieldPanel = new GUIGameFieldPanel(this);
		this.statusPanel = new GUIStatusPanel(this);
		
		JPanel controlStatusPanel = new JPanel(new BorderLayout());
		
		controlStatusPanel.add(controlPanel, BorderLayout.NORTH);
		controlStatusPanel.add(statusPanel, BorderLayout.CENTER);
		controlStatusPanel.setBorder(GUIBorderFactory.createControlStatusBorder());
		
		// contentPane layout
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(controlStatusPanel, BorderLayout.NORTH);
		contentPane.add(gameFieldPanel, BorderLayout.CENTER);
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.setContentPane(contentPane);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public IBoardControllerPort getBoardControllerPort() {
		return this.boardController;
	}
	
	public GUIStatusPanel getStatusPanel() {
		return this.statusPanel;
	}
}