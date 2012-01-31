package com.mathplayer.player.interaction;

import com.google.gwt.user.client.ui.Widget;
import com.mathplayer.player.geom.Area;

public interface InteractionSocket {

	boolean isEditorMode();
	
	void addGap(int x, int y);
	void setCanvas(Widget canvas, int width, int height);
	int getTextBoxWidth();
	int getTextBoxHeight();
	void removeTextBox();
	
	void setCursorPosition(Area area);
}
