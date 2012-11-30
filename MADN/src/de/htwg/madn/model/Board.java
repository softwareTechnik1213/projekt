package de.htwg.madn.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;


public final class Board implements IBoard {
	private List<HomeField> homeFields;
	private List<FinishField> finishFields;
	private List<Player> players;
	private PublicField publicField;
	private final int maxPlayers;
	private final int figuresPerPlayer;
	private final int publicFieldsCount;
	private int diceMin;
	private int diceMax;
	private Dice dice;

	public Board(IGameSettings gameSettings) {

		this.maxPlayers = gameSettings.getMaxPlayers();
		this.figuresPerPlayer = gameSettings.getFiguresPerPlayer();
		this.publicFieldsCount = gameSettings.getPublicFieldsCount();
		this.diceMin = gameSettings.getDiceMin();
		this.diceMax = gameSettings.getDiceMax();

		init();
	}
	
	private void init() {
		this.dice = new Dice(diceMin, diceMax);
		this.homeFields = new LinkedList<HomeField>();
		this.finishFields = new LinkedList<FinishField>();

		initHomeAndFinishFields();

		this.players = new LinkedList<Player>();
		this.publicField = new PublicField(publicFieldsCount);
	}
	
	private void initHomeAndFinishFields() {
		for (int i = 0; i < this.maxPlayers; i++) {
			homeFields.add(new HomeField(getExitIndexHome(i), this.figuresPerPlayer));
			finishFields.add(new FinishField(getEntryIndexFinish(i),
					this.figuresPerPlayer));
		}
	}
	
	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#reset()
	 */
	@Override
	public void reset() {
		init();
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getExitIndexHome(int)
	 */
	@Override
	public int getExitIndexHome(final int playerNumber) {
		return playerNumber * publicFieldsCount / maxPlayers;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getEntryIndexFinish(int)
	 */
	@Override
	public int getEntryIndexFinish(final int playerNumber) {
		return (getExitIndexHome(playerNumber) + publicFieldsCount - 1)
				% publicFieldsCount;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#addPlayer(java.awt.Color, java.lang.String, boolean)
	 */
	@Override
	public Player addPlayer(final Color color, final String name, boolean isHuman) {
		if (players.size() >= maxPlayers) {
			return null;
		}

		int playerId = players.size();

		Player newPlayer = new Player(playerId, color, name,
				homeFields.get(playerId), finishFields.get(playerId),
				figuresPerPlayer, isHuman);
		players.add(newPlayer);
		return newPlayer;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getHomeFields()
	 */
	@Override
	public List<HomeField> getHomeFields() {
		return homeFields;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getFinishFields()
	 */
	@Override
	public List<FinishField> getFinishFields() {
		return finishFields;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getPublicField()
	 */
	@Override
	public PublicField getPublicField() {
		return publicField;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getPlayers()
	 */
	@Override
	public List<Player> getPlayers() {
		return players;
	}

	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getDice()
	 */
	@Override
	public Dice getDice() {
		return this.dice;
	}
	
	/* (non-Javadoc)
	 * @see de.htwg.madn.model.IBoard#getFigureForPlayerByLetter(de.htwg.madn.model.Player, char)
	 */
	@Override
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
