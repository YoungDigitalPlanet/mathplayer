package com.mathplayer.player.geom;

public class Color {

	private int red;
	private int blue;
	private int green;

	public Color(int red, int green, int blue){
		this.red = red;
		this.blue = blue;
		this.green = green;
	}
	
	public gwt.g2d.client.graphics.Color toG2dColor(){
		return new gwt.g2d.client.graphics.Color(red, green, blue);
	}
	
}
