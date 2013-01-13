package de.htwg.madn.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.model.IModelPort;
import de.htwg.madn.util.observer.IObserver;
import de.htwg.madn.view.tui.DataToStringConverter;

@SuppressWarnings("serial")
public final class GUIControlPanel extends JPanel implements ActionListener,
		IObserver {

	private static final String BTN_START = "Start";
	private static final String BTN_NEW = "Neues Spiel";
	private static final String ERR_NAME_EMPTY = "Bitte einen Namen eingeben!";
	private GUIGameFieldPanel gameFieldPanel;
	private GUIStatusPanel statusPanel;
	private DataToStringConverter stringifyer;
	private JTextField nameFld;
	private JTextField diceFld;
	private JButton addPlayerBtn;
	private JButton toggleGameBtn;
	private JButton diceBtn;
	private IBoardControllerPort controller;
	private IModelPort model;

	public GUIControlPanel(GUIView guiView) {
		this.gameFieldPanel = guiView.getGameFieldPanel();
		this.statusPanel = guiView.getStatusPanel();
		this.stringifyer = guiView.getStringifyer();
		this.controller = guiView.getBoardControllerPort();
		this.model = controller.getModelPort();
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
		
		if (src == diceBtn) {
			controller.throwDice();
		} else if (src == addPlayerBtn) {
			addPlayer();
		} else if (src == toggleGameBtn) {
			toggleGameState();
		}
	}

	private void toggleGameState() {
		if (!controller.gameIsRunning()) {
			controller.startGame();
		} else {
			controller.reset();
		}
	}

	private void addPlayer() {
		String name = nameFld.getText();
		if (name != null && !name.isEmpty()) {
			controller.addPlayer(name, getNextFreeColor(), true);
		} else {
			statusPanel.setStatus(ERR_NAME_EMPTY);
		}
	}

	private Color getNextFreeColor() {

		return null;
	}

	@Override
	public void update() {
		// update all fields..
		updateDiceField();
		updateToggleBtn();
	}

	private void updateToggleBtn() {
		if (!controller.gameIsRunning()) {
			toggleGameBtn.setText(BTN_START);
		} else {
			toggleGameBtn.setText(BTN_NEW);
		}
	}

	private void updateDiceField() {
		if (model.getDice().getThrowsCount() > 0) {
			diceFld.setText(String.valueOf(model.getDice().getLastNumber()));
		} else {
			diceFld.setText(null);
		}
	}

}
