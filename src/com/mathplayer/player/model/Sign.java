package com.mathplayer.player.model;

import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;

public abstract class Sign  {

	protected Font font;
	protected Size size;
	protected Area exactArea;

	public void setFont(Font font) {
		this.font = font;
	}
	
	public abstract Size measure(InteractionSocket socket);

	public void render(Surface canvas, Area area, InteractionSocket socket) {
		findExactArea(area);
	}

	public Area getExactArea() {
		return exactArea.clone();
	}

	private void findExactArea(Area area) {
		exactArea = new TokenExactAreaCalculator().calculate(area, size);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
