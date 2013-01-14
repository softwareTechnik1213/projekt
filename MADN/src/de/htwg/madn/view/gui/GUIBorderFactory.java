package de.htwg.madn.view.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public final class GUIBorderFactory {

	private static final int SPECIAL_WIDTH = 5;
	private static final int PUBLIC_WIDTH = 7;


	public static Border createSpecialBorder() {
		return new CompoundBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				BorderFactory.createEmptyBorder(SPECIAL_WIDTH,
						SPECIAL_WIDTH, SPECIAL_WIDTH,
						SPECIAL_WIDTH));
	}

	public static Border createPublicBorder() {
		return BorderFactory.createEmptyBorder(PUBLIC_WIDTH,
				PUBLIC_WIDTH, PUBLIC_WIDTH, PUBLIC_WIDTH);
	}

	public static Border createControlStatusBorder() {
		return new CompoundBorder(
				BorderFactory.createEmptyBorder(0, 0, 20, 0),
				BorderFactory.createRaisedBevelBorder());
	}

}
