package com.mathplayer.player.model.layoutschematas;

import eu.ydp.gwtutil.client.debug.logger.Debug;
import gwt.g2d.client.graphics.Surface;

import java.util.Vector;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.ContentToken;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.tokens.MEmpty;

public class MMultiScripts extends LayoutSchemata {
	
	private boolean drawout;
	
	public MMultiScripts(Token base, Token sub, Token sup, Token lsub, Token lsup){
		this(base, sub, sup, lsub, lsup, true);
	}
	
	public MMultiScripts(Token base, Token sub, Token sup, Token lsub, Token lsup, boolean drawout){
		if (sub instanceof MEmpty) sub = null;
		if (sup instanceof MEmpty) sup = null;
		if (lsub instanceof MEmpty) lsub = null;
		if (lsup instanceof MEmpty) lsup = null;
		
		tokens = new Vector<Token>();
		tokens.add(base);
		tokens.add(sub);
		tokens.add(sup);
		tokens.add(lsub);
		tokens.add(lsup);
		
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
		if (tokens.get(3) != null)
			tokens.get(3).setFont(font.cloneShrunk());
		if (tokens.get(4) != null)
			tokens.get(4).setFont(font.cloneShrunk());
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;
				
		size = tokens.get(0).measure(socket);
		
		double mSubSupWidth = 0;
		double supMiddleLineCorrection = 0;
		double subHeightCorrection = 0;
		
		if (tokens.get(1) != null){ // sub
			Size subSize = tokens.get(1).measure(socket);
			subHeightCorrection = (drawout) ? subSize.height * (1.0d / 3.0d) : 0;
			size.addLeft(new Size(subSize.width, size.height + subHeightCorrection, size.middleLine));
			mSubSupWidth = subSize.width;
		}
		if (tokens.get(2) != null){ // sup
			Size supSize = tokens.get(2).measure(socket);
			mSubSupWidth = Math.max(0, supSize.width-mSubSupWidth);
			supMiddleLineCorrection = (drawout) ? supSize.height / 2 : 0;
			size.addLeft(new Size(mSubSupWidth, size.height, size.middleLine + supMiddleLineCorrection));
		}
		
		double mlSubSupWidth = 0;
		double lSupMiddleLineCorrection = 0;
		double lSubHeightCorrection = 0;

		if (tokens.get(3) != null){ // lsub
			Size lSubSize = tokens.get(3).measure(socket);
			lSubHeightCorrection = Math.max(0, ((drawout) ? lSubSize.height * (1.0d / 3.0d) : 0) - subHeightCorrection);
			size.addLeft(new Size(lSubSize.width, size.height + lSubHeightCorrection, size.middleLine));
			mlSubSupWidth = lSubSize.width;
		}
		
		if (tokens.get(4) != null){ // lsup
			Size lSupSize = tokens.get(4).measure(socket);
			mlSubSupWidth = Math.max(0, lSupSize.width-mlSubSupWidth); 
			lSupMiddleLineCorrection = Math.max(0, (((drawout) ? lSupSize.height / 2 : 0) - supMiddleLineCorrection));
			size.addLeft(new Size(mlSubSupWidth, size.height, size.middleLine + lSupMiddleLineCorrection));
		}
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		double supOffset = exactArea.middleLine - tokens.get(0).measure(socket).middleLine;
		
		Size lSubSize = new Size();
		Size lSupSize = new Size();
		
		if (tokens.get(3) != null) { // lsub
			Area lSubArea = new Area(exactArea.x, exactArea.y + supOffset + tokens.get(0).measure(socket).height - tokens.get(3).measure(socket).height*((drawout)?(2.0d/3.0d):1), tokens.get(3).measure(socket));
			tokens.get(3).render(canvas, lSubArea, socket);
			lSubSize = tokens.get(3).measure(socket);
		}
		
		if (tokens.get(4) != null) { // lsup
			Area supArea = new Area(exactArea.x, exactArea.y, tokens.get(4).measure(socket));
			tokens.get(4).render(canvas, supArea, socket);
			lSupSize = tokens.get(4).measure(socket);
		}
		
		Area baseArea = new Area(exactArea.x + Math.max(lSubSize.width, lSupSize.width), exactArea.y + ((drawout)?supOffset:0), tokens.get(0).measure(socket));
		//Area baseArea = new Area(exactArea.x, exactArea.y, tokens.get(0).measure(socket));
		tokens.get(0).render(canvas, baseArea, socket);
		
		if (tokens.get(1) != null) { // sub
			Area subArea = new Area(exactArea.x + tokens.get(0).measure(socket).width + Math.max(lSubSize.width, lSupSize.width), exactArea.y + supOffset + tokens.get(0).measure(socket).height - tokens.get(1).measure(socket).height*((drawout)?(2.0d/3.0d):1), tokens.get(1).measure(socket));
			tokens.get(1).render(canvas, subArea, socket);
		}
		
		if (tokens.get(2) != null) { // sup
			Area supArea = new Area(exactArea.x + tokens.get(0).measure(socket).width + Math.max(lSubSize.width, lSupSize.width), exactArea.y, tokens.get(2).measure(socket));
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
		if (tokens.get(3) != null){
			if (tokens.get(3) instanceof ContentToken)
				s += "__" + tokens.get(3).toString();
			else
				s += "__(" + tokens.get(3).toString() + ")";
		}
		if (tokens.get(4) != null){
			if (tokens.get(4) instanceof ContentToken)
				s += "^^" + tokens.get(4).toString();
			else
				s += "^^(" + tokens.get(4).toString() + ")";
		}
		return s;
	}

	@Override
	public String toMathML() {
		
		String mathML = "";
		mathML += "<mmultiscripts>";
		mathML += tokens.get(0).toMathML();
		mathML += ( (tokens.get(1) == null) ? "<none/>" : tokens.get(1).toMathML() ); 
		mathML += ( (tokens.get(2) == null) ? "<none/>" : tokens.get(2).toMathML() );
		mathML += "<mprescripts/>";
		mathML += ( (tokens.get(3) == null) ? "<none/>" : tokens.get(3).toMathML() );
		mathML += ( (tokens.get(4) == null) ? "<none/>" : tokens.get(4).toMathML() );
		mathML += "</mmultiscripts>";
		return mathML;
	}

}
