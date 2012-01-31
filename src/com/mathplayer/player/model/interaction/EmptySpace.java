package com.mathplayer.player.model.interaction;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;

import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.InteractionToken;

public class EmptySpace extends InteractionToken {

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;
		
		size = new Size(font.size, font.size, font.size/2);
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		//canvas.setFillStyle(new Color(200, 200, 255, 0.4d));
		//canvas.fillRectangle(exactArea.x, exactArea.y, exactArea.width, exactArea.height);

		canvas.setLineWidth(0.5);
		canvas.setStrokeStyle(new Color(0,0,0,1));
		canvas.setLineCap(gwt.g2d.client.graphics.LineCap.BUTT);
		ShapeBuilder sb = new ShapeBuilder();
		sb.drawRect(exactArea.x+1, exactArea.y+1, exactArea.width-2, exactArea.height-2);
		canvas.strokeShape(sb.build());
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public String toMathML() {
		return "";
	}

}
