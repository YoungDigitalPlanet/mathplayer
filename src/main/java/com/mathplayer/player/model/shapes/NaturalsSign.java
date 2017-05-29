package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class NaturalsSign extends Sign {

	private double posX, posY, width, height;

	public NaturalsSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = height;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		long height = Math.round(this.height);
		long width = Math.round(this.width);

		Context2d context2d = canvas.getContext2d();
		context2d.beginPath();

		context2d.moveTo(posX, this.height + posY);
		context2d.lineTo(posX, posY);
		context2d.lineTo(Math.round(0.76 * width) + posX, Math.round(0.82 * height) + posY);
		context2d.moveTo(Math.round(0.76 * width) + posX, height + posY);
		context2d.lineTo(posX, Math.round(0.18 * height) + posY);
		context2d.moveTo(Math.round(0.76 * width) + posX, posY);
		context2d.lineTo(Math.round(0.76 * width) + posX, height + posY);

		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(0.9 * width), height);
	}

}