package com.mathplayer.player;

import java.util.HashMap;
import java.util.Map;

import gwt.g2d.client.graphics.Surface;
import com.google.gwt.user.client.ui.Panel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.Token;

public class MathPlayerManager {
	
	private Font font;
	private int gapWidth;
	private int gapHeight;	
	private Map<String, Integer> customFieldWidths;
	private Map<String, Integer> customFieldHeights;
	
	public MathPlayerManager(){
		font = new Font(16, "Verdana", false, false);
		gapWidth = 26;
		gapHeight = 14;
		
		customFieldWidths = new HashMap<String, Integer>();
		customFieldHeights = new HashMap<String, Integer>();
	}

	public void setFont(Font font){
		if (font != null)
			this.font = font;
	}

	public void setGapWidth(int w){
		if (w >= 0)
			gapWidth = w;
	}

	public void setGapHeight(int h){
		if (h >= 0)
			gapHeight = h;
	}

	public void setCustomFieldWidth(String type, int w){
		if (w >= 0)
			customFieldWidths.put(type, w);
	}

	public void setCustomFieldHeight(String type, int h){
		if (h >= 0)
			customFieldHeights.put(type,  h);
	}
	
	
	public InteractionManager createMath(String source, Panel owner){
				
		InteractionManager manager = new InteractionManager(owner);
		
		Token t = MathMLParser.parse(source);
		t.setFont(font);
		
		manager.setTextBoxWidth(gapWidth);
		manager.setTextBoxHeight(gapHeight);
		manager.setCustomFieldWidths(customFieldWidths);
		manager.setCustomFieldHeights(customFieldHeights);
		manager.removeTextBox();
		
		Size size = t.measure(manager);

		Surface canvas = new Surface((int)size.width, (int)size.height);

		manager.setCanvas(canvas, (int)size.width, (int)size.height);

		t.render(canvas, new Area(0, 0, size), manager);
		
		manager.process();
		
		return manager;
	}
}
