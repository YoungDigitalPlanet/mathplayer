package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class IntegersSign extends Sign {

	private double posX, posY, width, height;

	public IntegersSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = height;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		long height = Math.round(this.height);
		Context2d context2d = canvas.getContext2d();
		context2d.beginPath();

		context2d.moveTo(Math.round(0.06 * height) + posX, Math.round(0.06 * height) + posY);
		context2d.lineTo(Math.round(0.76 * height) + posX, Math.round(0.06 * height) + posY);
		context2d.lineTo(Math.round(0.24 * height) + posX, Math.round(0.94 * height) + posY);
		context2d.moveTo(Math.round(0.53 * height) + posX, Math.round(0.06 * height) + posY);
		context2d.lineTo(Math.round(0.06 * height) + posX, Math.round(0.94 * height) + posY);
		context2d.moveTo(Math.round(0.06 * height) + posX, Math.round(0.94 * height) + posY);
		context2d.lineTo(Math.round(0.76 * height) + posX, Math.round(0.94 * height) + posY);
		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(0.8 * width), height);
	}

}