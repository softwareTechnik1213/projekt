package de.htwg.madn.model;


public final class GameSettings {
	private final int minPlayers;
	private final int maxPlayers;
	private final int figuresPerPlayer;
	private final int publicFieldsCount;
	private final int diceMin;
	private final int diceMax;
	private final int minNumberToExitHome;
	private final int throwsAllowedInHome;
	private final int throwsAllowedInPublic;
	
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

	public final int getMinPlayers() {
		return minPlayers;
	}

	public final int getMaxPlayers() {
		return maxPlayers;
	}

	public final int getFiguresPerPlayer() {
		return figuresPerPlayer;
	}

	public final int getPublicFieldsCount() {
		return publicFieldsCount;
	}

	public final int getDiceMin() {
		return diceMin;
	}

	public final int getDiceMax() {
		return diceMax;
	}

	public final int getMinNumberToExitHome() {
		return minNumberToExitHome;
	}

	public final int getThrowsAllowedInHome() {
		return throwsAllowedInHome;
	}

	public final int getThrowsAllowedInPublic() {
		return throwsAllowedInPublic;
	}
		
	
}
