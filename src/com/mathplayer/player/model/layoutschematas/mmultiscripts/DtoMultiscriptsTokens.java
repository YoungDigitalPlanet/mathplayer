package com.mathplayer.player.model.layoutschematas.mmultiscripts;

import com.mathplayer.player.model.Token;

public class DtoMultiscriptsTokens {

	private Token base;
	private Token sup;
	private Token sub;
	private Token leftSub;
	private Token leftSup;
	
	public DtoMultiscriptsTokens(Token base, Token sup, Token sub, Token leftSub, Token leftSup) {
		this.base = base;
		this.sup = sup;
		this.sub = sub;
		this.leftSub = leftSub;
		this.leftSup = leftSup;
	}

	public Token getBase() {
		return base;
	}
	public Token getSup() {
		return sup;
	}
	public Token getSub() {
		return sub;
	}
	public Token getLeftSub() {
		return leftSub;
	}
	public Token getLeftSup() {
		return leftSup;
	}
}
