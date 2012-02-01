package com.mathplayer.player.model.signs;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;

import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;

public class VectorSign extends Sign {

	protected final double WING_COEFF = 0.1;
	protected final double MARGIN_VERTICAL = 0.4;
	protected static final int LINE_THICNKENSS_COEFF = 20;
	protected boolean arrow;
	
	public VectorSign(boolean arrow){
		this.arrow = arrow;
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size.clone();
		
		int width = (int) (font.size * WING_COEFF * 2);
		int height = (int) (font.size * MARGIN_VERTICAL * 2);
		if (arrow)
			height += font.size * WING_COEFF * 2;
		
		size = new Size(width, height);
		
		return size.clone();				
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		int lineThickness = font.size/LINE_THICNKENSS_COEFF + 1;
		
		ShapeBuilder sb = new ShapeBuilder();
		canvas.setLineWidth(lineThickness);
		
		sb.drawLineSegment(area.x, area.y + area.height/2, area.x + area.width, area.y + area.height/2);
		if (arrow){
			sb.drawLineSegment(area.x + area.width - font.size * WING_COEFF * 2, 
					area.y + area.height/2 - font.size * WING_COEFF * 2, 
					area.x + area.width, 
					area.y + area.height/2);
			sb.drawLineSegment(area.x + area.width - font.size * WING_COEFF * 2, 
					area.y + area.height/2 + font.size * WING_COEFF * 2, 
					area.x + area.width, 
					area.y + area.height/2);
		}
		
		canvas.strokeShape(sb.build());
	}

}
