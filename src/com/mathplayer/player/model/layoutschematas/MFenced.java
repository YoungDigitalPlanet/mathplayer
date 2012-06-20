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
import com.mathplayer.player.model.signs.FenceSign;

public class MFenced extends LayoutSchemata {

	protected Size openBracketSize;
	protected Size closeBracketSize;
	protected FenceType openFenceType;
	protected FenceType closeFenceType;
	protected FenceSign openFenceSign;
	protected FenceSign closeFenceSign;

	public MFenced(Token content, FenceType openFenceType, FenceType closeFenceType){
		tokens = new Vector<Token>();
		tokens.add(content);
		this.openFenceType = openFenceType;
		this.closeFenceType = closeFenceType;
		openFenceSign = new FenceSign(openFenceType, true);
		closeFenceSign = new FenceSign(closeFenceType, false);
	}
	

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		openFenceSign.setFont(font);
		closeFenceSign.setFont(font);
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size;

		Surface canvas = createCanvas();

		size = tokens.get(0).measure(socket);
		
		openFenceSign.setHeight(size.height);
		closeFenceSign.setHeight(size.height);
		
		openBracketSize = openFenceSign.measure(socket);
		closeBracketSize = closeFenceSign.measure(socket);

		size.addLeft(openBracketSize);
		size.addLeft(closeBracketSize);
		
		return size.clone();

	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		canvas.setFont(font.clone((int) size.height).toString());
		canvas.setStrokeStyle(font.color);
		canvas.setFillStyle(font.color);

		Area openFenceArea = new Area(exactArea.x, exactArea.y, openBracketSize);		
		openFenceSign.render(canvas, openFenceArea, socket);
		
		Area contentsArea = new Area(exactArea.x + openBracketSize.width, exactArea.y, tokens.get(0).measure(socket));
		tokens.get(0).render(canvas, contentsArea, socket);

		Area closeFenceArea = new Area(exactArea.x + openBracketSize.width + contentsArea.width, exactArea.y, closeBracketSize);		
		closeFenceSign.render(canvas, closeFenceArea, socket);

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
}
