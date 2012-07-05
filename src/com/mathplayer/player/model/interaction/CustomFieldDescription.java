package com.mathplayer.player.model.interaction;

import com.mathplayer.player.geom.Point;

public class CustomFieldDescription {
	
	private Point _position;
	private boolean _isSubSup;
	
	public CustomFieldDescription(Point position, boolean isSubSup) {
		_position = position;
		_isSubSup = isSubSup;
	}
	
	public CustomFieldDescription(Point position) {
		this(position, false);
	}
	
	public Point getPosition() {
		return _position;
	}

	public Boolean isSubSup() {
		return _isSubSup;
	}
	
}
