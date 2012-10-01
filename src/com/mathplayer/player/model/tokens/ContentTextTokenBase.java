package com.mathplayer.player.model.tokens;

import eu.ydp.gwtutil.client.util.UserAgentChecker;
import gwt.g2d.client.graphics.Surface;

import com.google.gwt.user.client.ui.RootPanel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.ContentToken;
import com.mathplayer.player.style.Context;

public abstract class ContentTextTokenBase extends ContentToken {

	protected String content;
	protected Context styleContext;
	protected double MARGIN;

	public ContentTextTokenBase(String content) {
		this.content = content;
		MARGIN = 0;
	}

	public void setStyleContext(Context styleContext) {
		this.styleContext = styleContext;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
		if (styleContext != null && font != null) {
			styleContext.applyFontStyles(font);
		}
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null) {
			return size;
		}

		size = new Size();

		size.width = getTextWidth(content, font, MARGIN, RootPanel.get());
		size.height = font.size;
		size.middleLine = font.size / 2;

		return size.clone();
	}
	
	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.setFont(font.toString());
		canvas.setFillStyle(font.color);
		canvas.fillText(content, exactArea.x + font.size * MARGIN, exactArea.y + getFontTextOffset());
	}

	@Override
	public String toString() {
		return content;
	}

	@Override
	public abstract String toMathML();
}
