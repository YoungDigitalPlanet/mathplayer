package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class IntersectionSign extends Sign {

	private double posX, posY, width, height;

	public IntersectionSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = height;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		long height = Math.round(this.height);
		long width = Math.round(this.width);

		double centerX = Math.round(0.55 * width) + posX;
		double centerY = Math.round(0.59 * height) + posY;

		Context2d context2d = canvas.getContext2d();
		context2d.beginPath();

		context2d.moveTo(centerX - height / 3, centerY + height / 3);
		context2d.lineTo(centerX - height / 3, centerY);
		context2d.moveTo(centerX + height / 3, centerY + height / 3);
		context2d.lineTo(centerX + height / 3, centerY);
		context2d.moveTo(centerX - height / 3, centerY);
		context2d.bezierCurveTo(centerX - width / 4, centerY - height / 2, centerX + width / 4, centerY - height / 2, centerX + height / 3, centerY);
		context2d.moveTo(centerX, centerY);
		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(width, height);
	}

}