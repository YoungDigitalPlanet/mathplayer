package com.mathplayer.player.model.signs;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.CanvasPixelArray;
import gwt.g2d.client.graphics.canvas.Context;
import gwt.g2d.client.graphics.canvas.ImageData;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Sign;
import com.mathplayer.player.model.layoutschematas.FenceType;

public class FenceSign extends Sign {

	private FenceType fenceType;
	private boolean open;
	private double height;
	
	private static final int LINE_THICNKENSS_COEFF = 20;
	private static final int LINE_THICNKENSS_MIN = 1;
	private static final double LINE_MARGIN = 0.4d;
	private static final double CURLY_MARGIN = 0.1d;
	private static final double ANGLE_MARGIN = 0.1d;

	public FenceSign(FenceType fenceType, boolean open) {
		this.fenceType = fenceType;
		this.open = open;
	}
	
	public void setHeight(double height){
		this.height = height;
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size; 
		switch (fenceType) {
			case ROUND:
				size = new Size(Math.ceil(getRoundBracketWidth()), height);
				break;
			case CURLY:
				size = new Size(Math.ceil(getCurlyBracketWidth() + getCurlyBracketMargin()), height);
				break;
			case SQUARE:
				size = new Size(Math.ceil(getSquareBracketWidth()), height);
				break;
			case ANGLE:
				size = new Size(Math.ceil(getAngleBracketWidth() + getAngleBracketMargin()), height);
				break;
			case VERTICAL_BAR:
				size = new Size(Math.ceil(getVerticalBarBracketWidth()), height);
				break;
	
			default:
				break;
		}
		return size;
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		switch (fenceType) {
			case ROUND:
				drawRoundBracket(canvas.getContext());
				break;
			case CURLY:
				drawCurlyBracket(canvas.getContext());
				break;
			case SQUARE:
				drawSquareBracket(canvas.getContext());
				break;
			case ANGLE:
				drawAngleBracket(canvas.getContext());
				break;
			case VERTICAL_BAR:
				drawVerticalBarBracket(canvas.getContext());
				break;
			default:
				break;
		}
	}
	
	//----------------------------- ROUND -----------------------------

	private void drawRoundBracket(Context context2d) {
		double vDivisor = getRoundBracketVerticalDivisor();
		
		double sx = exactArea.x;
		double sy = exactArea.y;
		
		if (open){	
			double cp1x = getRoundBracketCp1X();
			double cp2y = getRoundBracketCp2Y();	
			double width = getRoundBracketWidth();
			for (int x = 0; x < 2 ; x++) {
				context2d.beginPath();
				context2d.moveTo(sx + width, sy);
				context2d.bezierCurveTo(sx + width, sy, sx  + width - cp1x - x, sy + 50 * vDivisor, sx + width, sy + cp2y);
				context2d.stroke();
			}
		} else {	
			double cp1x = getRoundBracketCp1X();
			double cp2y = getRoundBracketCp2Y();	
			for (int x = 0; x < 2 ; x++) {
				context2d.beginPath();
				context2d.moveTo(sx, sy);
				context2d.bezierCurveTo(sx, sy, sx + cp1x -x, sy + 50 * vDivisor, sx, sy + cp2y);
				context2d.stroke();
			}
		}
				
	}
	
	private double getRoundBracketCp1X(){
		double coeff = Math.pow(font.size/height, .4d);
		return 40 * coeff * getRoundBracketHorizontalDivisor();
	}
	
	private double getRoundBracketCp2Y(){
		return 108 * getRoundBracketVerticalDivisor();
	}
	
	private double getRoundBracketVerticalDivisor(){
		return height / 108f;
	}
	
	private double getRoundBracketHorizontalDivisor(){
		return height / 108f;
	}
	
