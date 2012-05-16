package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;

import java.util.Vector;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.ContentToken;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.signs.FractionSign;

public class MFraction extends LayoutSchemata {

	protected FractionSign fractionSign;
	protected final double MARGIN = 0.0d;

	public MFraction(Token numerator, Token denominator){
		tokens = new Vector<Token>();
		tokens.add(numerator);
		tokens.add(denominator);
		fractionSign = new FractionSign();
	}

	@Override
	public void setFont(Font font){
		super.setFont(font);
		this.font = font;
		fractionSign.setFont(font);
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size.clone();

		size = tokens.get(0).measure(socket).clone();
		size.addBottom(fractionSign.measure(socket));
		size.addBottom(tokens.get(1).measure(socket));
		size.width += 2*font.size*MARGIN;
		size.middleLine = tokens.get(0).measure(socket).height + fractionSign.measure(socket).height/2;

		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		Area next = exactArea.clone();

		next.setSize(size.width, tokens.get(0).measure(socket).height, tokens.get(0).measure(socket).middleLine);
		tokens.get(0).render(canvas, next, socket);

		next.y += tokens.get(0).measure(socket).height;
		next.setSize(size.width, fractionSign.measure(socket).height, 0);
		fractionSign.render(canvas, next, socket);

		next.y += fractionSign.measure(socket).height;
		next.setSize(size.width, tokens.get(1).measure(socket).height, tokens.get(1).measure(socket).middleLine);
		tokens.get(1).render(canvas, next, socket);
	}

	@Override
	public String toString() {
		String n;
		if (tokens.get(0) instanceof ContentToken)
			n = tokens.get(0).toString();
		else
			n = "(" + tokens.get(0).toString() + ")";

		String d;
		if (tokens.get(1) instanceof ContentToken)
			d = tokens.get(1).toString();
		else
			d = "(" + tokens.get(1).toString() + ")";

		return n + "/" + d;
	}

	@Override
	public String toMathML() {
		return "<mfrac>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + "</mfrac>";
	}

}
