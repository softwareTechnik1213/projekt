package de.htwg.madn.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class Board {
	private final List<HomeField> homeFields;
	private final List<FinishField> finishFields;
	private final List<Player> players;
	private final PublicField publicField;
	public final int minPlayers;
	public final int maxPlayers;
	public final int figuresPerPlayer;
	public final int publicFieldsCount;
	public final int diceMin;
	public final int diceMax;
	private final Dice dice;

	public Board(final int minPlayers, final int maxPlayers,
			final int figuresPerPlayer, final int publicFieldsCount,
			final int diceMin, final int diceMax) {

		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.figuresPerPlayer = figuresPerPlayer;
		this.publicFieldsCount = publicFieldsCount;
		this.diceMin = diceMin;
		this.diceMax = diceMax;

		this.dice = new Dice(this.diceMin, this.diceMax);
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
		for (Figure figure : player.getFigures()) {
			if (figure.getLetter() == figureLetter) {
				return figure;
			}
		}
		return null;
	}
}
