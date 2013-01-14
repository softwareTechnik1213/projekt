package de.htwg.madn.view.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.madn.controller.IBoardControllerPort;
import de.htwg.madn.util.observer.IObserver;

@SuppressWarnings("serial")
public final class GUIStatusPanel extends JPanel implements IObserver {

	private static final String LABEL_STATUS = "Status:";
	private static final int STATUS_COLUMNS = 40;
	private IBoardControllerPort controller;
	private JTextField statusFld;

	public GUIStatusPanel(GUIView guiView) {
		this.controller = guiView.getBoardControllerPort();
		// watch the controller with this class
		guiView.getBoardControllerPort().addObserver(this);
		initGui();
	}

	private void initGui() {
		JLabel statusLabel = new JLabel(LABEL_STATUS);
		statusFld = new JTextField(STATUS_COLUMNS);
		statusFld.setEditable(false);
		
		this.add(statusLabel);
		this.add(statusFld);
	}
	
	@Override
	public void update() {
		statusFld.setText(controller.getStatusString());
	}
	
	public void setStatus(String txt) {
		statusFld.setText(txt);
	}

}