	private double getRoundBracketWidth(){
		// Algorytm de Casteljau wyznaczania wspolrzednej punktu na krzywej Beziera
		int p0x = 0;
		int p1x = (int)Math.ceil(getRoundBracketCp1X());
		int p2x = 0;

		int p0x1 = (p0x + p1x)/2;
		int p1x1 = (p1x + p2x)/2;

		int p0x2 = (p0x1 + p1x1)/2;
				
		return p0x2+1;		
	}
	
	//----------------------------- CURLY -----------------------------

	/**
	 * rysuje nawias klamrowy {
	 *
	 * @param context2d
	 *            na czym ma rysowac
	 * @return szerokosc znaku
	 */
	private void drawCurlyBracket(Context context2d) {
		double divisor = getCurlyBracketDivisor(); // 108 bazowa wielkosc klamry
		double width = getCurlyBracketWidth();
		double margin = getCurlyBracketMargin();

		double sx = exactArea.x;
		double sy = exactArea.y;
		
		if (open){
			sx += margin;
			context2d.beginPath();
			context2d.moveTo(sx + 20 * divisor, sy + 1.00 * divisor);
			context2d.bezierCurveTo(sx + 20.00 * divisor, sy + 1.00 * divisor, sx + 10.00 * divisor, sy + 2.00 * divisor, sx + 10.00 * divisor, sy + 15.00 * divisor);
			context2d.bezierCurveTo(sx + 10.00 * divisor, sy + 24.00 * divisor, sx + 10.00 * divisor, sy + 30.00 * divisor, sx + 10.00 * divisor, sy + 35.00 * divisor);
			context2d.bezierCurveTo(sx + 10.00 * divisor, sy + 47.00 * divisor, sx + 10.00 * divisor, sy + 53.00 * divisor, sx + 0.00 * divisor, sy + 54.00 * divisor);
			context2d.bezierCurveTo(sx + 10.00 * divisor, sy + 55 * divisor, sx + 10.00 * divisor, sy + 61 * divisor, sx + 10.00 * divisor, sy + 93 * divisor);
			context2d.bezierCurveTo(sx + 10.00 * divisor,sy +  93 * divisor, sx + 10.00 * divisor, sy + 107 * divisor, sx + 20.00 * divisor, sy + 108.00 * divisor);
			context2d.stroke();
		} else {
			width += sx;
			context2d.beginPath();
			context2d.moveTo(width - 20 * divisor, sy + 1.00 * divisor);
			context2d.bezierCurveTo(width - 20.00 * divisor, sy + 1.00 * divisor, width - 10.00 * divisor, sy + 2.00 * divisor, width - 10.00 * divisor, sy + 15.00 * divisor);
			context2d.bezierCurveTo(width - 10.00 * divisor, sy + 24.00 * divisor, width - 10.00 * divisor, sy + 30.00 * divisor, width - 10.00 * divisor, sy + 35.00 * divisor);
			context2d.bezierCurveTo(width - 10.00 * divisor, sy + 47.00 * divisor, width - 10.00 * divisor, sy + 53.00 * divisor, width - 0.00 * divisor, sy + 54.00 * divisor);
			context2d.bezierCurveTo(width - 10.00 * divisor, sy + 55 * divisor, width - 10.00 * divisor, sy + 61 * divisor, width - 10.00 * divisor, sy + 93 * divisor);
			context2d.bezierCurveTo(width - 10.00 * divisor, sy + 93 * divisor, width - 10.00 * divisor, sy + 107 * divisor, width - 20.00 * divisor, sy + 108.00 * divisor);
			context2d.stroke();
		}
	}
	
	private double getCurlyBracketWidth(){
		return 20 * getCurlyBracketDivisor() ;
	}
	
	private double getCurlyBracketMargin(){
		return  CURLY_MARGIN * font.size;
	}
	
	private double getCurlyBracketDivisor(){
		return height / 108f;
	}

	//----------------------------- SQUARE -----------------------------

