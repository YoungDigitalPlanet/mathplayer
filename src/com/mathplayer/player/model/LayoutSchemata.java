package com.mathplayer.player.model;

import java.util.Vector;

import com.mathplayer.player.geom.Font;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.layoutschematas.MSubSup;

public abstract class LayoutSchemata extends Token {

	protected Vector<Token> tokens;

	@Override
	public void setSubSup(boolean isSubSup) {
		super.setSubSup(isSubSup);
		for (Token t : tokens){
			t.setSubSup(isSubSup);
		}
	}
	
	@Override
	public void setFont(Font font) {
		this.font = font.clone();
		for (Token t : tokens){
			t.setFont(font.clone());
		}		
	}

	public int getChildTokensCount(){
		return tokens.size();
	}
	
	public Token getChildTokenAt(int i){
		return tokens.get(i);
	}

	public void reset(){
		size = null;
		for (Token t : tokens){
			if (t != null)
				t.reset();
		}		
	}
	
	public void updateCursor(InteractionSocket socket){
		for (Token t : tokens){
			if (t instanceof LayoutSchemata)
				((LayoutSchemata)t).updateCursor(socket);
		}	
	}

	public boolean containsToken(Class<?> class1, int fromDepth) {
		for (Token t : tokens){
			if (t == null)
				continue;
			if (fromDepth == 0  &&  class1.getName().equals(t.getClass().getName()))
				return true;
			if (t instanceof LayoutSchemata){
				if (fromDepth > 0)
					fromDepth--;
				return ((LayoutSchemata)t).containsToken(class1, fromDepth);
			}
		}
		return false;
	}
}
