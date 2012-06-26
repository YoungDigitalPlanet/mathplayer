package com.mathplayer.player.geom;

public class Size {

	public double width;
	public double height;
	public double middleLine;

	/**
	 * c'tor
	 */
	public Size() {
		this(0, 0, 0);
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public Size(double width, double height) {
		this(width, height, height / 2);
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public Size(double width, double height, double middleLine) {
		super();
		this.width = width;
		this.height = height;
		this.middleLine = middleLine;
	}
	
	public Size clone(){
		return new Size(width, height, middleLine);
	}

	public void addLeft(Size arg){
		width += arg.width;
		double newMiddleLine = Math.max(arg.middleLine, middleLine);
		height = newMiddleLine + Math.max(height-middleLine, arg.height-arg.middleLine);
		middleLine = newMiddleLine;
	}

	public void addTop(Size arg){
		height += arg.height;
		middleLine += arg.height;
		if (arg.width > width)
			width = arg.width;
	}

	public void addBottom(Size arg){
		height += arg.height;
		if (arg.width > width)
			width = arg.width;
	}

}
