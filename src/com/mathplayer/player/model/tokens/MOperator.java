package com.mathplayer.player.model.tokens;

public class MOperator extends ContentTextTokenBase {

	public MOperator(String content) {
		super(content);
		MARGIN = 0.15d;
	}

	@Override
	public String toMathML() {
		return "<mo>" + content + "</mo>";
	}

}
