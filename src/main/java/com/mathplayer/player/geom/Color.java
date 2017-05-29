package com.mathplayer.player.geom;

public class Color {

	private int red;
	private int blue;
	private int green;
	private double alpha;

	public Color(int red, int green, int blue) {
		this(red, green, blue, 1.0D);
	}

	public Color(int red, int green, int blue, double alpha) {
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.alpha = alpha;
	}

	public final int getRed() {
		return this.red;
	}

	public final int getGreen() {
		return this.green;
	}

	public final int getBlue() {
		return this.blue;
	}

	public final double getAlpha() {
		return this.alpha;
	}

	@Override
	public String toString() {
		return "rgb(" + red + "," + green + "," + blue + ")";
	}
}
