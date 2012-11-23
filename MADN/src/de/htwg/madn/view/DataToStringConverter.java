package de.htwg.madn.view;

import java.util.List;
import java.util.Queue;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Figure;
import de.htwg.madn.model.FinishField;
import de.htwg.madn.model.GameSettings;
import de.htwg.madn.model.HomeField;
import de.htwg.madn.model.Player;
import de.htwg.madn.model.PublicField;

public final class DataToStringConverter {

	private final GameSettings settings;

	public DataToStringConverter(GameSettings settings) {
		this.settings = settings;
	}

	public String getWinners(Queue<Player> finishedPlayers) {
		StringBuilder sb = new StringBuilder();

		sb.append("Platzierung\tSpielername\n");
		for (int i = 0; i < finishedPlayers.size(); i++) {
			sb.append(i + 1).append("\t")
					.append(finishedPlayers.poll().getName()).append("\n");
		}

		return sb.toString();
	}

	public String getPlayerSetting(List<Player> players) {
		StringBuilder sb = new StringBuilder();

		for (Player p : players) {
			sb.append(p.getId()).append(": ").append(p.getName())
					.append(" hat Figuren: ");
			appendFiguresForPlayer(p, sb);
			sb.append("\n");
		}
		return sb.toString();
	}

	private void appendFiguresForPlayer(Player player, StringBuilder sb) {
		for (Figure fig : player.getFigures()) {
			sb.append(fig.getLetter()).append(" ");
		}
	}

	public String getBorder() {
		StringBuilder sb = new StringBuilder();

		appendCorner(sb);
		for (int i = 0; i < settings.getPublicFieldsCount(); i++) {
			sb.append('-');
		}
		appendCorner(sb);

		return sb.toString();
	}

	private void appendCorner(StringBuilder sb) {
		sb.append('+');
	}

	public String getHomeFields(List<HomeField> homeFields) {
		StringBuilder sb = new StringBuilder();

		appendVerticalBorder(sb);
		for (HomeField hf : homeFields) {
			appendHomeField(hf, sb);
			appendDistanceBetweenSpecialFields(sb);
		}
		appendVerticalBorder(sb);
		sb.append("\n");

		return sb.toString();
	}

	private void appendHomeField(HomeField homeField, StringBuilder sb) {
		int figuresPerPlayer = settings.getFiguresPerPlayer();

		for (int i = 0; i < figuresPerPlayer; i++) {
			Figure fig = homeField.getFigure(i);
			if (fig == null) {
				sb.append("O");
			} else {
				sb.append(fig.getLetter());
			}
		}
	}

	private void appendDistanceBetweenSpecialFields(StringBuilder sb) {
		int publicFieldsCount = settings.getPublicFieldsCount();
		int numberSpecialFields = settings.getMaxPlayers();
		int numberFigures = settings.getFiguresPerPlayer();

		int distance = publicFieldsCount / numberSpecialFields - numberFigures;

		for (int i = 0; i < distance; i++) {
			sb.append(' ');
		}
	}

	private void appendVerticalBorder(StringBuilder sb) {
		sb.append('|');
	}

	public String getPublicFields(PublicField publicField, Board board) {
		StringBuilder sb = new StringBuilder();
		int publicFieldsCount = settings.getPublicFieldsCount();

		appendVerticalBorder(sb);
		for (int i = 0; i < publicFieldsCount; i++) {
			Figure fig = publicField.getFigure(i);
			appendFigureLetterInPublicField(sb, i, fig, board);
		}
		appendVerticalBorder(sb);
		sb.append("\n");

		return sb.toString();
	}

	private void appendFigureLetterInPublicField(StringBuilder sb, int index,
			Figure fig, Board board) {
		if (fig != null) {
			sb.append(fig.getLetter());
		} else if (isSpecialPublicFieldHomeExit(index, board)) {
			sb.append("*");
		} else if (isSpecialPublicFieldFinishEntry(index, board)) {
			sb.append("/");
		} else {
			sb.append("_");
		}
	}

	private boolean isSpecialPublicFieldHomeExit(int i, Board board) {
		for (HomeField homeField : board.getHomeFields()) {
			if (homeField.getExitIndex() == i) {
				return true;
			}
		}

		return false;
	}

	private boolean isSpecialPublicFieldFinishEntry(int i, Board board) {
		for (FinishField finishField : board.getFinishFields()) {
			if (finishField.getEntryIndex() == i) {
				return true;
			}
		}

		return false;
	}

	public String getFinishFields(List<FinishField> finishFields) {
		StringBuilder sb = new StringBuilder();

		appendVerticalBorder(sb);
		for (FinishField ff : finishFields) {
			appendFinishFieldString(sb, ff);
			appendDistanceBetweenSpecialFields(sb);
		}
		appendVerticalBorder(sb);
		sb.append("\n");

		return sb.toString();
	}

	private void appendFinishFieldString(StringBuilder sb,
			FinishField finishField) {
		int figuresPerPlayer = settings.getFiguresPerPlayer();

		for (int i = 0; i < figuresPerPlayer; i++) {
			Figure fig = finishField.getFigure(i);
			if (fig == null) {
				sb.append("#");
			} else {
				sb.append(fig.getLetter());
			}
		}
	}
}
