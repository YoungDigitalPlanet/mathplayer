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
import com.mathplayer.player.model.tokens.MEmpty;

public class MMultiScripts extends LayoutSchemata {
	
	private Token base;
	private Token sub;
	private Token sup;
	private Token lsub;
	private Token lsup;
	private boolean drawout;
	
	public MMultiScripts(Token base, Token sub, Token sup, Token lsub, Token lsup){
		this(base, sub, sup, lsub, lsup, true);
	}
	
	public MMultiScripts(Token base, Token sub, Token sup, Token lsub, Token lsup, boolean drawout){
		if (sub instanceof MEmpty) sub = null;
		if (sup instanceof MEmpty) sup = null;
		if (lsub instanceof MEmpty) lsub = null;
		if (lsup instanceof MEmpty) lsup = null;
		
		this.base = base;
		this.sub = sub;
		this.sup = sup;
		this.lsub = lsub;
		this.lsup = lsup;
		
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
		base.setFont(font.clone());
		if (sub != null) sub.setFont(font.cloneShrunk());
		if (sup != null) sup.setFont(font.cloneShrunk());
		if (lsub != null) lsub.setFont(font.cloneShrunk());
		if (lsup != null) lsup.setFont(font.cloneShrunk());
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;
				
		size = base.measure(socket);
		
		double mSubSupWidth = 0;
		double supMiddleLineCorrection = 0;
		double subHeightCorrection = 0;
		
		if (sub != null){
			Size subSize = sub.measure(socket);
			subHeightCorrection = (drawout) ? subSize.height * (1.0d / 3.0d) : 0;
			size.addLeft(new Size(subSize.width, size.height + subHeightCorrection, size.middleLine));
			mSubSupWidth = subSize.width;
		}
		if (sup != null){
			Size supSize = sup.measure(socket);
			mSubSupWidth = Math.max(0, supSize.width-mSubSupWidth);
			supMiddleLineCorrection = (drawout) ? supSize.height / 2 : 0;
			size.addLeft(new Size(mSubSupWidth, size.height, size.middleLine + supMiddleLineCorrection));
		}
		
		double mlSubSupWidth = 0;
		double lSupMiddleLineCorrection = 0;
		double lSubHeightCorrection = 0;

		if (lsub != null){
			Size lSubSize = lsub.measure(socket);
			lSubHeightCorrection = Math.max(0, ((drawout) ? lSubSize.height * (1.0d / 3.0d) : 0) - subHeightCorrection);
			size.addLeft(new Size(lSubSize.width, size.height + lSubHeightCorrection, size.middleLine));
			mlSubSupWidth = lSubSize.width;
		}
		
		if (lsup != null){
			Size lSupSize = lsup.measure(socket);
			mlSubSupWidth = Math.max(0, lSupSize.width-mlSubSupWidth); 
			lSupMiddleLineCorrection = Math.max(0, (((drawout) ? lSupSize.height / 2 : 0) - supMiddleLineCorrection));
			size.addLeft(new Size(mlSubSupWidth, size.height, size.middleLine + lSupMiddleLineCorrection));
		}
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		Size baseTokenSize = base.measure(socket);
		double supOffset = exactArea.middleLine - baseTokenSize.middleLine;
		
		Size lSubSize = new Size();
		Size lSupSize = new Size();
		
		if (lsub != null) {
			Area lSubArea = new Area(exactArea.x, exactArea.y + supOffset + baseTokenSize.height - lsub.measure(socket).height*((drawout)?(2.0d/3.0d):1), lsub.measure(socket));
			lsub.render(canvas, lSubArea, socket);
			lSubSize = lsub.measure(socket);
		}
		
		if (lsup != null) {
			Area supArea = new Area(exactArea.x, exactArea.y, lsup.measure(socket));
			lsup.render(canvas, supArea, socket);
			lSupSize = lsup.measure(socket);
		}
		
		double maxLeftTokenWidth = Math.max(lSubSize.width, lSupSize.width);
		Area baseArea = new Area(exactArea.x + maxLeftTokenWidth, exactArea.y + ((drawout)?supOffset:0), baseTokenSize);
		base.render(canvas, baseArea, socket);
		
		if (sub != null) {
			Area subArea = new Area(exactArea.x + baseTokenSize.width + maxLeftTokenWidth, exactArea.y + supOffset + baseTokenSize.height - sub.measure(socket).height*((drawout)?(2.0d/3.0d):1), sub.measure(socket));
			sub.render(canvas, subArea, socket);
		}
		
		Token supToken = sup;
		if (supToken != null) {
			double x = exactArea.x + baseTokenSize.width + maxLeftTokenWidth;
			Size measure = supToken.measure(socket);
			Area supArea = new Area(x, exactArea.y, measure);
			supToken.render(canvas, supArea, socket);
		}
	}

	@Override
	public String toString() {
		String s = base.toString();
		if (sub != null){
			if (sub instanceof ContentToken)
				s += "_" + sub.toString();
			else
				s += "_(" + sub.toString() + ")";
		}
		if (sup != null){
			if (sup instanceof ContentToken)
				s += "^" + sup.toString();
			else
				s += "^(" + sup.toString() + ")";
		}
		if (lsub != null){
			if (lsub instanceof ContentToken)
				s += "__" + lsub.toString();
			else
				s += "__(" + lsub.toString() + ")";
		}
		if (lsup != null){
			if (lsup instanceof ContentToken)
				s += "^^" + lsup.toString();
			else
				s += "^^(" + lsup.toString() + ")";
		}
		return s;
	}

	@Override
	public String toMathML() {
		
		String mathML = "";
		mathML += "<mmultiscripts>";
		mathML += base.toMathML();
		mathML += ( (sub == null) ? "<none/>" : sub.toMathML() ); 
		mathML += ( (sup == null) ? "<none/>" : sup.toMathML() );
		mathML += "<mprescripts/>";
		mathML += ( (lsub == null) ? "<none/>" : lsub.toMathML() );
		mathML += ( (lsup == null) ? "<none/>" : lsup.toMathML() );
		mathML += "</mmultiscripts>";
		return mathML;
	}

}
