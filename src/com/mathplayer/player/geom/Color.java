package com.mathplayer.player.geom;

public class Color {

	private int red;
	private int blue;
	private int green;
	private double alpha;
	private String colorCode;

	public Color(int red, int green, int blue) {
		this(red, green, blue, 1.0D);
	}

	public Color(int red, int green, int blue, double alpha) {
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.alpha = alpha;
		colorCode = genrateStringCode();
	}

	public final int getR() {
		return this.red;
	}

	public final int getG() {
		return this.green;
	}

	public final int getB() {
		return this.blue;
	}

	public final double getAlpha() {
		return this.alpha;
	}

	@Override
	public String toString() {
		return colorCode;
	}

	private String genrateStringCode() {
		StringBuilder stringBuilder = new StringBuilder("#000000");
		String hexString = Integer.toHexString(getHexValue(red, green, blue));
		return stringBuilder.replace(stringBuilder.length() - hexString.length(), stringBuilder.length(), hexString).toString();
	}

	private final int getHexValue(int r, int g, int b) {
		return r << 16 & 16711680 | g << 8 & '\uff00' | b & 255;
	}
}
