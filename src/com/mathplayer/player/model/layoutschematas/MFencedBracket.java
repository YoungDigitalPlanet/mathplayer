package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.CanvasElement;
import gwt.g2d.client.graphics.canvas.CanvasPixelArray;
import gwt.g2d.client.graphics.canvas.Context;
import gwt.g2d.client.graphics.canvas.ImageData;

import java.util.HashMap;
import java.util.Map;

import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;

public class MFencedBracket {
	private FenceType fenceType;
	private Font font;
	private Surface s = null;
	int height = 0;
	private static Map<String, Integer> realFontsSizes = new HashMap<String, Integer>();
	public MFencedBracket(FenceType fenceType,Font font) {
		this.fenceType = fenceType;
		this.font = font;
	}

	/**
	 * mierzy czcionke na canvasie i podaje rzeczywista wielkosc
	 * @param size
	 * @return wielkosc w px
	 */
	private int getRealFontSize(Size size) {
		if (realFontsSizes.containsKey(font.toString())) {
			return realFontsSizes.get(font.toString());
		}
		Surface s = new Surface();
		Context context2d = s.getCanvas().getContext2D();
		s.setFont(font.toString());
		context2d.fillText("[", 0, size.height);
		context2d.stroke();
		context2d.fill();
		ImageData imageData = context2d.getImageData(0, 0, s.getWidth(), s.getHeight());
		CanvasPixelArray pixelArray = imageData.getPixelArray();
		int lastSetPix = 0;
		for (int x = 3; x < s.getWidth() * s.getHeight() * 4; x += 4) {
			// sprawdzamy alpha jak ustawiona to nadal jest tekst
			if (pixelArray.getData(x) > 0) {
				lastSetPix = x;
			}
		}
		int realFontSize = lastSetPix / 100 / 4;
		realFontsSizes.put(font.toString(), realFontSize);
		return realFontSize;
	}

	public void render(Size size,boolean openTag) {
		 s = new Surface();
		 Context context2d = s.getCanvas().getContext2D();
		 s.setFont(font.toString());
		 context2d.fillText(openTag ? FenceType.getOpenFence(fenceType) : FenceType.getCloseFence(fenceType), 0, size.height);
		 context2d.stroke();
		 context2d.fill();
		 height = getRealFontSize(size);

	}

	public CanvasElement getBracket(){
		return s.getCanvas();
	}

	public int getCanvasHeight(){
		return s.getHeight();
	}

	public int getTextSizeOnCanvas(){
		return height;
	}
}
