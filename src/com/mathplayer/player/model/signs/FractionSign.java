package com.mathplayer.player.model.signs;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class FractionSign extends Sign {

	public final int SPACE_FONT_COEFF = 20; 
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;
		size = new Size(0,3*(font.size/SPACE_FONT_COEFF + 1));
		return size;
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.setFillStyle(new Color(0, 0,0));
		canvas.fillRectangle(area.x, area.y + font.size/SPACE_FONT_COEFF+1, area.width, font.size/SPACE_FONT_COEFF+1);
	}

}
