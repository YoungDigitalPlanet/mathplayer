package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.CanvasElement;
import gwt.g2d.client.graphics.canvas.Context;

import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;

public class MFencedBracket {
	private FenceType fenceType;
	private Font font;
	private Surface s = null;
	public MFencedBracket(FenceType fenceType,Font font) {
		this.fenceType = fenceType;
		this.font = font;
	}
	public void render(Size size,boolean openTag) {
		s = new Surface();
		 Context context2d = s.getCanvas().getContext2D();
		 s.setFont(font.toString());
		 context2d.fillText(openTag ? FenceType.getOpenFence(fenceType) : FenceType.getCloseFence(fenceType), 0, size.height);
		 context2d.stroke();
		 context2d.fill();
		 ;
	}

	public CanvasElement getBracket(){
		return s.getCanvas();
	}

	public int getHeight(){
		return s.getHeight();
	}
}
