package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class DisjunctionSign extends Sign {

	private double posX, posY, height, width;

	public DisjunctionSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.width = height;
		this.height = height;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		Context2d context2d = canvas.getContext2d();
		context2d.beginPath();

		long height = Math.round(this.height);
		long width = Math.round(this.width);

		double centerX = Math.round(0.55 * height) + posX;
		double centerY = Math.round(0.59 * height) + posY;

		context2d.moveTo(centerX, centerY + height / 3);
		context2d.lineTo(centerX - width / 3, centerY - height / 3);
		context2d.moveTo(centerX, centerY + height / 3);
		context2d.lineTo(centerX + width / 3, centerY - height / 3);

		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(width, height);
	}

}