package com.mathplayer.player.model.signs;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class RootSign extends Sign {

	public static final double OVER_MARGIN = 0.35d;
	public static final double BETWEEN_MARGIN = 0.4d;
	public static final double OVERHANG_LEFT_WIDTH_1 = 0.2d;
	public static final double OVERHANG_LEFT_WIDTH_2 = 0.25d;
	public static final double OVERHANG_LEFT_HEIGHT = 0.4d;
	public static final double OVERHANG_RIGHT_MARGIN = 0.2d;
	public static final int LINE_THICNKENSS_COEFF = 20;

	public double getOverMargin(){
		return font.size*OVER_MARGIN;
	}

	public double getBetweenMargin(){
		return font.size*BETWEEN_MARGIN;
	}

	public double getOverhangLeftMargin(){
		return font.size*OVERHANG_LEFT_WIDTH_1 + font.size*OVERHANG_LEFT_WIDTH_2;
	}

	protected double getOverhangLeftWidth1(){
		return font.size*OVERHANG_LEFT_WIDTH_1;
	}

	protected double getOverhangLeftWidth2(){
		return font.size*OVERHANG_LEFT_WIDTH_2;
	}

	protected double getOverhangLeftHeight(){
		return font.size*OVERHANG_LEFT_HEIGHT;
	}

	public double getOverhangRightMargin(){
		return font.size*OVERHANG_RIGHT_MARGIN;
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		return null;
	}
	
	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {

		int lineThickness = font.size/LINE_THICNKENSS_COEFF + 1;
		
		ShapeBuilder builder = new ShapeBuilder();
		canvas.setLineWidth(lineThickness);
		builder.drawLineSegment(area.x + area.width + getOverhangRightMargin(), area.y - getOverMargin() + lineThickness, area.x, area.y - getOverMargin() + lineThickness);
		builder.drawLineSegment(area.x, area.y - getOverMargin() + lineThickness, area.x - getBetweenMargin(), area.y + area.height);
		builder.drawLineSegment(area.x - getBetweenMargin(), area.y + area.height, area.x - getBetweenMargin() - getOverhangLeftWidth1(), area.y + area.height - getOverhangLeftHeight());
		builder.drawLineSegment(area.x - getBetweenMargin() - getOverhangLeftWidth1(), area.y + area.height - getOverhangLeftHeight(), area.x - getBetweenMargin() - getOverhangLeftWidth1() - getOverhangLeftWidth2(), area.y + area.height - getOverhangLeftHeight());
		canvas.strokeShape(builder.build());
		
	}

}
