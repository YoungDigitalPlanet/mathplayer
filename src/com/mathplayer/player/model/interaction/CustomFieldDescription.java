package com.mathplayer.player.model.interaction;

import com.mathplayer.player.geom.Point;

public class CustomFieldDescription {
	
	private Point _position;
	
	public CustomFieldDescription(Point position) {
		_position = position;
	}
	
	public Point getPosition() {
		return _position;
	}
	
}
