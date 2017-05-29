package com.mathplayer.player.model.layoutschematas.mmultiscripts;

public enum MMultiscriptsProportionRates {

	/*Enum describing proportion rates of MMultiscript module
	  Sum of all rates have to be equal 1.0d;
	*/
	UPPER_INDEX_BOTTOM_LIMIT_LINE_RATE(0.4d),
	BOTTOM_INDEX_TOP_LIMIT_LINE_RATE(0.4d),
	DISTANCE_BEETWEN_TOP_AND_BOTTOM_INDEX_RATE(0.2d);
	
	private final double rate;

	private MMultiscriptsProportionRates(double rate){
		this.rate = rate;
	}
	
	public double getRate(){
		return this.rate;
	}
}
