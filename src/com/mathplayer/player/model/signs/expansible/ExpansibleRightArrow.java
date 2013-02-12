package com.mathplayer.player.model.signs.expansible;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.Shape;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;
import gwt.g2d.client.math.Vector2;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;

public class ExpansibleRightArrow implements ExpansibleSign{

	private static final double ARROWHEAD_WIDTH_RATE = 0.20d;
	private static final double ARROWHEAD_HEIGHT_RATE = 0.45d;
	private static final double ARROW_MIDDLE_LINE_THICKNESS = 0.06;
	private static final double MIDDLE_PANEL_PADDING = 0.1d;
	private Font font;
	
	@Override
	public Size measureLeftPanel(InteractionSocket socket) {
		return new Size(0, 0, 0); //no left panel in right arrow is needed
	}

	@Override
	public Size measureRightPanel(InteractionSocket socket) {
		double arrowHeadWidth = ARROWHEAD_WIDTH_RATE * font.size;
		double arrowHeadHeight = ARROWHEAD_HEIGHT_RATE * font.size;
		double middleLine = arrowHeadHeight / 2;
		
		return new Size(arrowHeadWidth, arrowHeadHeight, middleLine);
	}

	@Override
	public Size measureMiddlePanel(InteractionSocket socket) {
		double arrowMiddleLineHeight = ARROW_MIDDLE_LINE_THICKNESS * font.size;
		double middlePanelPadding = MIDDLE_PANEL_PADDING * font.size;
		double middlePanelHeight = middlePanelPadding + arrowMiddleLineHeight + middlePanelPadding;
		double middleLine = middlePanelHeight / 2;
		
		return new Size(0, middlePanelHeight, middleLine);
	}
	
	@Override
	public void renderMiddlePanel(Surface canvas, Area area, InteractionSocket socket) {
		double middlePanelPadding = MIDDLE_PANEL_PADDING * font.size;
		
		ShapeBuilder shapeBuilder = new ShapeBuilder();
		shapeBuilder.drawRect(area.x, area.y + middlePanelPadding, area.width, area.height - (2*middlePanelPadding));
		Shape shape = shapeBuilder.build();
		canvas.fillShape(shape);
	}
	
	@Override
	public void renderLeftPanel(Surface canvas, Area area, InteractionSocket socket) {
		//nothing on left panel
	}
	
	@Override
	public void renderRightPanel(Surface canvas, Area area, InteractionSocket socket) {
		Vector2 topVertex = new Vector2(area.x, area.y);
		Vector2 leftMiddleVertex = new Vector2(area.x, area.y+ (area.height / 2));
		Vector2 rightMiddleVertex = new Vector2(area.x  + area.width, area.y+ (area.height / 2));
		Vector2 bottomVertex = new Vector2(area.x, area.y + area.height);
		
		ShapeBuilder shapeBuilder = new ShapeBuilder();
		shapeBuilder.drawLineSegment(topVertex, rightMiddleVertex);
		shapeBuilder.drawLineSegment(rightMiddleVertex, bottomVertex);
		shapeBuilder.drawLineSegment(leftMiddleVertex, rightMiddleVertex);
		Shape shape = shapeBuilder.build();
		
		double lineWidth = font.size * ARROW_MIDDLE_LINE_THICKNESS;
		canvas.setLineWidth(lineWidth);
		canvas.strokeShape(shape);
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public String toMathML() {
		return "<mo>RightArrow</mo>";
	}
}
