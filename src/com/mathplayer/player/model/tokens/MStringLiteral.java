package com.mathplayer.player.model.tokens;

import com.google.gwt.user.client.Window.Navigator;

public class MStringLiteral extends ContentTextTokenBase {

	public MStringLiteral(String content) {
		super(replaceUnsupportedCharacters(content));
	}
	
	private static String replaceUnsupportedCharacters(String c){
		if (Navigator.getUserAgent().contains("MSIE 8.0")){
			c = c.replace("\u211D", "R");
		}
		return c;
	}

	@Override
	public String toMathML() {
		return "<ms>" + content + "</ms>";
	}

}
