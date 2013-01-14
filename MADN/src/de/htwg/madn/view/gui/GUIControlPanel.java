package de.htwg.madn.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;

@SuppressWarnings("serial")
public final class GUIControlPanel extends JPanel implements ActionListener,
		IObserver {

	private static final String BTN_START = "Start";
	private static final String BTN_NEW = "Neues Spiel";
	private static final String ERR_NAME_EMPTY = "Bitte einen Namen eingeben!";
	private static final String ERR_GAME_RUNNING = "Das Spiel laeuft bereits!";
	private static final int NAME_COLUMNS = 10;
	private static final String BTN_ADD_PLAYER = "Spieler hinzufuegen";
	private static final String BTN_DICE = "Wuerfeln!";
	private GUIStatusPanel statusPanel;
	private JTextField nameFld;
	private JButton addPlayerBtn;
	private JButton toggleGameBtn;
	private JButton diceBtn;
	private IBoardControllerPort controller;
	private Deque<Color> colorSet;
	private static final Color[] initColors = { Color.RED, Color.BLUE, Color.GREEN,
			Color.YELLOW };

	public GUIControlPanel(GUIView guiView) {
		this.statusPanel = guiView.getStatusPanel();
		this.controller = guiView.getBoardControllerPort();
		this.colorSet = new LinkedList<Color>();
		// add all colors to the list
		for (Color col : GUIControlPanel.initColors) {
			colorSet.push(col);
		}
		
		// watch the controller with this class
		guiView.getBoardControllerPort().addObserver(this);
		initGui();
	}

	private void initGui() {
		nameFld = new JTextField(NAME_COLUMNS);
		addPlayerBtn = new JButton(BTN_ADD_PLAYER);
		toggleGameBtn = new JButton(BTN_START);
		diceBtn = new JButton(BTN_DICE);
		
		nameFld.addActionListener(this);
		addPlayerBtn.addActionListener(this);
		toggleGameBtn.addActionListener(this);
		diceBtn.addActionListener(this);
		
		this.add(nameFld);
		this.add(addPlayerBtn);
		this.add(toggleGameBtn);
		this.add(diceBtn);
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
		if (controller.gameIsRunning()) {
			statusPanel.setStatus(ERR_GAME_RUNNING);
			nameFld.setText(null);
			return;
		}

		String name = nameFld.getText();
		if (name != null && !name.isEmpty()) {
			controller.addPlayer(name, getNextFreeColor(), true);
			nameFld.setText(null);
		} else {
			statusPanel.setStatus(ERR_NAME_EMPTY);
		}
	}

	private Color getNextFreeColor() {
		if (colorSet.isEmpty()) {
			return null;
		}
		return colorSet.pop();
	}

	@Override
	public void update() {
		// update all fields..
		if (!controller.gameIsRunning()) {
			toggleGameBtn.setText(BTN_START);
		} else {
			toggleGameBtn.setText(BTN_NEW);
		}
	}

}
