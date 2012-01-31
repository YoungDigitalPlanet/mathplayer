package com.mathplayer.player.geom;

public class Font {

	public Integer size;
	public String name;
	public boolean italic;
	public boolean bold;
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
	}

	public Font clone(){
		return new Font(size, name, italic, bold);
	}
	
	public Font clone(int size){
		return new Font(size, name, italic, bold);
	}
	
	public Font cloneShrunk(){
		int newSize = (int)(size * SHRINK_COEFF);
		if (newSize < MIN_SIZE)
			newSize = MIN_SIZE;
		return new Font(newSize, name, italic, bold);
	}
	
	public String toString(){
		String s = "";
		if (italic)
			s += "italic ";
		if (bold)
			s += "bold ";
		s += size.toString() + "px ";
		s += name;
		return s;
		
	}
	
}
