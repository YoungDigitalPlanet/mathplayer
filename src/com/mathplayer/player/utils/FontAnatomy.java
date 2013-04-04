package com.mathplayer.player.utils;

import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class FontAnatomy {

	public static final double BASELINE_FACTOR;
	public static final double TEXT_OFFSET;
	
	static {
		if(UserAgentChecker.isStackAndroidBrowser()){
			BASELINE_FACTOR = 0.32d;
			TEXT_OFFSET = 0.81d;
		} else if (BrowserUtils.getUserAgent().toLowerCase().contains("msie")){
			BASELINE_FACTOR = 0.37d;	
			TEXT_OFFSET = 0.91d;		
		} else {
			BASELINE_FACTOR = 0.275d;
			TEXT_OFFSET = 0.725d;
		}
	}
}
