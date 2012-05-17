package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.shapes.ShapeBuilder;

import java.util.Vector;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;

public class MFenced extends LayoutSchemata {

	protected Size openBracketSize;
	protected Size closeBracketSize;
	protected FenceType openFenceType;
	protected FenceType closeFenceType;
	public static final int LINE_THICNKENSS_COEFF = 20;
	public static final int LINE_THICNKENSS_MIN = 2;
	public static final double LINE_MARGIN = 0.4d;

	public MFenced(Token content, FenceType openFenceType, FenceType closeFenceType){
		tokens = new Vector<Token>();
		tokens.add(content);
		this.openFenceType = openFenceType;
		this.closeFenceType = closeFenceType;
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size;

		Surface canvas = createCanvas();

		size = tokens.get(0).measure(socket);

		Font fenceFont = font.clone((int)size.height);
		canvas.setFont(fenceFont.toString());

		if (openFenceType == FenceType.VERTICAL_BAR){
			openBracketSize = new Size(getVerticalBarLineThickness() + font.size * LINE_MARGIN, size.height);
		} else {
			openBracketSize = new Size(canvas.measureText(FenceType.getOpenFence(openFenceType)), size.height);
		}
		if (closeFenceType == FenceType.VERTICAL_BAR){
			closeBracketSize = new Size(getVerticalBarLineThickness() + font.size * LINE_MARGIN, size.height);
		} else {
			closeBracketSize = new Size(canvas.measureText(FenceType.getCloseFence(closeFenceType)), size.height);
		}

		size.width +=  openBracketSize.width + closeBracketSize.width;
		removeCanvas(canvas);
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.setFont(font.clone((int) size.height).toString());
		if (openFenceType == FenceType.VERTICAL_BAR) {
			ShapeBuilder sb = new ShapeBuilder();
			canvas.setLineWidth(getVerticalBarLineThickness());
			sb.drawLineSegment((int) (exactArea.x + openBracketSize.width / 2), exactArea.y, (int) (exactArea.x + openBracketSize.width / 2), exactArea.y + exactArea.height);
			canvas.strokeShape(sb.build());
		} else {
			if (font.size / size.height > .75) {
				canvas.fillText(FenceType.getOpenFence(openFenceType), exactArea.x, exactArea.y + getTextOffset((int) size.height));
			} else {
				MFencedBracket mFencedBracket = new MFencedBracket(openFenceType, font.clone((int) size.height));
				mFencedBracket.render(size, true);
				double textOffset = getTextOffset((int) size.height);
				canvas.drawImage(mFencedBracket.getBracket(), exactArea.x + openBracketSize.width / 2, -(size.height - textOffset), 53 - (size.height - textOffset),
						mFencedBracket.getHeight());
			}
		}

		Area contentsArea = new Area(exactArea.x + openBracketSize.width, exactArea.y, tokens.get(0).measure(socket));
		tokens.get(0).render(canvas, contentsArea, socket);

		canvas.setFont(font.clone((int) size.height).toString());
		if (closeFenceType == FenceType.VERTICAL_BAR) {
			ShapeBuilder sb = new ShapeBuilder();
			canvas.setLineWidth(getVerticalBarLineThickness());
			sb.drawLineSegment((int) (exactArea.x + exactArea.width - closeBracketSize.width / 2), exactArea.y, (int) (exactArea.x + exactArea.width - closeBracketSize.width / 2),
					exactArea.y + exactArea.height);
			canvas.strokeShape(sb.build());
		} else {
			if (font.size / size.height > .75) {
				canvas.fillText(FenceType.getCloseFence(closeFenceType), exactArea.x + exactArea.width - closeBracketSize.width, exactArea.y + getTextOffset((int) size.height));
			} else {
				MFencedBracket mFencedBracket = new MFencedBracket(closeFenceType, font.clone((int) size.height));
				mFencedBracket.render(size, false);
				double textOffset = getTextOffset((int) size.height);
				canvas.drawImage(mFencedBracket.getBracket(), exactArea.x + exactArea.width - closeBracketSize.width/2, -(size.height - textOffset), 53 - (size.height - textOffset),
						mFencedBracket.getHeight());
			}
		}
	}

	@Override
	public String toString() {
		return "(" + tokens.get(0).toString() + ")";
	}

	@Override
	public String toMathML() {
		StringBuilder mml = new StringBuilder("<mfenced open=\"");
		mml.append(FenceType.openFenceToString(openFenceType));
		mml.append("\" close=\"");
		mml.append(FenceType.closeFenceToString(closeFenceType));
		mml.append("\">");
		mml.append(tokens.get(0).toMathML());
		mml.append("</mfenced>");
		return mml.toString();
	}

	private int getVerticalBarLineThickness(){
		int lineThickness = font.size/LINE_THICNKENSS_COEFF + LINE_THICNKENSS_MIN;
		return lineThickness;
	}
}
