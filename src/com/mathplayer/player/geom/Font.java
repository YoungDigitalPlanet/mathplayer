package com.mathplayer.player.geom;

public class Font {

	public int size;
	public String name;
	public boolean italic;
	public boolean bold;
	public Color color;
	public static final int MIN_SIZE = 8;
	public static final double SHRINK_COEFF = 0.7;

	/**
	 * @param size
	 * @param name
	 * @param italic
	 * @param bold
	 */
	public Font(int size, String name, boolean italic, boolean bold) {
		this.size = size;
		this.name = name;
		this.italic = italic;
		this.bold = bold;
	}

	public Font(int size, String name, boolean italic, boolean bold, Color color) {
		this.size = size;
		this.name = name;
		this.italic = italic;
		this.bold = bold;
		this.color = color;
	}

	public Font clone() {
		return new Font(size, name, italic, bold, color);
	}

	public Font clone(int size) {
		return new Font(size, name, italic, bold, color);
	}

	public Font cloneShrunk() {
		int newSize = (int) (size * SHRINK_COEFF);
		if (newSize < MIN_SIZE) {
			newSize = MIN_SIZE;
		}
		return new Font(newSize, name, italic, bold, color);
	}

	@Override
	public String toString() {
		String s = "";
		if (italic) {
			s += "italic ";
		}
		if (bold) {
			s += "bold ";
		}
		s += size + "px ";
		s += name;
		return s;
	}
}
