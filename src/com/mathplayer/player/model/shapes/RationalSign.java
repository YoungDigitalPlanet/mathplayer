package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class RationalSign extends Sign {

	private double posX, posY, width, height;

	public RationalSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = height;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		long height = Math.round(0.94 * this.height);
		long width = Math.round(0.94 * this.width);

		double centerX = Math.round(0.55 * height) + posX;
		double centerY = Math.round(0.59 * height) + posY;

		Context2d context2d = canvas.getContext2d();
		context2d.beginPath();

		context2d.moveTo(centerX, centerY - height / 2);

		context2d.bezierCurveTo(centerX + width / 2, centerY - height / 2, centerX + width / 2, centerY + height / 2, centerX, centerY + height / 2);
		context2d.bezierCurveTo(centerX - width / 2, centerY + height / 2, centerX - width / 2, centerY - height / 2, centerX, centerY - height / 2);
		context2d.bezierCurveTo(centerX + width / 3, centerY - height / 2, centerX + width / 3, centerY + height / 2, centerX, centerY + height / 2);
		context2d.bezierCurveTo(centerX - width / 3, centerY + height / 2, centerX - width / 3, centerY - height / 2, centerX, centerY - height / 2);

		context2d.moveTo(centerX, centerY + height / 2);
		context2d.lineTo(Math.round(0.44 * height) + centerX, Math.round(0.22 * height) + centerY + height / 2);
		context2d.lineTo(Math.round(0.30 * height) + centerX, Math.round(0.32 * height) + centerY + height / 2);
		context2d.moveTo(centerX - Math.round(0.06 * height), centerY + height / 2);
		context2d.lineTo(Math.round(0.30 * height) + centerX, Math.round(0.32 * height) + centerY + height / 2);

		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(width, height);
	}

}