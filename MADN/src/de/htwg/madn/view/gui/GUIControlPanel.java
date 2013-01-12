package de.htwg.madn.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;
import de.htwg.madn.view.tui.DataToStringConverter;

@SuppressWarnings("serial")
public final class GUIControlPanel extends JPanel implements ActionListener,
		IObserver {

	private JPanel controlPanel;
	private JPanel gameFieldPanel;
	private DataToStringConverter stringifyer;
	private JTextField nameFld;
	private JTextField diceFld;
	private JButton addPlayerBtn;
	private JButton toggleGameBtn;
	private JButton diceBtn;
	private IBoardControllerPort controller;

	public GUIControlPanel(GUIView guiView) {
		this.controlPanel = guiView.getControlPanel();
		this.gameFieldPanel = guiView.getGameFieldPanel();
		this.stringifyer = guiView.getStringifyer();
		this.controller = guiView.getBoardControllerPort();
		// watch the controller with this class
		guiView.getBoardControllerPort().addObserver(this);
		initGui();
	}

	private void initGui() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
	}

	@Override
	public void update() {
		// update all fields..
		updateDiceField();
		updateToggleBtn();
	}

	private void updateToggleBtn() {
		if (!controller.gameIsRunning()) {
			
		}
	}

	private void updateDiceField() {
		// TODO Auto-generated method stub
		
	}

}
