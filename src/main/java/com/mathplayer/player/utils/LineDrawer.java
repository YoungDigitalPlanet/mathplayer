package com.mathplayer.player.utils;

import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.Position;

public class LineDrawer {

	private final static LineDrawer INSTANCE = new LineDrawer();

	private LineDrawer(){
	}

	public static LineDrawer getInstance(){
		return INSTANCE;
	}

	public void drawLine(Context2d context2d, double startX, double startY, double endX, double endY) {
		context2d.beginPath();
		context2d.moveTo(startX, startY);
		context2d.lineTo(endX, endY);
		context2d.stroke();
	}

	public void drawLine(Context2d context2d, Position startPosition, Position endPosition) {
		context2d.beginPath();
		context2d.moveTo(startPosition.getX(), startPosition.getY());
		context2d.lineTo(endPosition.getX(), endPosition.getY());
		context2d.stroke();
	}
}
