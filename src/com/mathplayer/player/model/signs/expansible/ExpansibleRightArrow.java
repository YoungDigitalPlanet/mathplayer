package com.mathplayer.player.model.signs.expansible;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.utils.LineDrawer;

public class ExpansibleRightArrow implements ExpansibleSign {

	private static final double ARROWHEAD_WIDTH_RATE = 0.20d;
	private static final double ARROWHEAD_HEIGHT_RATE = 0.45d;
	private static final double ARROW_MIDDLE_LINE_THICKNESS = 0.06;
	private static final double MIDDLE_PANEL_PADDING = 0.1d;
	private LineDrawer lineDrawer = LineDrawer.getInstance();
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
	public void renderMiddlePanel(Canvas canvas, Area area, InteractionSocket socket) {
		double middlePanelPadding = MIDDLE_PANEL_PADDING * font.size;
		Context2d context2d = canvas.getContext2d();
		context2d.fillRect(area.x, area.y + middlePanelPadding, area.width, area.height - (2 * middlePanelPadding));
	}

	@Override
	public void renderLeftPanel(Canvas canvas, Area area, InteractionSocket socket) {
		//nothing on left panel
	}

	@Override
	public void renderRightPanel(Canvas canvas, Area area, InteractionSocket socket) {
		Position topVertex = new Position(area.x, area.y);
		Position leftMiddleVertex = new Position(area.x, area.y + (area.height / 2));
		Position rightMiddleVertex = new Position(area.x + area.width, area.y + (area.height / 2));
		Position bottomVertex = new Position(area.x, area.y + area.height);

		Context2d context2d = canvas.getContext2d();

		double lineWidth = font.size * ARROW_MIDDLE_LINE_THICKNESS;
		context2d.setLineWidth(lineWidth);

		lineDrawer.drawLine(context2d, topVertex, rightMiddleVertex);
		lineDrawer.drawLine(context2d, rightMiddleVertex, bottomVertex);
		lineDrawer.drawLine(context2d, leftMiddleVertex, rightMiddleVertex);
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
