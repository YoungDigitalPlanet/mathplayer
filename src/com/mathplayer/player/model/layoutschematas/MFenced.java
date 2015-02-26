package com.mathplayer.player.model.layoutschematas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.*;
import com.mathplayer.player.model.signs.FenceSign;
import java.util.Vector;

public class MFenced extends LayoutSchemata {

	protected Size openBracketSize;
	protected Size closeBracketSize;
	protected FenceType openFenceType;
	protected FenceType closeFenceType;
	protected FenceSign openFenceSign;
	protected FenceSign closeFenceSign;

	public MFenced(Token content, FenceType openFenceType, FenceType closeFenceType) {
		tokens = new Vector<>();
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
		if (size != null) {
			return size;
		}

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
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		Context2d context2d = canvas.getContext2d();
		context2d.setFont(font.clone((int) size.height).toString());

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
