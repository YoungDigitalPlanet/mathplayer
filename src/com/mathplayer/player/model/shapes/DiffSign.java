package com.mathplayer.player.model.shapes;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
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
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		double widthBuffer = canvas.getLineWidth();
		canvas.setLineWidth(Math.round(0.07*height));				
		long height = Math.round(this.height);
		
		ShapeBuilder ctx = new ShapeBuilder();
		ctx.moveTo(Math.round(0.12*width) + posX, Math.round(0.28*height) + posY);		
		ctx.drawLineTo(new Vector2(Math.round(0.82*width) + posX, Math.round(0.96*height) + posY));		
		canvas.strokeShape(ctx.build());
		canvas.setLineWidth(widthBuffer);		
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(Math.round(0.9*width), height);		
	}
}