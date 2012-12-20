package com.mathplayer.player.model.shapes;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
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
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		long height = Math.round(this.height);
		
		ShapeBuilder ctx = new ShapeBuilder();
		ctx.moveTo(Math.round(0.06*height) + posX, Math.round(0.06*height) + posY);
		ctx.drawLineTo(new Vector2(Math.round(0.76*height) + posX, Math.round(0.06*height) + posY));
		ctx.drawLineTo(new Vector2(Math.round(0.24*height) + posX, Math.round(0.94*height) + posY));
		ctx.moveTo(Math.round(0.53*height) + posX, Math.round(0.06*height) + posY);
		ctx.drawLineTo(new Vector2(Math.round(0.06*height) + posX, Math.round(0.94*height) + posY));
		ctx.moveTo(Math.round(0.06*height) + posX, Math.round(0.94*height) + posY);
		ctx.drawLineTo(new Vector2(Math.round(0.76*height) + posX, Math.round(0.94*height) + posY));
		canvas.strokeShape(ctx.build());		
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(0.8*width), height);
	}
	
}