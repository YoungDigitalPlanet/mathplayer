package com.mathplayer.player.model.layoutschematas;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.signs.BarSign;
import com.mathplayer.player.model.tokens.MOperator;

public class MBar extends MOperator {

	protected BarSign vectorSign;
	private final BarType barType;

	public MBar(BarType barType) {
		super("_");
		this.barType = barType;
		vectorSign = new BarSign(barType);
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		vectorSign.setFont(font);
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size.clone();

		size = vectorSign.measure(socket);

		return size.clone();
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		Area vectorSignArea = new Area(area.x, area.y, area.width, area.height);
		vectorSign.render(canvas, vectorSignArea, socket);
	}

	public BarType getBarType() {
		return barType;
	}
}
