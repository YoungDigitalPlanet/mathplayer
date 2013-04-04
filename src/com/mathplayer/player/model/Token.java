package com.mathplayer.player.model;

import eu.ydp.gwtutil.client.util.UserAgentChecker;
import gwt.g2d.client.graphics.Surface;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.utils.FontAnatomy;

public abstract class Token {

	protected Font font;
	protected Size size;
	protected Area exactArea;

	public void setFont(Font font) {
		this.font = font;
	}

	public void render(Surface canvas, Area area, InteractionSocket socket) {
		findExactArea(area);
	}

	public abstract Size measure(InteractionSocket socket);

	@Override
	public abstract String toString();

	public abstract String toMathML();
	
	public Surface getCanvas() {
		return new Surface();
	}

	public Surface createCanvas(AbsolutePanel panel) {
		Surface canvas = getCanvas();
		panel.add(canvas);
		return canvas;
	}

	public void removeCanvas(Surface canvas, AbsolutePanel panel) {
		panel.remove(canvas);
	}
	
	public double getTextWidth(String content, Font font, double margin, AbsolutePanel panel) {
		Surface canvas = createCanvas(panel);
		canvas.setFont(font.toString());
		double textWidth = canvas.measureText(content) + font.size * margin * 2;
		removeCanvas(canvas, panel);
		return textWidth;
	}

	public Area getExactArea() {
		return exactArea.clone();
	}

	protected void findExactArea(Area area) {
		exactArea = new TokenExactAreaCalculator().calculate(area, size);
	}

	public double getFontTextOffset() {
		return font.size * getTextOffset();
	}

	public double getFontTextOffset(int fontSize) {
		return fontSize * getTextOffset();
	}
	
	protected double getTextOffset() {
		return FontAnatomy.TEXT_OFFSET;
	}
	
	public Boolean isStackAndroidBrowser() {
		return UserAgentChecker.isStackAndroidBrowser();
	}
	
	public void reset() {
		size = null;
	}
}
