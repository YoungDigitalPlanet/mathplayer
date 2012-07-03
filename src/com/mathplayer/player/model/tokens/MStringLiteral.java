package com.mathplayer.player.model.tokens;

public class MStringLiteral extends SimpleTextTokenBase {

	public MStringLiteral(String content) {
		super(content, "ms");
	}

	@Override
	public String toMathML() {
		return "<ms lquote=\"\" rquote=\"\" mathvariant=\"normal\">" + content + "</ms>";
	}

}
