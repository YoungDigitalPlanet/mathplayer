package com.mathplayer.player.model.tokens;

import com.google.gwt.user.client.Window.Navigator;
import com.mathplayer.player.utils.XmlUtils;

public abstract class SimpleTextTokenBase extends ContentTextTokenBase {

	private String nodeName;

	public SimpleTextTokenBase(String content, String nodeName) {
		super(replaceUnsupportedCharacters(content));
		this.nodeName = nodeName;
	}
	
	private static String replaceUnsupportedCharacters(String c){
		if (Navigator.getUserAgent().contains("MSIE 8.0")){
			c = c.replace("\u211D", "R");
		}
		return c;
	}

	@Override
	public String toMathML() {
		return "<"+nodeName+">" + escape(content) + "</"+nodeName+">";
	}
	
	private String escape(String value){
		return XmlUtils.escape(value);
	}

}
