package com.mathplayer.player.model.interaction;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.InteractionToken;

public class EmptySpace extends InteractionToken {

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;

		size = new Size(font.size, font.size, font.size / 2);
		return size.clone();
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		Context2d context2d = canvas.getContext2d();

		context2d.setLineWidth(0.5);
		Color color = new Color(0, 0, 0, 1);
		context2d.setStrokeStyle(color.toString());
		context2d.setLineCap(Context2d.LineCap.BUTT);
		context2d.strokeRect(exactArea.x + 1, exactArea.y + 1, exactArea.width - 2, exactArea.height - 2);
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public String toMathML() {
		return "";
	}

}
