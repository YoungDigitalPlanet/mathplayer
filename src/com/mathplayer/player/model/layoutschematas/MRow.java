package com.mathplayer.player.model.layoutschematas;

import gwt.g2d.client.graphics.Surface;

import java.util.Vector;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.interaction.EmptySpace;

public class MRow extends LayoutSchemata {

	protected int cursor;
	
	public MRow(Vector<Token> tokens){
		this.tokens = tokens;
		cursor = -1;
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size;
				
		size = new Size();
		
		for (Token t : tokens){
			size.addLeft( t.measure(socket) );
		}
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		Area next = exactArea.clone();
		
		for (Token t : tokens){
			next.setSize(t.measure(socket).width, exactArea.height, exactArea.middleLine);
			t.render(canvas, next, socket);
			next.x += t.measure(socket).width;
		}

		if (cursor != -1){
			socket.setCursorPosition(getCursorPosition(socket));
		}

	}

	@Override
	public String toString() {
		String s = "";
		for (Token t : tokens)
			s += t.toString();
		return s;
	}

	@Override
	public String toMathML() {
		String s = "<mrow>";
		for (Token t : tokens)
			s += t.toMathML();
		s += "</mrow>";
		return s;
	}
	
	// ---------------------- ROW ---------------------------
	
	public int getCursor(){
		return cursor;
	}
	
	public void setCursor(int cursor){
		this.cursor = cursor;
		if (tokens.size() == 1  &&  tokens.get(0) instanceof EmptySpace  &&  cursor == 1){
			this.cursor = 0;
		}
	}
	
	public Area getCursorPosition(InteractionSocket socket){
		Area cursorArea = new Area(exactArea.x, exactArea.y, 1, exactArea.height);
		for (int i = 0 ; i < tokens.size()  &&  i < cursor ;  i ++){
			cursorArea.x += tokens.get(i).measure(socket).width;
		}
		return cursorArea;
	}
	
	public void addChildToken(Token t){
		Vector<Token> ts = new Vector<Token>();
		ts.add(t);
		addChildToken(ts);
	}
	
	public void addChildToken(Vector<Token> ts){
		if (tokens.size() == 1  &&  tokens.get(0) instanceof EmptySpace){
			tokens.remove(0);
			cursor = 0;
		}
		for (Token t : ts){
			if (cursor >= 0)
				tokens.insertElementAt(t, cursor);
			else
				tokens.add(t);
			cursor++;
		}
	}
	
	public void removeChildTokenAtCursor(int offset){
		if (cursor == -1  ||  cursor-1+offset < 0)
			return;
		
		if (cursor-1+offset < tokens.size())
			tokens.remove(cursor-1+offset);
		else
			tokens.remove(tokens.size()-1);
		
		if (tokens.size() == 0){
			EmptySpace eSpace = new EmptySpace();
			tokens.add(eSpace);
			cursor = 0;
		}
		if (cursor > tokens.size())
			cursor = tokens.size();
		else if (offset == 0  &&  tokens.size() != 1  &&  !(tokens.get(0) instanceof EmptySpace))
			cursor--;
	}

	public void updateCursor(InteractionSocket socket){
		if (cursor != -1){
			socket.setCursorPosition(getCursorPosition(socket));
		} else {
			super.updateCursor(socket);
		}	
	}
}