	private void drawSquareBracket(Context context) {
		double divisor = getSquareBracketDivisor(); // 108 bazowa wielkosc klamry
		int width = (int)getSquareBracketWidth();
		int height = (int)Math.round( 108 * divisor);

		int sx = (int)exactArea.x;
		int sy = (int)exactArea.y;
		
		try {
			ImageData imageData = context.createImageData(width, height);
			CanvasPixelArray cpa = imageData.getPixelArray();
			drawHorizontalLine(cpa, width, 0, width-1, 0, font.color);
			drawHorizontalLine(cpa, width, 0, width-1, height-1, font.color);
			int dx = ( (open) ? 0 : (int)width-2 );
			drawVerticalLine(cpa, width, 1, height-2, dx, font.color);
			drawVerticalLine(cpa, width, 1, height-2, dx+1, font.color);
			context.putImageData(imageData, sx, sy, 0, 0, width, height);
			
		} catch (Exception e) {
			// implementation for IE
			context.beginPath();
			context.moveTo( sx + ( (open) ? (int)width-1 : 1.0d ) , sy + 0.00);
			context.lineTo( sx + ( (open) ? 1.0d : (int)width-1 ) , sy + 0);
			context.lineTo( sx + ( (open) ? 1.0d : (int)width-1 ) , sy + (int)height);
			context.lineTo( sx + ( (open) ? (int)width : 1.0d ) , sy + (int)height);
			context.stroke();
		}
		
	}
	
	private double getSquareBracketWidth() {
		double coeff = Math.pow(font.size/height, .8d);
		return 40 * coeff * getSquareBracketDivisor();
	}
	
	private double getSquareBracketDivisor() {
		return height / 108f;
	}

	//----------------------------- ANGLE -----------------------------

	private void drawAngleBracket(Context context) {
		double divisor = getAngleBracketDivisor(); // 108 bazowa wielkosc klamry
		double width = getAngleBracketWidth();
		double height = 108 * divisor;

		double sx = exactArea.x;
		double sy = exactArea.y;
		
		if (open){
			sx += getAngleBracketMargin();
			context.beginPath();
			context.moveTo(sx + width, sy + 0.00);
			context.lineTo(sx + 0, sy + height / 2);
			context.lineTo(sx + width, sy + height);
			context.stroke();
		} else {
			context.beginPath();
			context.moveTo(sx + 0, sy + 0.00);
			context.lineTo(sx + width, sy + height / 2);
			context.lineTo(sx + 0, sy + height);
			context.stroke();
		}
	}
	
	private double getAngleBracketWidth(){
		double coeff = Math.pow(font.size/height, .5d);
		return 30 * coeff * getAngleBracketDivisor();
	}
	private double getAngleBracketDivisor() {
		return height / 108f;
	}
	
	private double getAngleBracketMargin(){
		return  ANGLE_MARGIN * font.size;
	}

	//----------------------------- VERTICAL BAR -----------------------------

	private void drawVerticalBarBracket(Context context) {
		double width = getVerticalBarBracketWidth();

		int sx = (int)(exactArea.x + width/2);
		int sy = (int)exactArea.y;

		int lineThickness = getVerticalBarLineThickness();
		
		for (int i = 0 ; i < lineThickness ; i ++){
			context.beginPath();
			context.moveTo( sx + i , sy + 0.00);
			context.lineTo( sx + i , sy + 0);
			context.lineTo( sx + i , sy + (int)height);
			context.lineTo( sx + i , sy + (int)height);
			context.stroke();
		}
		
	}
	
	private double getVerticalBarBracketWidth(){
		return getVerticalBarLineThickness() + font.size * LINE_MARGIN;
	}
	

	private int getVerticalBarLineThickness(){
		int lineThickness = font.size/LINE_THICNKENSS_COEFF + LINE_THICNKENSS_MIN;
		return lineThickness;
	}

	//----------------------------- UTILS -----------------------------
	
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
}
