package com.mathplayer.player.model.tokens;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.ContentToken;
import com.mathplayer.player.style.StyleContext;

public abstract class ContentTextTokenBase extends ContentToken {

	protected String content;
	protected StyleContext styleContext;
	protected double MARGIN;
	
	public ContentTextTokenBase(String content){
		this.content = content;
		MARGIN = 0;
	}

	public void setStyleContext(StyleContext styleContext){
		this.styleContext = styleContext;
	}
	
	@Override
	public void setFont(Font font) {
		this.font = font;
		if (styleContext != null  &&  font != null)
			styleContext.applyFontStyles(font);
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		
		if (size != null)
			return size;
		
		Surface canvas = createCanvas();
		
		canvas.setFont(font.toString());
		
		size = new Size();
		
		size.width = canvas.measureText(content) + font.size*MARGIN*2;
		size.height = font.size;
		size.middleLine = font.size/2;
		
		removeCanvas(canvas);
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.setFont(font.toString());
		canvas.setFillStyle(new Color(0, 0,0));
		canvas.fillText(content, exactArea.x + font.size*MARGIN, exactArea.y + getTextOffset() );
	}

	@Override
	public String toString() {
		return content;
	}

	public abstract String toMathML();
}
