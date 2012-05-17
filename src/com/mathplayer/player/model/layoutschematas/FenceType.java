package com.mathplayer.player.model.layoutschematas;

public enum FenceType {
	ROUND, SQUARE, CURLY, ANGLE, VERTICAL_BAR, NONE;
	
	public static FenceType fromString(String fenceTypeString){
		if (fenceTypeString.toLowerCase().equals("square")  ||  fenceTypeString.equals("[")  ||  fenceTypeString.equals("]"))
			return SQUARE;
		if (fenceTypeString.toLowerCase().equals("curly")  ||  fenceTypeString.equals("{")  ||  fenceTypeString.equals("}"))
			return CURLY;
		if (fenceTypeString.toLowerCase().equals("angle")  ||  fenceTypeString.equals("<")  ||  fenceTypeString.equals(">"))
			return ANGLE;
		if (fenceTypeString.toLowerCase().equals("vertical-bar")  ||  fenceTypeString.equals("|"))
			return VERTICAL_BAR;
		if (fenceTypeString.toLowerCase().equals("none")  ||  fenceTypeString.equals(""))
			return NONE;
		
		return ROUND;
	}
	 
	public static String openFenceToString(FenceType fenceType){
		if (fenceType == FenceType.SQUARE)
			return "[";
		if (fenceType == FenceType.CURLY)
			return "{";
		if (fenceType == FenceType.ANGLE)
			return "<";
		if (fenceType == FenceType.VERTICAL_BAR)
			return "|";
		if (fenceType == FenceType.NONE)
			return "";
		return "(";
	}
	 
	public static String closeFenceToString(FenceType fenceType){
		if (fenceType == FenceType.SQUARE)
			return "]";
		if (fenceType == FenceType.CURLY)
			return "}";
		if (fenceType == FenceType.ANGLE)
			return ">";
		if (fenceType == FenceType.VERTICAL_BAR)
			return "|";
		if (fenceType == FenceType.NONE)
			return "";
		return ")";
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
		if (type == NONE)
			return "";
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
		if (type == NONE)
			return "";
		return ")";
	}
}
