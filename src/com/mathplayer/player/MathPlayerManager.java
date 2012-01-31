package com.mathplayer.player;

import gwt.g2d.client.graphics.Surface;
import com.google.gwt.user.client.ui.Panel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.Token;

public class MathPlayerManager {
	
	private Font font;
	
	public MathPlayerManager(){
		font = new Font(16, "Verdana", false, false);
	}

	public void setFont(Font font){
		this.font = font;
	}
	
	public InteractionManager createMath(String source, Panel owner){
				
		InteractionManager manager = new InteractionManager(owner);
		
		Token t = MathMLParser.parse(source);
		t.setFont(font);
		
		manager.removeTextBox();
		
		Size size = t.measure(manager);

		Surface canvas = new Surface((int)size.width, (int)size.height);

		manager.setCanvas(canvas, (int)size.width, (int)size.height);

		t.render(canvas, new Area(0, 0, size), manager);
		
		manager.process();
		
		return manager;
	}
}
