package com.mathplayer.player.model.shapes;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class DiffSign extends Sign {

	private double posX, posY, height, width;

	public DiffSign(double posX, double posY, double height) {
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
		context2d.moveTo(Math.round(0.12 * width) + posX, Math.round(0.28 * height) + posY);
		context2d.lineTo(Math.round(0.82 * width) + posX, Math.round(0.96 * height) + posY);
		context2d.stroke();
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(0.9 * width), height);
	}
}