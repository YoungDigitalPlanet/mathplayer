package com.mathplayer.player.geom;

public class Area extends Size {

	public double x;
	public double y;


	/**
	 * c'tor
	 */
	public Area() {
		super();
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.middleLine = 0;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param size
	 */
	public Area(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
		this.middleLine = 0;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Area(double x, double y, double width, double height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.middleLine = 0;
	}

	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Area(double x, double y, double width, double height, double middleLine) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.middleLine = middleLine;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param size
	 */
	public Area(double x, double y, Size size) {
		super();
		this.x = x;
		this.y = y;
		this.width = size.width;
		this.height = size.height;
		this.middleLine = size.middleLine;
	}
	
	public Area clone(){
		return new Area(x, y, width, height, middleLine);
	}
	
	public Size getSize(){
		return ((Size)this).clone();// new Size(width, height, topLine);
	}
	
	public void setSize(Size size){
		width = size.width;
		height = size.height;
		middleLine = size.middleLine;
	}
	
	public void setSize(double width, double height, double middleLine){
		this.width = width;
		this.height = height;
		this.middleLine = middleLine;
	}
	
	public double getTopLine(int fontSize){
		return middleLine - fontSize/2;
	}
/*
	public void addLeft(Area arg){
		width += arg.width;
		topLine = Math.max(arg.topLine, topLine);
		height += topLine + Math.max(height-topLine, arg.height-arg.topLine);
	}
	*/
	public boolean contains(int x, int y){
		return x >= this.x  &&  x <= this.x + this.width  &&  y >= this.y  &&  y <= this.y + this.height;
			
	}
	
	public boolean leftHalfContains(int x, int y){
		return x >= this.x  &&  x <= this.x + this.width/2  &&  y >= this.y  &&  y <= this.y + this.height;
			
	}

}
