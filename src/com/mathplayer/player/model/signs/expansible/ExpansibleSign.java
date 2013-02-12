package com.mathplayer.player.model.signs.expansible;

import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;

public interface ExpansibleSign {

	Size measureLeftPanel(InteractionSocket socket);

	Size measureRightPanel(InteractionSocket socket);

	Size measureMiddlePanel(InteractionSocket socket);

	void renderMiddlePanel(Surface canvas, Area area, InteractionSocket socket);

	void renderLeftPanel(Surface canvas, Area area, InteractionSocket socket);

	void renderRightPanel(Surface canvas, Area area, InteractionSocket socket);

	void setFont(Font font);
	
	String toMathML();
}
