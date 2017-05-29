package com.mathplayer.player.model;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;

public class TokenExactAreaCalculator {

	public Area calculate(Area area, Size size) {
		double heightSurplus = area.middleLine - size.middleLine;
		if (area.y + heightSurplus < 0) {
			heightSurplus = -area.y;
		}
		return new Area(area.x + (area.width - size.width) / 2, area.y + heightSurplus, size);
	}
}
