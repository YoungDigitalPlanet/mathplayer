package com.mathplayer.player.style;

import com.google.gwt.xml.client.Element;
import com.mathplayer.player.geom.Font;

public class Context {
	
	private boolean italic = false; 

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}
		
	public void parseElement(Element element){
		if (element.hasAttribute("mathvariant")){
			String mathvariant = element.getAttribute("mathvariant");
			if ("normal".equals(mathvariant) ){
				italic = false;				
			} else {
				italic = true;
			}
		}
	}
	
	public void applyFontStyles(Font font){
		font.italic = italic;
	}
	
	public Context clone(){
		Context sc = new Context();
		sc.setItalic(italic);
		return sc;
	}
	
}
