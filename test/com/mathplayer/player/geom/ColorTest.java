package com.mathplayer.player.geom;

import static org.junit.Assert.*;

import org.junit.Test;

public class ColorTest {

	@Test
	public void testColorHashValue() {
		// given
		int red = 255;
		int green = 0;
		int blue = 255;
		Color testObj = new Color(red, green, blue);
		String expectedHash = "rgb(255,0,255)";

		// when
		String resultHash = testObj.toString();

		// then
		assertEquals(expectedHash, resultHash);
	}
}