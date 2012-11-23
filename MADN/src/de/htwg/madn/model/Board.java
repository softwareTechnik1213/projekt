package de.htwg.madn.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class Board {
	private final List<HomeField> homeFields;
	private final List<FinishField> finishFields;
	private final List<Player> players;
	private final PublicField publicField;
	private final int maxPlayers;
	private final int figuresPerPlayer;
	private final int publicFieldsCount;
	private final Dice dice;

	public Board(GameSettings gameSettings) {

		this.maxPlayers = gameSettings.getMaxPlayers();
		this.figuresPerPlayer = gameSettings.getFiguresPerPlayer();
		this.publicFieldsCount = gameSettings.getPublicFieldsCount();
		int diceMin = gameSettings.getDiceMin();
		int diceMax = gameSettings.getDiceMax();

		this.dice = new Dice(diceMin, diceMax);
		homeFields = new LinkedList<HomeField>();
		finishFields = new LinkedList<FinishField>();

		for (int i = 0; i < this.maxPlayers; i++) {
			homeFields.add(new HomeField(getExitIndexHome(i), this.figuresPerPlayer));
			finishFields.add(new FinishField(getEntryIndexFinish(i),
					this.figuresPerPlayer));
		}

		players = new LinkedList<Player>();
		publicField = new PublicField(publicFieldsCount);
	}

	public int getExitIndexHome(final int playerNumber) {
		return playerNumber * publicFieldsCount / maxPlayers;
	}

	public int getEntryIndexFinish(final int playerNumber) {
		return (getExitIndexHome(playerNumber) + publicFieldsCount - 1)
				% publicFieldsCount;
	}

	public Player addPlayer(final Color color, final String name) {
		if (players.size() >= maxPlayers) {
			return null;
		}

		int playerId = players.size();

		Player newPlayer = new Player(playerId, color, name,
				homeFields.get(playerId), finishFields.get(playerId),
				figuresPerPlayer);
		players.add(newPlayer);
		return newPlayer;
	}

	public List<HomeField> getHomeFields() {
		return homeFields;
	}

	public List<FinishField> getFinishFields() {
		return finishFields;
	}

	public PublicField getPublicField() {
		return publicField;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Dice getDice() {
		return this.dice;
	}
	
	public Figure getFigureForPlayerByLetter(Player player, char figureLetter) {
		if (player == null || !Character.isLetter(figureLetter)) {
			throw new IllegalArgumentException("player or char is null");
		}
		for (Figure figure : player.getFigures()) {
			if (figure.getLetter() == figureLetter) {
				return figure;
			}
		}
		return null;
	}
}
