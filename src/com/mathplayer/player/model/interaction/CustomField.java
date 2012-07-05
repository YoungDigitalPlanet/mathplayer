package com.mathplayer.player.model.interaction;

import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.InteractionToken;

public class CustomField extends InteractionToken {

	private String type;

	public CustomField(String type){
		this.type = type;
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
	
		if (size != null)
			return size;

		size = new Size(socket.getCustomFieldWidth(type), socket.getCustomFieldHeight(type), socket.getCustomFieldHeight(type)/2);
		
		return size.clone();
	}

	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		
		socket.addCustomField((int)exactArea.x, (int)exactArea.y, this.isSubSup);
	}
	
	@Override
	public String toString() {
		return "[]";
	}

	@Override
	public String toMathML() {
		return "<custom/>";
	}

}
