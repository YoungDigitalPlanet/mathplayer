package com.mathplayer.player.model.tokens;

import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;

public class MSpace extends ContentTextTokenBase {

	protected double width;
	
	public MSpace(double width){
		super("M");
		this.width = width;
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size == null){
			super.measure(socket);
			size.width *= width;
		}
		return size;
	}
	

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
	}


	@Override
	public String toString() {
		return " ";
	}

	@Override
	public String toMathML() {
		return "<mspace width=" + width + "em/>";
	}

}
