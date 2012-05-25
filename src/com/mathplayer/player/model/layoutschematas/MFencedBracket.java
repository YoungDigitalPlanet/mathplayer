package com.mathplayer.player.model.layoutschematas;

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
		this.s = new Surface();
		Context context2d = this.s.getCanvas().getContext2D();
		this.s.setFont(font.toString());
		switch (fenceType) {
		case CURLY:
			context2d.setLineWidth(1.5);
			letterWidth = drawCurlyBracket(context2d);
			break;
		case ROUND:
			letterWidth = drawRoundBracket(context2d);
			if (openTag) {
				rotate(context2d, font.size, letterWidth);
			}
			break;
		case SQUARE:
			context2d.setLineWidth(3);
			letterWidth = drawSquareBracket(context2d);
			break;
		case ANGLE:
			letterWidth = drawPointyBracket(context2d);
			break;
		}
		if (!openTag && fenceType != FenceType.ROUND) {
			rotate(context2d, font.size, letterWidth);
		}
	}

	private double getImageWidth(Context context2d) {
		ImageData imageData = context2d.getImageData(0, 0, s.getWidth(), s.getHeight());
		CanvasPixelArray data = imageData.getPixelArray();
		int linePixelCount = imageData.getWidth() * 4;
		int width = imageData.getWidth() * linePixelCount;
		double letterWidth = 0;
		// mierzymy czcionke na canvasie
		for (int x = 3; x < width; x += 4) {
			if (data.getData(x) > 0) {
				if (x % linePixelCount / 4 > letterWidth) {
					letterWidth = Math.floor((x % linePixelCount) / 4);
				}
			}
		}
		return ++letterWidth;
	}

	/**
	 * obraca canvas o 180 stopni aby otrzymac zamykajacy nawias
	 *
	 * @param context2d
	 * @param letterSize
	 * @param letterWidth
	 */
	private void rotate(Context context2d, int letterSize, double letterWidth) {
		Surface s = new Surface();
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
	private double drawSquareBracket(Context context2d) {
		double divisor = font.size / 108f; // 108 bazowa wielkosc klamry
		double width = 20 * divisor;
		double height = 108 * divisor;
		context2d.beginPath();
		context2d.moveTo(width, 0.00);
		context2d.lineTo(0, 0);
		context2d.lineTo(0, height);
		context2d.lineTo(width, height);
		context2d.stroke();
		return width;
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
		double width = 50 * divisor;
		double height = 108 * divisor;
		context2d.moveTo(width * divisor, 0.00);
		for (int x = 0; x < 1 + 4; x++) {
			context2d.beginPath();
			context2d.moveTo(0, 0.00);
			context2d.bezierCurveTo(0, 0.00, width - x, 50 * divisor, 0, height);
			context2d.stroke();
		}
		return getImageWidth(context2d);
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

	/**
	 * wielkosc tekstu wraz z marginesem gornym
	 *
	 * @return
	 *
	 */
	public int getTextHeightOnCanvas() {
		return font.size;
	}

	/**
	 * Wysokosc znaku w px;
	 *
	 * @return
	 */
	public int getLetterHeight() {
		return font.size;
	}

	/**
	 * Szerokosc znaku w px
	 *
	 * @return
	 */
	public double getLetterWidth() {
		return letterWidth;
	}
}
