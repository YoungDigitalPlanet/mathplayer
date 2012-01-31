package com.mathplayer.player.model.layoutschematas;

public enum FenceType {
	ROUND, SQUARE, CURLY, ANGLE, VERTICAL_BAR;
	
	public static FenceType fromString(String fenceTypeString){
		if (fenceTypeString.toLowerCase().equals("square"))
			return SQUARE;
		if (fenceTypeString.toLowerCase().equals("curly"))
			return CURLY;
		if (fenceTypeString.toLowerCase().equals("angle"))
			return ANGLE;
		if (fenceTypeString.toLowerCase().equals("vertical-bar"))
			return VERTICAL_BAR;
		
		return ROUND;
	}
	
	public static String getOpenFence(FenceType type){
		if (type == ROUND)
			return "(";
		if (type == SQUARE)
			return "[";
		if (type == CURLY)
			return "{";
		if (type == VERTICAL_BAR)
			return String.valueOf((char)9168);
		if (type == ANGLE)
			return String.valueOf((char)9001);
		return "(";
	}
	
	public static String getCloseFence(FenceType type){
		if (type == ROUND)
			return ")";
		if (type == SQUARE)
			return "]";
		if (type == CURLY)
			return "}";
		if (type == VERTICAL_BAR)
			return String.valueOf((char)9168);
		if (type == ANGLE)
			return String.valueOf((char)9002);
		return ")";
	}
}
