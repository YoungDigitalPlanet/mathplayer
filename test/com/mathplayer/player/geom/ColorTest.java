package com.mathplayer.player.geom;

import com.google.gwt.junit.client.GWTTestCase;

public class ColorTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.mathplayer.Math_player";
	}

	public void testColorHashValue() {
		// given
		int red = 255;
		int green = 0;
		int blue = 255;
		Color testObj = new Color(red,green,blue);
		String expectedHash = "rgb(255,0,255)";

		// when
		String resultHash = testObj.toString();

		// then
		assertEquals(expectedHash, resultHash);
	}
}