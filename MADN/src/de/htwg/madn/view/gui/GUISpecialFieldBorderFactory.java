package de.htwg.madn.view.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public final class GUISpecialFieldBorderFactory {

	private static final int specialBorderWidth = 5;
	private static final int publicBorderWidth = 10;

	public static Border createSpecialBorder() {
		CompoundBorder innerBorder = new CompoundBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				BorderFactory.createEmptyBorder(specialBorderWidth,
						specialBorderWidth, specialBorderWidth,
						specialBorderWidth));
		CompoundBorder border = new CompoundBorder(
				BorderFactory.createEmptyBorder(specialBorderWidth,
						specialBorderWidth, specialBorderWidth,
						specialBorderWidth), innerBorder);

		return border;
	}

	public static Border createPublicBorder() {
		return BorderFactory.createEmptyBorder(publicBorderWidth,
				publicBorderWidth, publicBorderWidth, publicBorderWidth);
	}

}
