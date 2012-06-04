package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.CanvasElement;
import gwt.g2d.client.graphics.canvas.CanvasPixelArray;
import gwt.g2d.client.graphics.canvas.Context;
import gwt.g2d.client.graphics.canvas.ImageData;

import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;

public class MFencedBracket {
	private FenceType fenceType;
	private Font font;
	private Surface s = null;
	private double letterWidth = 0;

	public MFencedBracket(FenceType fenceType, Font font) {
		this.fenceType = fenceType;
		this.font = font;
	}

	/**
	 * tworzy nawias o odpowiedniej wielkosci
	 *
	 * @param fontSize
	 * @param openTag
	 */
	public void render(Size fontSize, boolean openTag) {
		this.s = new Surface(100, font.size);
		Context context2d = this.s.getCanvas().getContext2D();
		context2d.setStrokeStyle(font.color.getColorCode());
		this.s.setFont(font.toString());
		switch (fenceType) {
		case CURLY:
			context2d.setLineWidth(1.5);
			font.size-=2; //korygujemy ze wzgldu na grubosc linii ucina na dole
			letterWidth = drawCurlyBracket(context2d);
			break;
		case ROUND:
			font.size-=2;//korygujemy ze wzgldu na grubosc linii ucina na dole
			letterWidth = drawRoundBracket(context2d);
			if (openTag) {
				rotate(context2d, font.size, letterWidth);
			}
			break;
		case SQUARE:
			font.size-=2; //korygujemy ze wzgldu na grubosc linii ucina na dole
			letterWidth = drawSquareBracket(context2d, openTag);
			break;
		case ANGLE:
			letterWidth = drawPointyBracket(context2d);
			break;
		}
		if (!openTag && fenceType != FenceType.ROUND && fenceType != FenceType.SQUARE) {
			rotate(context2d, font.size, letterWidth);
		}
	}

	/**
	 * obraca canvas o 180 stopni aby otrzymac zamykajacy nawias
	 *
	 * @param context2d
	 * @param letterSize
	 * @param letterWidth
	 */
	private void rotate(Context context2d, int letterSize, double letterWidth) {
		Surface s = new Surface(this.s.getWidth(),this.s.getHeight());
		s.translate(s.getWidth() / 2, s.getHeight() / 2);
		s.rotate(Math.PI);
		s.translate(-s.getWidth() / 2, -s.getHeight() / 2);
		s.translate(s.getWidth() - letterWidth, s.getHeight() - letterSize);
		s.drawImage(this.s.getCanvas(), 0, 0, s.getWidth(), s.getHeight());
		s.save();
		this.s = s;
	}
	/**
	 * rysuje nawias ostry <
	 *
	 * @param context2d
	 * @return
	 */
	private double drawPointyBracket(Context context2d) {
		double divisor = font.size / 108f; // 108 bazowa wielkosc klamry
		double width = 30 * divisor;
		double height = 108 * divisor;
		context2d.beginPath();
		context2d.moveTo(width, 0.00);
		context2d.lineTo(0, height / 2);
		context2d.lineTo(width, height);
		context2d.stroke();
		return width;
	}

	/**
	 * rysuje nawias kwadratowy [
	 *
	 * @param context2d
	 *            na czym ma rysowac
	 * @return szerokosc znaku
	 */
	private double drawSquareBracket(Context context2d, boolean openTag) {
		double divisor = font.size / 108f; // 108 bazowa wielkosc klamry
		int width = (int)Math.round(20 * divisor);
		int height = (int)Math.round( 108 * divisor);
		try {
			ImageData imageData = context2d.getImageData(0, 0, width, height);
			CanvasPixelArray cpa = imageData.getPixelArray();
			drawHorizontalLine(cpa, width, 0, width-1, 0, font.color);
			drawHorizontalLine(cpa, width, 0, width-1, height-1, font.color);
			int sx = ( (openTag) ? 0 : (int)width-2 );
			drawVerticalLine(cpa, width, 1, height-2, sx, font.color);
			drawVerticalLine(cpa, width, 1, height-2, sx+1, font.color);
			context2d.putImageData(imageData, 0, 0, 0, 0, width, height);
			
		} catch (Exception e) {
			// implementation for IE
			context2d.beginPath();
			context2d.moveTo( ( (openTag) ? (int)width : 0.0d ) , 0.00);
			context2d.lineTo( ( (openTag) ? 0.0d : (int)width ) , 0);
			context2d.lineTo( ( (openTag) ? 0.0d : (int)width ) , (int)height);
			context2d.lineTo( ( (openTag) ? (int)width : 0.0d ) , (int)height);
			context2d.stroke();
		}
		return width;
	}
	
