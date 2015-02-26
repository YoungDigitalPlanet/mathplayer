package com.mathplayer.player.geom;

import com.google.gwt.canvas.dom.client.CssColor;

public class Color {

	private int red;
	private int blue;
	private int green;
	private double alpha;
	private CssColor cssColor;

	public Color(int red, int green, int blue) {
		this(red, green, blue, 1.0D);
	}

	public Color(int red, int green, int blue, double alpha) {
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.alpha = alpha;
		cssColor = CssColor.make(red, green, blue);
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
		return cssColor.value();
	}
}
