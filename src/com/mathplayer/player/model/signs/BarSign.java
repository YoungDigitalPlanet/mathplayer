package com.mathplayer.player.model.signs;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;
import com.mathplayer.player.model.layoutschematas.BarType;

public class BarSign extends Sign {

	protected static final double WING_COEFF = 0.15d;
	protected static final double MARGIN_VERTICAL = 0.5d;
	protected static final double MARGIN_VERTICAL_MID = 0.3d;
	protected static final double WIDTH_MIN_COEFF = 0.5d;
	protected static final int LINE_THICNKENSS_COEFF = 20;
	protected BarType barType;
	
	public BarSign(BarType barType){
		this.barType = barType;
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size.clone();
		
		int width = (int) (font.size * (WING_COEFF * 2 + WIDTH_MIN_COEFF));
		int height = (int) (font.size * MARGIN_VERTICAL * 2);
		if (barType == BarType.ARROW)
			height += font.size * WING_COEFF * 2;
		else if (barType == BarType.DOUBLE) 
			height += font.size * MARGIN_VERTICAL_MID;
		
		size = new Size(width, height);
		
		return size.clone();				
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		int lineThickness = font.size/LINE_THICNKENSS_COEFF + 1;
		
		ShapeBuilder sb = new ShapeBuilder();
		canvas.setLineWidth(lineThickness);
		canvas.setStrokeStyle(font.color);
		 
		if (barType == BarType.DOUBLE){
			double midMarginHalf = font.size * MARGIN_VERTICAL_MID/2;
			sb.drawLineSegment(area.x, area.y + area.height/2 - midMarginHalf, area.x + area.width, area.y + area.height/2 - midMarginHalf);
			sb.drawLineSegment(area.x, area.y + area.height/2 + midMarginHalf, area.x + area.width, area.y + area.height/2 + midMarginHalf);			
		} else {
			sb.drawLineSegment(area.x, area.y + area.height/2, area.x + area.width, area.y + area.height/2);
			if (barType == BarType.ARROW){
				sb.drawLineSegment(area.x + area.width - font.size * WING_COEFF * 2, 
						area.y + area.height/2 - font.size * WING_COEFF * 2, 
						area.x + area.width, 
						area.y + area.height/2);
				sb.drawLineSegment(area.x + area.width - font.size * WING_COEFF * 2, 
						area.y + area.height/2 + font.size * WING_COEFF * 2, 
						area.x + area.width, 
						area.y + area.height/2);
			}
		}
		
		
		canvas.strokeShape(sb.build());
	}

}
