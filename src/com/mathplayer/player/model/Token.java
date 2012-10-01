package com.mathplayer.player.model;

import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.utils.BrowserUtils;

import eu.ydp.gwtutil.client.util.UserAgentChecker;
import gwt.g2d.client.graphics.Surface;

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
		
		//android zle mierzy italica na canvasie
		if (isStackAndroidBrowser() && font.italic) {
			size.width += size.width * .1;
		}
		
		return textWidth;
	}

	public Area getExactArea() {
		return exactArea.clone();
	}

	protected void findExactArea(Area area) {
		double heightSurplus = area.middleLine - size.middleLine;
		if (area.y + heightSurplus < 0) {
			heightSurplus = -area.y;
		}
		exactArea = new Area(area.x + (area.width - size.width) / 2, area.y + heightSurplus, size);
	}

	public double getFontTextOffset() {
		return font.size * getTextOffset();
	}

	public double getFontTextOffset(int fontSize) {
		return fontSize * getTextOffset();
	}
	
	protected double getTextOffset() {
		double textOffset = BrowserUtils.getUserAgent().toLowerCase().contains("msie") ? 0.91d : 0.725d;
		
		if (isStackAndroidBrowser()) {
			textOffset = 0.81d;
		}
		
		return textOffset;
	}
	
	public Boolean isStackAndroidBrowser() {
		return UserAgentChecker.isStackAndroidBrowser();
	}
	
	public void reset() {
		size = null;
	}
}
