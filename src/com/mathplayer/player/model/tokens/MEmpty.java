package com.mathplayer.player.model.tokens;

public class MEmpty extends ContentTextTokenBase {

	public MEmpty() {
		super("");
	}

	@Override
	public String toMathML() {
		return "";
	}

}
