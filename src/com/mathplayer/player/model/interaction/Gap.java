package com.mathplayer.player.model.interaction;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.InteractionToken;

public class Gap extends InteractionToken {

	public Gap() {

	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size != null)
			return size;

		if (socket.isEditorMode())
			size = new Size(font.size, font.size, font.size / 2);
		else
			size = new Size(socket.getTextBoxWidth(), socket.getTextBoxHeight(), socket.getTextBoxHeight() / 2);

		return size.clone();
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);
		Context2d context2d = canvas.getContext2d();

		if (socket.isEditorMode()) {
			Color color = new Color(0, 255, 255);
			context2d.setFillStyle(color.toString());
			context2d.fillRect(exactArea.x, exactArea.y, exactArea.width, exactArea.height);
		} else {
			socket.addGap((int) exactArea.x, (int) exactArea.y);
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
