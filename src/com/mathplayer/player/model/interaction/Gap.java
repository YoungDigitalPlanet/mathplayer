package com.mathplayer.player.model.interaction;

import gwt.g2d.client.graphics.Color;
import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.InteractionToken;

public class Gap extends InteractionToken {

	public Gap(){
		
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;
		
		if (socket.isEditorMode())
			size = new Size(font.size, font.size, font.size/2);
		else
			size = new Size(socket.getTextBoxWidth(), socket.getTextBoxHeight(), socket.getTextBoxHeight()/2);
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		if (socket.isEditorMode()){
			canvas.setFillStyle(new Color(0, 255, 255));
			canvas.fillRectangle(exactArea.x, exactArea.y, exactArea.width, exactArea.height);
		} else {
			socket.addGap((int)exactArea.x, (int)exactArea.y);
		}
	}

	@Override
	public String toString() {
		return " ";
	}

	@Override
	public String toMathML() {
		return "<gap/>";
	}

}
