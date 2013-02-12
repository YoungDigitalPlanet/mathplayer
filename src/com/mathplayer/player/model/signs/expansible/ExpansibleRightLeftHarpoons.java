package com.mathplayer.player.model.signs.expansible;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;

public class ExpansibleRightLeftHarpoons implements ExpansibleSign{

	private static final double HARPOON_HEAD_WIDTH_RATE = 0.65d;
	private static final double HARPOON_HEAD_HEIGHT_RATE = 0.25d;
	private static final double HARPOON_MIDDLE_LINE_THICKNESS = 0.06;
	private static final double DISTANCE_BETWEEN_HARPOONS = 0.25d;
	private static final double MIDDLE_PANEL_PADDING = 0.1d;
	private Font font;

	@Override
	public Size measureLeftPanel(InteractionSocket socket) {
		return measurePanel();
	}
	
	@Override
	public Size measureRightPanel(InteractionSocket socket) {
		return measurePanel();
	}
	
	private Size measurePanel() {
		int fontSize = font.size;
		double harpoonHeight = fontSize * HARPOON_HEAD_HEIGHT_RATE;
		double middleLineThickness = fontSize * HARPOON_MIDDLE_LINE_THICKNESS;
		double distanceBetweenHarpoons = DISTANCE_BETWEEN_HARPOONS * fontSize;
		
		double summarizedHeight = (harpoonHeight *2) + (middleLineThickness *2) + distanceBetweenHarpoons;
		double width = fontSize * HARPOON_HEAD_WIDTH_RATE;
		double middleLine = summarizedHeight / 2d;
		
		return new Size(width, summarizedHeight, middleLine);
	}

	@Override
	public Size measureMiddlePanel(InteractionSocket socket) {
		int fontSize = font.size;
		double middleLineThickness = fontSize * HARPOON_MIDDLE_LINE_THICKNESS;
		double distanceBetweenHarpoons = DISTANCE_BETWEEN_HARPOONS * fontSize;
		double paddingFromMiddlePanel = MIDDLE_PANEL_PADDING * fontSize;
		
		double summarizedHeight = (2*middleLineThickness) + distanceBetweenHarpoons + (2*paddingFromMiddlePanel);
		double middleLine = summarizedHeight / 2d;
		
		return new Size(0, summarizedHeight, middleLine);
	}

	@Override
	public void renderMiddlePanel(Surface canvas, Area area, InteractionSocket socket) {
		double padding = MIDDLE_PANEL_PADDING * font.size;
		double distanceBetweenHarpoons = DISTANCE_BETWEEN_HARPOONS * font.size;
		
		Vector2 leftTopVertex = new Vector2(area.x, area.y + padding);
		Vector2 rightTopVertex = new Vector2(area.x + area.width, area.y + padding);
		Vector2 leftBottomVertex = new Vector2(area.x, area.y + padding+distanceBetweenHarpoons);
		Vector2 rightBottomVertex = new Vector2(area.x + area.width, area.y + padding+distanceBetweenHarpoons);
		
		ShapeBuilder shapeBuilder = new ShapeBuilder();
		shapeBuilder.drawLineSegment(leftTopVertex, rightTopVertex);
		shapeBuilder.drawLineSegment(leftBottomVertex, rightBottomVertex);
		
		strokeShapeWithCorrectLineWidth(canvas, shapeBuilder);
	}

	@Override
	public void renderLeftPanel(Surface canvas, Area area, InteractionSocket socket) {
		double higherLineYCoordinate = calculateHigherLineYCoordinate(area);
		double lowerLineYCoordinate = calculateLowerLineYCoordinate(higherLineYCoordinate);
		
		ShapeBuilder shapeBuilder = new ShapeBuilder();
		drawMiddleLinesContinousFragments(area, higherLineYCoordinate, lowerLineYCoordinate, shapeBuilder);
		drawLeftBottomHarpoon(area, lowerLineYCoordinate, shapeBuilder);
		
		strokeShapeWithCorrectLineWidth(canvas, shapeBuilder);
	}
	
