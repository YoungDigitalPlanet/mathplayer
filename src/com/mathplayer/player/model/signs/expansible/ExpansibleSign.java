package com.mathplayer.player.model.signs.expansible;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;

public interface ExpansibleSign {

	Size measureLeftPanel(InteractionSocket socket);

	Size measureRightPanel(InteractionSocket socket);

	Size measureMiddlePanel(InteractionSocket socket);

	void renderMiddlePanel(Canvas canvas, Area area, InteractionSocket socket);

	void renderLeftPanel(Canvas canvas, Area area, InteractionSocket socket);

	void renderRightPanel(Canvas canvas, Area area, InteractionSocket socket);

	void setFont(Font font);

	String toMathML();
}
