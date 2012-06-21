package com.mathplayer.player.model.tokens;

public class MiddleLineCorrection {
	
	public static double correction(String token, double fontSize)
	{
		// infinity; fontSize 16 => 0.15, fontSize 100 => 0.10
		if (token.indexOf("\u221E") > -1)
			return -fontSize * (-0.000595 * fontSize + 0.15952);
		else
			return 0;
	}

}
