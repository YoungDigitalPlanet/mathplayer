package com.mathplayer.player.geom;

import gwt.g2d.client.graphics.Color;

public class Font {

	public Integer size;
	public String name;
	public boolean italic;
	public boolean bold;
	public Color color;
	public static final int MIN_SIZE = 8;
	public static final int SHRINK_STEP = 4;
	public static final double SHRINK_COEFF = 0.7;

	/**
	 * @param size
	 * @param name
	 * @param italic
	 * @param bold
	 */
	public Font(int size, String name, boolean italic, boolean bold) {
		super();
		this.size = size;
		this.name = name;
		this.italic = italic;
		this.bold = bold;
		color = new Color(0, 0, 0);
	}

	/**
	 * @param size
	 * @param name
	 * @param italic
	 * @param bold
	 * @param color
	 */
	public Font(int size, String name, boolean italic, boolean bold, Color color) {
		super();
		this.size = size;
		this.name = name;
		this.italic = italic;
		this.bold = bold;
		this.color = color;
	}

	/**
	 * @param size
	 * @param name
	 * @param italic
	 * @param bold
	 * @param color
	 */
	public Font(int size, String name, boolean italic, boolean bold, com.mathplayer.player.geom.Color color) {
		super();
		this.size = size;
		this.name = name;
		this.italic = italic;
		this.bold = bold;
		this.color = color.toG2dColor();
	}

	public Font clone(){
		return new Font(size, name, italic, bold, color);
	}

	public Font clone(int size){
		return new Font(size, name, italic, bold, color);
	}

	public Font cloneShrunk(){
		int newSize = (int)(size * SHRINK_COEFF);
		if (newSize < MIN_SIZE) {
			newSize = MIN_SIZE;
		}
		return new Font(newSize, name, italic, bold, color);
	}

	@Override
	public String toString(){
		String s = "";
		if (italic) {
			s += "italic ";
		}
		if (bold) {
			s += "bold ";
		}
		s += size.toString() + "px ";
		s += name;
		return s;

	}

}
