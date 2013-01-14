package de.htwg.madn.view.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public final class GUIBorderFactory {

	private static final int SPECIAL_WIDTH = 5;
	private static final int PUBLIC_WIDTH = 10;

	public static Border createSpecialBorder() {
		CompoundBorder innerBorder = new CompoundBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				BorderFactory.createEmptyBorder(SPECIAL_WIDTH,
						SPECIAL_WIDTH, SPECIAL_WIDTH,
						SPECIAL_WIDTH));
		CompoundBorder border = new CompoundBorder(
				BorderFactory.createEmptyBorder(SPECIAL_WIDTH,
						SPECIAL_WIDTH, SPECIAL_WIDTH,
						SPECIAL_WIDTH), innerBorder);

		return border;
	}

	public static Border createPublicBorder() {
		return BorderFactory.createEmptyBorder(PUBLIC_WIDTH,
				PUBLIC_WIDTH, PUBLIC_WIDTH, PUBLIC_WIDTH);
	}

}
