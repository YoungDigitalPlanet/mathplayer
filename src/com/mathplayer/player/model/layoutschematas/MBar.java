package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.signs.BarSign;
import com.mathplayer.player.model.tokens.MOperator;

public class MBar extends MOperator {

	protected BarSign vectorSign;
	
	public MBar(boolean showArrow) {
		super("_");
		vectorSign = new BarSign(showArrow);
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		vectorSign.setFont(font);
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size.clone();
		
		size = vectorSign.measure(socket) ;
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		
		Area vectorSignArea = new Area(area.x, area.y, area.width, area.height);
		vectorSign.render(canvas, vectorSignArea, socket);
	}
}