	private void drawHorizontalLine(CanvasPixelArray cpa, int lineWidth, int fromX, int toX,int y, Color color){
		for (int x = fromX ; x <= toX ; x ++){
			cpa.setData(y*lineWidth*4 + x*4, color.getR());
			cpa.setData(y*lineWidth*4 + x*4+1, color.getG());
			cpa.setData(y*lineWidth*4 + x*4+2, color.getB());
			cpa.setData(y*lineWidth*4 + x*4+3, (int)(255*color.getAlpha()));
		}
	}
	
	private void drawVerticalLine(CanvasPixelArray cpa, int lineWidth, int fromY, int toY,int x, Color color){
		for (int y = fromY ; y <= toY ; y ++){
			cpa.setData(y*lineWidth*4 + x*4, color.getR());
			cpa.setData(y*lineWidth*4 + x*4+1, color.getG());
			cpa.setData(y*lineWidth*4 + x*4+2, color.getB());
			cpa.setData(y*lineWidth*4 + x*4+3, (int)(255*color.getAlpha()));
		}
	}

	/**
	 * rysuje nawias odwrocony okragly )
	 *
	 * @param context2d
	 *            na czym ma rysowac
	 * @return szerokosc znaku
	 */
	private double drawRoundBracket(Context context2d) {
		double divisor = font.size / 108f; // 108 bazowa wielkosc klamry
		double width = 40 * divisor;
		double height = 108 * divisor;
		context2d.moveTo(width * divisor, 0.00);
		for (int x = 0; x < 2 ; x++) {
			context2d.beginPath();
			context2d.moveTo(0, 0.00);
			context2d.bezierCurveTo(0, 0.00, width -x, 50 * divisor, 0, height);
			context2d.stroke();
		}
		
		// Algorytm de Casteljau wyznaczania wspolrzednej punktu na krzywej Beziera
		
		int p0x = 0;
		int p1x = (int)width;
		int p2x = 0;

		int p0x1 = (p0x + p1x)/2;
		int p1x1 = (p1x + p2x)/2;

		int p0x2 = (p0x1 + p1x1)/2;
				
		return p0x2+1;
	}

	/**
	 * rysuje nawias klamrowy {
	 *
	 * @param context2d
	 *            na czym ma rysowac
	 * @return szerokosc znaku
	 */
	private double drawCurlyBracket(Context context2d) {
		double divisor = font.size / 108f; // 108 bazowa wielkosc klamry
		context2d.moveTo(20 * divisor, 1.00 * divisor);
		context2d.beginPath();
		context2d.bezierCurveTo(20.00 * divisor, 1.00 * divisor, 10.00 * divisor, 2.00 * divisor, 10.00 * divisor, 15.00 * divisor);
		context2d.bezierCurveTo(10.00 * divisor, 24.00 * divisor, 10.00 * divisor, 30.00 * divisor, 10.00 * divisor, 35.00 * divisor);
		context2d.bezierCurveTo(10.00 * divisor, 47.00 * divisor, 10.00 * divisor, 53.00 * divisor, 0.00 * divisor, 54.00 * divisor);
		context2d.bezierCurveTo(10.00 * divisor, 55 * divisor, 10.00 * divisor, 61 * divisor, 10.00 * divisor, 93 * divisor);
		context2d.bezierCurveTo(10.00 * divisor, 93 * divisor, 10.00 * divisor, 107 * divisor, 20.00 * divisor, 108.00 * divisor);
		context2d.stroke();
		return 20 * divisor;
	}

	public CanvasElement getBracket() {
		return s.getCanvas();
	}

	public int getCanvasHeight() {
		return s.getHeight();
	}

	public int getCanvasWidth() {
		return s.getWidth();
	}

	public double getLetterWidth() {
		return letterWidth;
	}
}
