package com.mathplayer.player.model;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.utils.FontAnatomy;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public abstract class Token {

	protected Font font;
	protected Size size;
	protected Area exactArea;

	public void setFont(Font font) {
		this.font = font;
	}

	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		findExactArea(area);
	}

	public abstract Size measure(InteractionSocket socket);

	@Override
	public abstract String toString();

	public abstract String toMathML();

	public Canvas getCanvas() {
		return Canvas.createIfSupported();
	}

	public Canvas createCanvas(AbsolutePanel panel) {
		Canvas canvas = getCanvas();
		panel.add(canvas);
		return canvas;
	}

	public void removeCanvas(Canvas canvas, AbsolutePanel panel) {
		panel.remove(canvas);
	}

	public double getTextWidth(String content, Font font, double margin, AbsolutePanel panel) {
		Canvas canvas = createCanvas(panel);
		canvas.getContext2d().setFont(font.toString());
		double textWidth = canvas.getContext2d().measureText(content).getWidth() + font.size * margin * 2;
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
