package com.mathplayer.player.model.signs;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class FractionSign extends Sign {

	public final int SPACE_FONT_COEFF = 20;
	public final int SPACE_FONT_COEFF2 = 2;
	public final int LINE_POSITION_COEFF = 3;

	@Override
	public Size measure(InteractionSocket socket) {
		size = new Size(0, getSpaceHeight());
		return size;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.getContext2d().setFillStyle(font.color.toString());
		canvas.getContext2d().fillRect(area.x, (int) area.y + getSpaceHeight() / LINE_POSITION_COEFF, area.width, getSignHeight());
	}

	private int getSignHeight() {
		return font.size / SPACE_FONT_COEFF + 1;
	}

	private int getSpaceHeight() {
		return font.size / SPACE_FONT_COEFF2 + getSignHeight();
	}

}
