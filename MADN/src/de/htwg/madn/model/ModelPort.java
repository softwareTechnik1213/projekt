package de.htwg.madn.model;

import java.awt.Color;
import java.util.List;

public final class ModelPort implements IModelPort {
	
	private final IGameSettings settings;
	private final Board board;

	public ModelPort(IGameSettings settings, Board board) {
		this.settings = settings;
		this.board = board;
	}
	@Override
	public void reset() {
		board.reset();
	}

	@Override
	public IGameSettings getSettings() {
		return settings;
	}

	@Override
	public Dice getDice() {
		return board.getDice();
	}

	@Override
	public Player addPlayer(Color col, String name, boolean isHuman) {
		return board.addPlayer(col, name, isHuman);
	}

	@Override
	public List<Player> getPlayers() {
		return board.getPlayers();
	}

	@Override
	public PublicField getPublicField() {
		return board.getPublicField();
	}

	@Override
	public Figure getFigureForPlayerByLetter(Player player, char figureLetter) {
		return board.getFigureForPlayerByLetter(player, figureLetter);
	}

	@Override
	public List<HomeField> getHomeFields() {
		return board.getHomeFields();
	}

	@Override
	public List<FinishField> getFinishFields() {
		return board.getFinishFields();
	}

	



}
