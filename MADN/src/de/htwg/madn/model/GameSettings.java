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
			int publicFieldsCount, int diceMin, int diceMax,
			int minNumberToExitHome, int throwsAllowedInHome,
			int throwsAllowedInPublic) {

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
		if (minPlayers > maxPlayers || minPlayers < 1 || maxPlayers < 1) {
			throw new IllegalArgumentException("player settings not valid");
		}

		if (figuresPerPlayer < 1) {
			throw new IllegalArgumentException("figures settings not valid");
		}

		if (publicFieldsCount < 1
			|| publicFieldsCount % maxPlayers != 0) {
			throw new IllegalArgumentException("public fields settings not valid");
		}

		if (throwsAllowedInHome < 1 || throwsAllowedInPublic < 1) {
			throw new IllegalArgumentException(
					"throws allowed settings not valid");
		}
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getFiguresPerPlayer() {
		return figuresPerPlayer;
	}

	public int getPublicFieldsCount() {
		return publicFieldsCount;
	}

	public int getDiceMin() {
		return diceMin;
	}

	public int getDiceMax() {
		return diceMax;
	}

	public int getMinNumberToExitHome() {
		return minNumberToExitHome;
	}

	public int getThrowsAllowedInHome() {
		return throwsAllowedInHome;
	}

	public int getThrowsAllowedInPublic() {
		return throwsAllowedInPublic;
	}

}
