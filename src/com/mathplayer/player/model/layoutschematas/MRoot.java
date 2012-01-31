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
import com.mathplayer.player.model.signs.RootSign;
import com.mathplayer.player.model.tokens.MNumber;

public class MRoot extends LayoutSchemata {

	protected RootSign rootSign;
	
	public MRoot(Token base, Token index){
		tokens = new Vector<Token>();
		tokens.add(base);
		tokens.add(index);
		rootSign = new RootSign();
	}
	
	@Override
	public void setFont(Font font){
		this.font = font;
		tokens.get(0).setFont(font);
		if (tokens.get(1) != null)
			tokens.get(1).setFont(font.cloneShrunk());
		rootSign.setFont(font);
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;
		
		size = tokens.get(0).measure(socket);
		
		size.width += rootSign.getBetweenMargin() + rootSign.getOverhangRightMargin();
		
		if (tokens.get(1) == null  ||  rootSign.getOverhangLeftMargin() > tokens.get(1).measure(socket).width){
			size.width += rootSign.getOverhangLeftMargin();
		} else {
			size.width += tokens.get(1).measure(socket).width;
		}
		
		if (tokens.get(1) == null  ||  rootSign.getOverMargin() > tokens.get(1).measure(socket).height - tokens.get(0).measure(socket).height/2){
			size.height += rootSign.getOverMargin();
			size.middleLine += rootSign.getOverMargin();
		} else {
			size.height += tokens.get(1).measure(socket).height - tokens.get(0).measure(socket).height/2;
			size.middleLine += tokens.get(1).measure(socket).height - tokens.get(0).measure(socket).height/2;
		}
				
		return size;
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		double indexWidth = rootSign.getOverhangLeftMargin();
		if (tokens.get(1) != null  &&  indexWidth < tokens.get(1).measure(socket).width)
			indexWidth = tokens.get(1).measure(socket).width;
		
		Area baseArea = new Area(exactArea.x + indexWidth + rootSign.getBetweenMargin(), exactArea.y + rootSign.getOverMargin(), tokens.get(0).measure(socket));
		tokens.get(0).render(canvas, baseArea, socket);
		
		if (tokens.get(1) != null){
			Area indexArea = exactArea.clone();
			
			if (rootSign.getOverhangLeftMargin() > tokens.get(1).measure(socket).width){
				indexArea.x += rootSign.getOverhangLeftMargin() - tokens.get(1).measure(socket).width;
			} else {
				indexArea.x += 0;
			}
			
			if (rootSign.getOverMargin() > tokens.get(1).measure(socket).height - tokens.get(0).measure(socket).height/2){
				indexArea.y = rootSign.getOverMargin() + tokens.get(0).measure(socket).height/2 - tokens.get(1).measure(socket).height;
			} else {
				indexArea.y = 0;
			}
			
			indexArea.setSize(tokens.get(1).measure(socket));
			
			tokens.get(1).render(canvas, indexArea, socket);
		}
		
		rootSign.render(canvas, baseArea, socket);

	}

	@Override
	public String toString() {
		String s = tokens.get(0).toString();
		if (!(tokens.get(0) instanceof MNumber))
			s = "(" + s + ")";
		
		if (tokens.get(1) != null){
			s += "$";
			if (tokens.get(0) instanceof ContentToken)
				s += tokens.get(1).toString();
			else
				s = "(" + tokens.get(1).toString() + ")";
		}
		
		return s;
	}

	@Override
	public String toMathML() {
		if (tokens.get(1) != null)
			return "<mroot>" + tokens.get(0).toMathML() + tokens.get(1).toMathML() + "</mroot>";
		return "<msqrt>" + tokens.get(1).toMathML() + "</msqrt>";
	}

}
