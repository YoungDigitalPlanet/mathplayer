package com.mathplayer.player.model.shapes;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
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
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		double widthBuffer = canvas.getLineWidth();
		canvas.setLineWidth(Math.round(0.07*height));		
		long height = Math.round(this.height);
		long width = Math.round(this.width);
		
		ShapeBuilder ctx = new ShapeBuilder();
		ctx.moveTo(posX, this.height + posY);		
		ctx.drawLineTo(new Vector2(posX, posY));
		ctx.drawLineTo(new Vector2(Math.round(0.76*width) + posX, Math.round(0.82*height) + posY));
		ctx.moveTo(Math.round(0.76*width) + posX, height + posY);
		ctx.drawLineTo(new Vector2(posX, Math.round(0.18*height) + posY));
		ctx.moveTo(Math.round(0.76*width) + posX, posY);
		ctx.drawLineTo(new Vector2(Math.round(0.76*width) + posX, height + posY));
		canvas.strokeShape(ctx.build());		
		canvas.setLineWidth(widthBuffer);
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(0.9*width), height);
	}
	
}