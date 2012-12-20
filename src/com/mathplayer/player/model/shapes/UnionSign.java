package com.mathplayer.player.model.shapes;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class UnionSign extends Sign {
	
	private double posX, posY, height, width;

	public UnionSign(double posX, double posY, double height) {
		this.posX = posX;
		this.posY = posY;
		this.width = height;
		this.height = height;		
	}
	
	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		canvas.getContext().beginPath();
		
		ShapeBuilder ctx = new ShapeBuilder();
		double widthBuffer = canvas.getLineWidth();
		canvas.setLineWidth(Math.round(1.5*widthBuffer));
		long height = Math.round(this.height);
		long width = Math.round(this.width);
		
		double centerX = Math.round(0.55 * height) + posX;
		double centerY = Math.round(0.59 * height) + posY; 		
		
		ctx.moveTo(centerX - width / 3, centerY - height / 3);
		ctx.drawLineTo(new Vector2(centerX - width / 3, centerY));
		ctx.moveTo(centerX - width / 3, centerY);				
		ctx.drawBezierCurveTo(centerX - width / 4, centerY + height / 2, centerX + width / 4, centerY + height / 2, centerX + height / 3, centerY);
		ctx.drawLineTo(new Vector2(centerX + width / 3, centerY - height / 3));
		ctx.moveTo(centerX + height / 3, centerY - height / 3);
		
		canvas.strokeShape(ctx.build());
		canvas.getContext().beginPath();
		
		canvas.setLineWidth(widthBuffer);
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(width, height);		
	}
	
}