	@Override
	public void renderRightPanel(Surface canvas, Area area, InteractionSocket socket) {
		double higherLineYCoordinate = calculateHigherLineYCoordinate(area);
		double lowerLineYCoordinate = calculateLowerLineYCoordinate(higherLineYCoordinate);
		
		ShapeBuilder shapeBuilder = new ShapeBuilder();
		drawMiddleLinesContinousFragments(area, higherLineYCoordinate, lowerLineYCoordinate, shapeBuilder);
		drawTopRightHarpoon(area, higherLineYCoordinate, shapeBuilder);
		
		strokeShapeWithCorrectLineWidth(canvas, shapeBuilder);
	}

	private void strokeShapeWithCorrectLineWidth(Surface canvas, ShapeBuilder shapeBuilder) {
		double lineWidth = HARPOON_MIDDLE_LINE_THICKNESS * font.size;
		canvas.setLineWidth(lineWidth);
		canvas.strokeShape(shapeBuilder.build());
	}

	private void drawLeftBottomHarpoon(Area area, double lowerLineYCoordinate, ShapeBuilder shapeBuilder) {
		double harpoonHeadWidth = (HARPOON_HEAD_WIDTH_RATE * 0.6) * font.size;
		double harpoonHeadHeight = HARPOON_HEAD_HEIGHT_RATE * font.size;
		Vector2 lowerHarpoonTopVertex = new Vector2(area.x, lowerLineYCoordinate);
		Vector2 lowerHarpoonBottomVertex = new Vector2(area.x + harpoonHeadWidth, lowerLineYCoordinate + harpoonHeadHeight);
		shapeBuilder.drawLineSegment(lowerHarpoonTopVertex, lowerHarpoonBottomVertex);
	}

	private void drawTopRightHarpoon(Area area, double higherLineYCoordinate, ShapeBuilder shapeBuilder) {
		double harpoonHeadWidth = (HARPOON_HEAD_WIDTH_RATE * 0.6) * font.size;
		Vector2 lowerHarpoonTopVertex = new Vector2(area.x +area.width - harpoonHeadWidth, area.y);
		Vector2 lowerHarpoonBottomVertex = new Vector2(area.x + area.width, higherLineYCoordinate);
		shapeBuilder.drawLineSegment(lowerHarpoonTopVertex, lowerHarpoonBottomVertex);
	}

	private void drawMiddleLinesContinousFragments(Area area, double higherLineYCoordinate, double lowerLineYCoordinate, ShapeBuilder shapeBuilder) {
		Vector2 leftTopVertex = new Vector2(area.x, higherLineYCoordinate);
		Vector2 rightTopVertex = new Vector2(area.x + area.width, higherLineYCoordinate);
		shapeBuilder.drawLineSegment(leftTopVertex, rightTopVertex);
		
		Vector2 leftBottomVertex = new Vector2(area.x, lowerLineYCoordinate);
		Vector2 rightBottomVertex = new Vector2(area.x + area.width, lowerLineYCoordinate);
		shapeBuilder.drawLineSegment(leftBottomVertex, rightBottomVertex);
	}

	private double calculateLowerLineYCoordinate(double higherLineYCoordinate) {
		double distanceBetweenHarpoons = DISTANCE_BETWEEN_HARPOONS * font.size;
		double lowerLineYCoordinate = higherLineYCoordinate + distanceBetweenHarpoons;
		return lowerLineYCoordinate;
	}

	private double calculateHigherLineYCoordinate(Area area) {
		double higherHarpoonOffset = HARPOON_HEAD_HEIGHT_RATE * font.size;
		
		double higherLineYCoordinate = area.y + higherHarpoonOffset;
		return higherLineYCoordinate;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public String toMathML() {
		return "<mo>RightLeftHarpoons</mo>";
	}

}
