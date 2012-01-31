package com.mathplayer.player.geom;

public class Position {

	public double x;
	public double y;

	public Position(){
		x = 0d;
		y = 0d;
	}

	public Position(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Position clone(){
		return new Position(x, y);
	}
	
}
