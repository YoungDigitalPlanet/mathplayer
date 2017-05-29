package com.mathplayer.player.utils;

public abstract class BrowserUtils {

	public static native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;

}
