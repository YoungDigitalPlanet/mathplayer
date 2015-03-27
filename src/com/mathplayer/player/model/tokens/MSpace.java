package com.mathplayer.player.model.tokens;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;

public class MSpace extends ContentTextTokenBase {

	protected double width;

	public MSpace(double width) {
		super("M");
		this.width = width;
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size == null) {
			super.measure(socket);
			size.width *= width;
		}
		return size;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		findExactArea(area);
	}

	@Override
	public String toString() {
		return " ";
	}

	@Override
	public String toMathML() {
		return "<mspace width=\"" + width + "em\" />";
	}

}
