package com.mathplayer.player.model.tokens;

public class MNumber extends ContentTextTokenBase {
	
	public MNumber(String content){
		super(content);
		MARGIN = 0.2d;
	}

	@Override
	public String toMathML() {
		return "<mn>" + content + "</mn>";
	}
}
