package com.mathplayer.player.model.signs;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;
import com.mathplayer.player.utils.LineDrawer;

public class RootSign extends Sign {

	public static final double OVER_MARGIN = 0.35d;
	public static final double BETWEEN_MARGIN = 0.3d;
	public static final double OVERHANG_LEFT_WIDTH_1 = 0.2d;
	public static final double OVERHANG_LEFT_WIDTH_2 = 0.25d;
	public static final double OVERHANG_LEFT_HEIGHT = 1.0d;
	public static final double OVERHANG_RIGHT_MARGIN = 0.2d;
	public static final double LINE_THICNKENSS_COEFF = 20;
	public static final double CONTENT_MARGIN = 0.25d;
	private LineDrawer lineDrawer = LineDrawer.getInstance();

	public double getOverMargin() {
		return font.size * OVER_MARGIN;
	}

	public double getBetweenMargin() {
		return font.size * BETWEEN_MARGIN;
	}

	public double getOverhangLeftMargin() {
		return font.size * (OVERHANG_LEFT_WIDTH_1 + OVERHANG_LEFT_WIDTH_2);
	}

	protected double getOverhangLeftWidth1() {
		return font.size * OVERHANG_LEFT_WIDTH_1;
	}

	protected double getOverhangLeftWidth2() {
		return font.size * OVERHANG_LEFT_WIDTH_2;
	}

	public double getOverhangLeftHeight() {
		return font.size * OVERHANG_LEFT_HEIGHT;
	}

	public double getOverhangRightMargin() {
		return font.size * OVERHANG_RIGHT_MARGIN;
	}

	public double getContentMargin() {
		return font.size * CONTENT_MARGIN;
	}

	@Override
	public Size measure(InteractionSocket socket) {
		return null;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		double lineThickness = (font.bold) ? font.size / LINE_THICNKENSS_COEFF + 1 : font.size / LINE_THICNKENSS_COEFF;

		Context2d context2d = canvas.getContext2d();
		context2d.setLineWidth(lineThickness);
		context2d.setStrokeStyle(font.color.toString());

		lineDrawer.drawLine(context2d, area.x + area.width + getOverhangRightMargin(), area.y - getOverMargin() + lineThickness, area.x - getContentMargin(),
							area.y - getOverMargin() + lineThickness);
		lineDrawer.drawLine(context2d, area.x - getContentMargin(), area.y - getOverMargin() + lineThickness, area.x - getContentMargin() - getBetweenMargin(),
							area.y + area.height);
		lineDrawer.drawLine(context2d, area.x - getContentMargin() - getBetweenMargin(), area.y + area.height,
							area.x - getContentMargin() - getBetweenMargin() - getOverhangLeftWidth1(), area.y + area.height - getOverhangLeftHeight());
		lineDrawer.drawLine(context2d, area.x - getContentMargin() - getBetweenMargin() - getOverhangLeftWidth1(),
							area.y + area.height - getOverhangLeftHeight(),
							area.x - getContentMargin() - getBetweenMargin() - getOverhangLeftWidth1() - getOverhangLeftWidth2(),
							area.y + area.height - getOverhangLeftHeight());
	}

}
