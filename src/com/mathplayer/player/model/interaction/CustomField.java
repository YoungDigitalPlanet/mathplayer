package com.mathplayer.player.model.interaction;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.*;
import com.mathplayer.player.model.InteractionToken;

public class CustomField extends InteractionToken {

	private GapIdentifier gid;

	public CustomField(GapIdentifier gid) {
		this.gid = gid;
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null)
			return size;

		size = new Size(socket.getCustomFieldWidth(gid), socket.getCustomFieldHeight(gid), socket.getCustomFieldHeight(gid) / 2);

		return size.clone();
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		socket.addCustomField((int) exactArea.x, (int) exactArea.y);
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
