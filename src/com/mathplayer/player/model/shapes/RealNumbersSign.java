package com.mathplayer.player.model.shapes;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
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
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		long height = Math.round(this.height);
		
		double widthBuffer = canvas.getLineWidth();
		canvas.setLineWidth(Math.round(0.07*height));
		ShapeBuilder ctx = new ShapeBuilder();
		ctx.drawRect(posX, posY, Math.round(0.18*height), height);
		ctx.moveTo(Math.round(0.18*height) + posX, posY);
		ctx.drawBezierCurveTo(height + posX, posY, height + posX, Math.round(0.59*height) + posY, Math.round(0.18*height) + posX, Math.round(0.59*height) + posY);
		ctx.moveTo(Math.round(0.18*height) + posX, posY);
		ctx.drawBezierCurveTo(height + posX - Math.round(0.24*height), posY, height + posX - Math.round(0.24*height), Math.round(0.59*height) + posY, Math.round(0.18*height) + posX, Math.round(0.59*height) + posY);
		ctx.drawLineTo(new Vector2(Math.round(0.41*height) + posX, Math.round(0.59*height) + posY));
		ctx.drawLineTo(new Vector2(Math.round(0.88*height) + posX, height + posY));
		ctx.drawLineTo(new Vector2(Math.round(0.65*height) + posX, height + posY));
		ctx.drawLineTo(new Vector2(Math.round(0.18*height) + posX, Math.round(0.53*height) + posY));		
		canvas.strokeShape(ctx.build());		

		canvas.setLineWidth(widthBuffer);
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(1.1*this.width), this.height);
	}
}