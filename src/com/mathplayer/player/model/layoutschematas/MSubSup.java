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

public class MSubSup extends LayoutSchemata {
	
	private boolean drawout;
	
	public MSubSup(Token base, Token sub, Token sup, boolean drawout){
		tokens = new Vector<Token>();
		tokens.add(base);
		tokens.add(sub);
		tokens.add(sup);
		this.drawout = drawout;
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
			return size;
				
		size = tokens.get(0).measure(socket);
		
		double subsupWidth = 0;
		
		if (tokens.get(1) != null){
			Size subSize = tokens.get(1).measure(socket);
			size.addLeft(new Size(subSize.width, size.height + ((drawout)?subSize.height/2:0), size.middleLine));
			subsupWidth = subSize.width;
		}
		if (tokens.get(2) != null){
			Size supSize = tokens.get(2).measure(socket);
			subsupWidth = Math.max(0, supSize.width-subsupWidth); 
			size.addLeft(new Size(subsupWidth, size.height + ((drawout)?supSize.height/2:0), size.middleLine + ((drawout)?supSize.height/2:0)));
		}
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		double supOffset = exactArea.middleLine - tokens.get(0).measure(socket).middleLine;;
		//if (tokens.get(2) != null)
		//	supOffset = tokens.get(2).measure(socket).height/2;
		
		//Area baseArea = exactArea.clone();
		Area baseArea = new Area(exactArea.x, exactArea.y + ((drawout)?supOffset:0), tokens.get(0).measure(socket));
		tokens.get(0).render(canvas, baseArea, socket);
		if (tokens.get(1) != null) {
			Area subArea = new Area(exactArea.x + tokens.get(0).measure(socket).width, exactArea.y + supOffset + tokens.get(0).measure(socket).height - tokens.get(1).measure(socket).height/((drawout)?2:1), tokens.get(1).measure(socket));
			tokens.get(1).render(canvas, subArea, socket);
		}
		if (tokens.get(2) != null) {
			Area supArea = new Area(exactArea.x + tokens.get(0).measure(socket).width, exactArea.y, tokens.get(2).measure(socket));
			tokens.get(2).render(canvas, supArea, socket);
		}

	}

	@Override
	public String toString() {
		String s = tokens.get(0).toString();
		if (tokens.get(1) != null){
			if (tokens.get(1) instanceof ContentToken)
				s += "_" + tokens.get(1).toString();
			else
				s += "_(" + tokens.get(1).toString() + ")";
		}
		if (tokens.get(2) != null){
			if (tokens.get(2) instanceof ContentToken)
				s += "^" + tokens.get(2).toString();
			else
				s += "^(" + tokens.get(2).toString() + ")";
		}
		return s;
	}

	@Override
	public String toMathML() {
		if (tokens.get(1) == null){
			return "<msup>" + tokens.get(0).toMathML() + tokens.get(2).toMathML() + "</msup>";
		} else if (tokens.get(2) == null){
			return "<msub>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + "</msub>";
		}
		return "<msubsup>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + tokens.get(2).toMathML() + "</msubsup>";
	}

}
