package com.mathplayer.player.model.shapes;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
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
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		long height = Math.round(0.94*this.height);
		long width = Math.round(0.94*this.width);
		
		ShapeBuilder ctx = new ShapeBuilder();
		double centerX = Math.round(0.55 * height) + posX;
		double centerY = Math.round(0.59 * height) + posY;
		ctx.moveTo(centerX, centerY - height / 2);

		ctx.drawBezierCurveTo(centerX + width / 2, centerY - height / 2, centerX + width / 2, centerY + height / 2, centerX, centerY + height / 2);
		ctx.drawBezierCurveTo(centerX - width / 2, centerY + height / 2, centerX - width / 2, centerY - height / 2, centerX, centerY - height / 2);
		ctx.drawBezierCurveTo(centerX + width / 3, centerY - height / 2, centerX + width / 3, centerY + height / 2, centerX, centerY + height / 2);
		ctx.drawBezierCurveTo(centerX - width / 3, centerY + height / 2, centerX - width / 3, centerY - height / 2, centerX, centerY - height / 2);
		
		ctx.moveTo(centerX, centerY + height / 2);
		ctx.drawLineTo(new Vector2(Math.round(0.44*height) + centerX, Math.round(0.22*height) + centerY  + height / 2));
		ctx.drawLineTo(new Vector2(Math.round(0.30*height) + centerX, Math.round(0.32*height) + centerY  + height / 2));
		ctx.moveTo(centerX - Math.round(0.06*height), centerY + height / 2);
		ctx.drawLineTo(new Vector2(Math.round(0.30*height) + centerX, Math.round(0.32*height) + centerY  + height / 2));
				
		canvas.strokeShape(ctx.build());
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return new Size(width, height);
	}
	
}