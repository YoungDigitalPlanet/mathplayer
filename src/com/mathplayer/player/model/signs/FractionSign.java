package com.mathplayer.player.model.signs;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class FractionSign extends Sign {

	public final int SPACE_FONT_COEFF = 20;
	public final int SPACE_FONT_COEFF2 = 2; 
	public final int LINE_POSITION_COEFF = 3; 
	
	@Override
	public Size measure(InteractionSocket socket) {
		size = new Size(0,getSpaceHeight());
		return size;
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.setFillStyle(new Color(0, 0,0));
		canvas.fillRectangle(area.x, (int)area.y + getSpaceHeight()/LINE_POSITION_COEFF, area.width, getSignHeight());
	}
	
	private int getSignHeight(){
		return font.size/SPACE_FONT_COEFF + 1;
	}
	
	private int getSpaceHeight(){
		return font.size/SPACE_FONT_COEFF2 + getSignHeight();
	}

}
