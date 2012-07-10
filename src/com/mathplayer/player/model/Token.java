package com.mathplayer.player.model;

import com.google.gwt.user.client.ui.RootPanel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.utils.BrowserUtils;

import eu.ydp.gwtutil.client.util.UserAgentChecker;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent.*;

import gwt.g2d.client.graphics.Surface;

public abstract class Token {

	protected Font font;
	protected Size size;
	protected Area exactArea;

	protected static double TEXT_OFFSET = BrowserUtils.getUserAgent().toLowerCase().contains("msie") ? 0.91d : 0.72d;
	static {
		if (UserAgentChecker.isMobileUserAgent(ANDROID23, ANDROID3, ANDROID321, ANDROID4)) {
			TEXT_OFFSET = 0.81d;
		}
	}

	public void setFont(Font font) {
		
		
		this.font = font;
	}

	public void render(Surface canvas, Area area, InteractionSocket socket) {
		findExactArea(area);
	}

	public abstract Size measure(InteractionSocket socket);

	public abstract String toString();

	public abstract String toMathML();

	public Surface createCanvas() {
		Surface canvas = new Surface();
		RootPanel.get().add(canvas);
		return canvas;
	}

	public void removeCanvas(Surface canvas) {
		RootPanel.get().remove(canvas);
	}

	public Area getExactArea() {
		return exactArea.clone();
	}

	protected void findExactArea(Area area) {
		double heightSurplus = area.middleLine - size.middleLine;
		if (area.y + heightSurplus < 0)
			heightSurplus = -area.y;
		exactArea = new Area(area.x + (area.width - size.width) / 2, area.y + heightSurplus, size);
	}

	public double getTextOffset() {
		return font.size * TEXT_OFFSET;
	}

	public double getTextOffset(int fontSize) {
		return fontSize * TEXT_OFFSET;
	}

	public void reset() {
		size = null;
	}

}
