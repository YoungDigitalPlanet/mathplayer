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

public class MUnderOver extends LayoutSchemata {

	public MUnderOver(Token base, Token under, Token over){
		tokens = new Vector<Token>();
		tokens.add(base);
		tokens.add(under);
		tokens.add(over);
	}
	
	@Override
	public void setFont(Font font){
		this.font = font;
		tokens.get(0).setFont(font.clone());
		if (tokens.get(1) != null)
			tokens.get(1).setFont(font.cloneShrunk());
		if (tokens.get(2) != null)
			tokens.get(2).setFont(font.cloneShrunk());
	}
	
	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size.clone();
				
		size = tokens.get(0).measure(socket);
		if (tokens.get(1) != null){
			Size underSize = tokens.get(1).measure(socket);
			size.addBottom(underSize);
		}
		if (tokens.get(2) != null){
			Size overSize = tokens.get(2).measure(socket);
			size.addTop(overSize);
		}
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		double overOffset = exactArea.middleLine - tokens.get(0).measure(socket).middleLine;
		//if (tokens.get(2) != null)
		//	overOffset = tokens.get(2).measure(socket).height;
		
		Area baseArea = new Area(exactArea.x, exactArea.y + overOffset, exactArea.width, tokens.get(0).measure(socket).height, tokens.get(0).measure(socket).middleLine);
		tokens.get(0).render(canvas, baseArea, socket);
		if (tokens.get(1) != null) {
			Area underArea = new Area(exactArea.x, exactArea.y + overOffset + tokens.get(0).measure(socket).height, exactArea.width, tokens.get(1).measure(socket).height,  tokens.get(1).measure(socket).middleLine);
			tokens.get(1).render(canvas, underArea, socket);
		}
		if (tokens.get(2) != null) {
			Area overArea = new Area(exactArea.x, exactArea.y, exactArea.width, tokens.get(2).measure(socket).height,  tokens.get(2).measure(socket).middleLine);
			tokens.get(2).render(canvas, overArea, socket);
		}

	}


	@Override
	public String toString() {
		String s = tokens.get(0).toString();
		if (tokens.get(1) != null){
			if (tokens.get(1) instanceof ContentToken)
				s += "__" + tokens.get(1).toString();
			else
				s += "__(" + tokens.get(1).toString() + ")";
		}
		if (tokens.get(2) != null){
			if (tokens.get(2) instanceof ContentToken)
				s += "^^" + tokens.get(2).toString();
			else
				s += "^^(" + tokens.get(2).toString() + ")";
		}
		return s;
	}

	@Override
	public String toMathML() {
		if (tokens.get(1) == null){
			return "<mover>" + tokens.get(0).toMathML() + tokens.get(2).toMathML() + "</mover>";
		} else if (tokens.get(2) == null){
			return "<munder>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + "</munder>";
		}
		return "<munderover>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + tokens.get(2).toMathML() + "</munderover>";
	}

}
