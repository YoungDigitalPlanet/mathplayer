package com.mathplayer.player.model.signs.expansible;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.utils.LineDrawer;

public class ExpansibleRightLeftHarpoons implements ExpansibleSign {

	private static final double HARPOON_HEAD_WIDTH_RATE = 0.65d;
	private static final double HARPOON_HEAD_HEIGHT_RATE = 0.25d;
	private static final double HARPOON_MIDDLE_LINE_THICKNESS = 0.06;
	private static final double DISTANCE_BETWEEN_HARPOONS = 0.25d;
	private static final double MIDDLE_PANEL_PADDING = 0.1d;
	private LineDrawer lineDrawer = LineDrawer.getInstance();
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

		double summarizedHeight = (harpoonHeight * 2) + (middleLineThickness * 2) + distanceBetweenHarpoons;
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

		double summarizedHeight = (2 * middleLineThickness) + distanceBetweenHarpoons + (2 * paddingFromMiddlePanel);
		double middleLine = summarizedHeight / 2d;

		return new Size(0, summarizedHeight, middleLine);
	}

	@Override
	public void renderMiddlePanel(Canvas canvas, Area area, InteractionSocket socket) {
		double padding = MIDDLE_PANEL_PADDING * font.size;
		double distanceBetweenHarpoons = DISTANCE_BETWEEN_HARPOONS * font.size;

		Position leftTopVertex = new Position(area.x, area.y + padding);
		Position rightTopVertex = new Position(area.x + area.width, area.y + padding);
		Position leftBottomVertex = new Position(area.x, area.y + padding + distanceBetweenHarpoons);
		Position rightBottomVertex = new Position(area.x + area.width, area.y + padding + distanceBetweenHarpoons);

		Context2d context2d = canvas.getContext2d();
		lineDrawer.drawLine(context2d, leftTopVertex, rightTopVertex);
		lineDrawer.drawLine(context2d, leftBottomVertex, rightBottomVertex);

		strokeShapeWithCorrectLineWidth(context2d);
	}

	@Override
	public void renderLeftPanel(Canvas canvas, Area area, InteractionSocket socket) {
		double higherLineYCoordinate = calculateHigherLineYCoordinate(area);
		double lowerLineYCoordinate = calculateLowerLineYCoordinate(higherLineYCoordinate);

		Context2d context2d = canvas.getContext2d();

		drawMiddleLinesContinousFragments(area, higherLineYCoordinate, lowerLineYCoordinate, context2d);
		drawLeftBottomHarpoon(area, lowerLineYCoordinate, context2d);

		strokeShapeWithCorrectLineWidth(context2d);
	}

	@Override
	public void renderRightPanel(Canvas canvas, Area area, InteractionSocket socket) {
		double higherLineYCoordinate = calculateHigherLineYCoordinate(area);
		double lowerLineYCoordinate = calculateLowerLineYCoordinate(higherLineYCoordinate);
		Context2d context2d = canvas.getContext2d();

		drawMiddleLinesContinousFragments(area, higherLineYCoordinate, lowerLineYCoordinate, context2d);
		drawTopRightHarpoon(area, higherLineYCoordinate, context2d);

		strokeShapeWithCorrectLineWidth(canvas.getContext2d());
	}

	private void strokeShapeWithCorrectLineWidth(Context2d context2d) {
		double lineWidth = HARPOON_MIDDLE_LINE_THICKNESS * font.size;
		context2d.setLineWidth(lineWidth);
		context2d.stroke();
	}

	private void drawLeftBottomHarpoon(Area area, double lowerLineYCoordinate, Context2d context2d) {
		double harpoonHeadWidth = (HARPOON_HEAD_WIDTH_RATE * 0.6) * font.size;
		double harpoonHeadHeight = HARPOON_HEAD_HEIGHT_RATE * font.size;
		Position lowerHarpoonTopVertex = new Position(area.x, lowerLineYCoordinate);
		Position lowerHarpoonBottomVertex = new Position(area.x + harpoonHeadWidth, lowerLineYCoordinate + harpoonHeadHeight);
		lineDrawer.drawLine(context2d, lowerHarpoonTopVertex, lowerHarpoonBottomVertex);
	}

	private void drawTopRightHarpoon(Area area, double higherLineYCoordinate, Context2d context2d) {
		double harpoonHeadWidth = (HARPOON_HEAD_WIDTH_RATE * 0.6) * font.size;
		Position lowerHarpoonTopVertex = new Position(area.x + area.width - harpoonHeadWidth, area.y);
		Position lowerHarpoonBottomVertex = new Position(area.x + area.width, higherLineYCoordinate);
		lineDrawer.drawLine(context2d, lowerHarpoonTopVertex, lowerHarpoonBottomVertex);
	}

	private void drawMiddleLinesContinousFragments(Area area, double higherLineYCoordinate, double lowerLineYCoordinate, Context2d context2d) {
		Position leftTopVertex = new Position(area.x, higherLineYCoordinate);
		Position rightTopVertex = new Position(area.x + area.width, higherLineYCoordinate);
		lineDrawer.drawLine(context2d, leftTopVertex, rightTopVertex);

		Position leftBottomVertex = new Position(area.x, lowerLineYCoordinate);
		Position rightBottomVertex = new Position(area.x + area.width, lowerLineYCoordinate);
		lineDrawer.drawLine(context2d, leftBottomVertex, rightBottomVertex);
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
