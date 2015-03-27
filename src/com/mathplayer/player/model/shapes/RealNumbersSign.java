package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class RealNumbersSign extends Sign {

	private final double posX, posY, height, width;

	public RealNumbersSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.width = height;
		this.height = height;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		long height = Math.round(this.height);

		Context2d context2d = canvas.getContext2d();
		context2d.strokeRect(posX, posY, Math.round(0.18 * height), height);

		context2d.beginPath();
		context2d.moveTo(Math.round(0.18 * height) + posX, posY);
		context2d.bezierCurveTo(height + posX, posY, height + posX, Math.round(0.59 * height) + posY, Math.round(0.18 * height) + posX,
								Math.round(0.59 * height) + posY);
		context2d.moveTo(Math.round(0.18 * height) + posX, posY);
		context2d.bezierCurveTo(height + posX - Math.round(0.24 * height), posY, height + posX - Math.round(0.24 * height), Math.round(0.59 * height) + posY,
								Math.round(0.18 * height) + posX, Math.round(0.59 * height) + posY);
		context2d.lineTo(Math.round(0.41 * height) + posX, Math.round(0.59 * height) + posY);
		context2d.lineTo(Math.round(0.88 * height) + posX, height + posY);
		context2d.lineTo(Math.round(0.65 * height) + posX, height + posY);
		context2d.lineTo(Math.round(0.18 * height) + posX, Math.round(0.53 * height) + posY);

		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(1.1 * this.width), this.height);
	}
}