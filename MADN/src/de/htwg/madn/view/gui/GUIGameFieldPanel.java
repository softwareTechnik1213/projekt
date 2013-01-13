package de.htwg.madn.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;
import de.htwg.madn.view.tui.DataToStringConverter;

@SuppressWarnings("serial")
public final class GUIGameFieldPanel extends JPanel implements ActionListener,
		IObserver {

	private GUIControlPanel controlPanel;
	private GUIStatusPanel statusPanel;
	private DataToStringConverter stringifyer;
	private IBoardControllerPort controller;

	public GUIGameFieldPanel(GUIView guiView) {
		this.controlPanel = guiView.getControlPanel();
		this.statusPanel = guiView.getStatusPanel();
		this.stringifyer = guiView.getStringifyer();
		this.controller = guiView.getBoardControllerPort();
		// watch the controller with this class
		guiView.getBoardControllerPort().addObserver(this);
		initGui();
	}

	private void initGui() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	@Override
	public void update() {
		// update all fields..
	}

}
