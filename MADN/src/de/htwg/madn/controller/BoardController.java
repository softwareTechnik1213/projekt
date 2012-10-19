package de.htwg.madn.controller;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import de.htwg.madn.model.Field;
import de.htwg.madn.model.FinishContainer;
import de.htwg.madn.model.HomeContainer;
import de.htwg.madn.model.Player;
import de.htwg.madn.model.RestrictedFieldContainer;

public final class BoardController {

	private static int countPlayer;
	private int countPublicFields;
	private List<Player> playersList = new LinkedList<Player>();
	private List<RestrictedFieldContainer> homeContainerList = new LinkedList<RestrictedFieldContainer>();
	private List<RestrictedFieldContainer> finishContainerList = new LinkedList<RestrictedFieldContainer>();
	private List<Field> publicFieldsList = new LinkedList<Field>();

	public BoardController() {
		init();
	}

	private int init() {

		playersList.add(new Player(1, Color.red, "Player1"));
		playersList.add(new Player(2, Color.yellow, "Player2"));
		playersList.add(new Player(3, Color.green, "Player3"));
		playersList.add(new Player(4, Color.blue, "Player4"));
		countPlayer = 4;
		countPublicFields = 28;

		// set up home and finish fields
		for (int i = 0; i < countPlayer; i++) {
			homeContainerList.add(new HomeContainer(playersList.get(i)));
			finishContainerList.add(new FinishContainer(playersList.get(i)));
			for (int j = 0; j < RestrictedFieldContainer.MAX_FIELDS; j++) {
				homeContainerList.get(i).getFieldsList().add(new Field());
				finishContainerList.get(i).getFieldsList().add(new Field());
			}
		}

		// set up public fields
		for (int i = 0; i < countPublicFields; i++) {
			publicFieldsList.add(new Field());
		}
		
//		initTUI();

		return 0;

	}
	
	
}
