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

	private final Token base;
	private final Token under;
	private final Token over;

	public MUnderOver(Token base, Token under, Token over){
		this.base = base;
		this.under = under;
		this.over = over;
		
		tokens = new Vector<Token>();
		tokens.add(base);
		tokens.add(under);
		tokens.add(over);
	}
	
	@Override
	public void setFont(Font font){
		this.font = font;
		base.setFont(font.clone());
		if (under != null)
			under.setFont(font.cloneShrunk());
		if (over != null)
			over.setFont(font.cloneShrunk());
	}
	
	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size.clone();
				
		size = base.measure(socket);
		if (under != null){
			Size underSize = under.measure(socket);
			size.addBottom(underSize);
		}
		if (over != null){
			Size overSize = over.measure(socket);
			size.addTop(overSize);
		}
		size.width+=6;
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		Size baseTokenSize = base.measure(socket);
		double overOffset = exactArea.middleLine - baseTokenSize.middleLine;
		//if (over != null)
		//	overOffset = over.measure(socket).height;
		
		Area baseArea = new Area(exactArea.x, exactArea.y + overOffset, exactArea.width, baseTokenSize.height, baseTokenSize.middleLine);
		base.render(canvas, baseArea, socket);
		if (under != null) {
			Size underTokenSize = under.measure(socket);
			Area underArea = new Area(exactArea.x, exactArea.y + overOffset + baseTokenSize.height, exactArea.width, underTokenSize.height,  underTokenSize.middleLine);
			under.render(canvas, underArea, socket);
		}
		if (over != null) {
			Size overTokenSize = over.measure(socket);
			Area overArea = new Area(exactArea.x, exactArea.y, exactArea.width, overTokenSize.height,  overTokenSize.middleLine);
			over.render(canvas, overArea, socket);
		}

	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(base.toString());
		appendTokenStringIfNotNull("__", under, sb);
		appendTokenStringIfNotNull("^^", over, sb);
		return sb.toString();
	}
	
	private void appendTokenStringIfNotNull(String prefix, Token token, StringBuilder sb){
		if(token != null){
			boolean isContentToken = token instanceof ContentToken;
			sb.append(prefix);
			if(isContentToken){
				sb.append("(");
			}
			sb.append(token.toString());
			if(isContentToken){
				sb.append(")");
			}
		}
	}

	@Override
	public String toMathML() {
		if (under == null){
			return "<mover>" + base.toMathML() + over.toMathML() + "</mover>";
		} else if (over == null){
			return "<munder>" + base.toMathML() + under.toMathML() + "</munder>";
		}
		return "<munderover>" + base.toMathML() + under.toMathML() + over.toMathML() + "</munderover>";
	}

}
