package de.htwg.madn.model;


public final class GameSettings {
	public final int minPlayers;
	public final int maxPlayers;
	public final int figuresPerPlayer;
	public final int publicFieldsCount;
	public final int diceMin;
	public final int diceMax;
	public final int minNumberToExitHome;
	public final int throwsAllowedInHome;
	public final int throwsAllowedInPublic;
	
	public GameSettings(int minPlayers, int maxPlayers, int figuresPerPlayer,
			int publicFieldsCount, int diceMin, int diceMax, int minNumberToExitHome,
			int throwsAllowedInHome, int throwsAllowedInPublic) {

		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.figuresPerPlayer = figuresPerPlayer;
		this.publicFieldsCount = publicFieldsCount;
		this.diceMin = diceMin;
		this.diceMax = diceMax;
		this.minNumberToExitHome = minNumberToExitHome;
		this.throwsAllowedInHome = throwsAllowedInHome;
		this.throwsAllowedInPublic = throwsAllowedInPublic;
		
		verifySettings();
	}

	private void verifySettings() {
		if (minPlayers > maxPlayers
				|| minPlayers < 1
				|| maxPlayers < 1
				|| figuresPerPlayer < 1
				|| publicFieldsCount < 1
				|| throwsAllowedInHome < 1
				|| throwsAllowedInPublic < 1
				|| publicFieldsCount % maxPlayers != 0) {
			throw new IllegalArgumentException("settings not valid");
		}
	}
		
	
}
