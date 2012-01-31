package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;

import java.util.Vector;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.signs.VectorSign;

public class MVector extends LayoutSchemata {

	protected VectorSign vectorSign;
	
	public MVector(Token base, boolean showArrow){
		tokens = new Vector<Token>();
		tokens.add(base);
		vectorSign = new VectorSign(showArrow);
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		vectorSign.setFont(font);
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size.clone();
		
		size = tokens.get(0).measure(socket);
		size.addTop( vectorSign.measure(socket) );
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		double overOffset = exactArea.middleLine - tokens.get(0).measure(socket).middleLine;
		
		Area baseArea = new Area(exactArea.x, exactArea.y + overOffset, exactArea.width, tokens.get(0).measure(socket).height, tokens.get(0).measure(socket).middleLine);
		tokens.get(0).render(canvas, baseArea, socket);
		
		Area vectorSignArea = new Area(exactArea.x, exactArea.y, exactArea.width, overOffset);
		vectorSign.render(canvas, vectorSignArea, socket);
	}

	@Override
	public String toString() {
		return tokens.get(0).toString();
	}

	@Override
	public String toMathML() {
		return "<mvector>" + tokens.get(0).toMathML() + "</mvector>";
	}

}
