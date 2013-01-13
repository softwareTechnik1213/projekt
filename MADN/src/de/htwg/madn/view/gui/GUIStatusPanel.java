package de.htwg.madn.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;
import de.htwg.madn.view.tui.DataToStringConverter;

@SuppressWarnings("serial")
public final class GUIStatusPanel extends JPanel implements ActionListener,
		IObserver {

	private static final String LABEL_STATUS = "Status:";
	private GUIControlPanel controlPanel;
	private GUIGameFieldPanel gameFieldPanel;
	private DataToStringConverter stringifyer;
	private IBoardControllerPort controller;
	private JTextField statusFld;
	private JLabel statusLabel;

	public GUIStatusPanel(GUIView guiView) {
		this.controlPanel = guiView.getControlPanel();
		this.gameFieldPanel = guiView.getGameFieldPanel();
		this.stringifyer = guiView.getStringifyer();
		this.controller = guiView.getBoardControllerPort();
		// watch the controller with this class
		guiView.getBoardControllerPort().addObserver(this);
		initGui();
	}

	private void initGui() {
		statusLabel = new JLabel(LABEL_STATUS);
		statusFld = new JTextField();
		
		this.add(statusLabel);
		this.add(statusFld);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	@Override
	public void update() {
		// update all fields..
	}
	
	public void setStatus(String txt) {
		
	}

}
