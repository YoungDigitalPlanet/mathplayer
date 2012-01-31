package com.mathplayer.player.model.tokens;

public class MIdentifier extends ContentTextTokenBase {

	public MIdentifier(String content) {
		super(content);
	}

	@Override
	public String toMathML() {
		return "<mi>" + content + "</mi>";
	}
}
