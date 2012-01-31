package com.mathplayer.player.model.tokens;

public class MStringLiteral extends ContentTextTokenBase {

	public MStringLiteral(String content) {
		super(content);
	}

	@Override
	public String toMathML() {
		return "<ms>" + content + "</ms>";
	}

}
