package com.mathplayer.player;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Panel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.utils.BrowserUtils;

import eu.ydp.gwtutil.client.collections.MatchableMap;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import gwt.g2d.client.graphics.Surface;

public class MathPlayerManager {

	private Font font;
	private int gapWidth;
	private int gapHeight;
	private final Map<GapIdentifier, Integer> customFieldWidths;
	private final Map<GapIdentifier, Integer> customFieldHeights;
	private static float FONT_DECENT = BrowserUtils.getUserAgent().toLowerCase().contains("msie") ? 0.37f : 0.275f;
	static{
		if(UserAgentChecker.isStackAndroidBrowser()){
			FONT_DECENT = 0.32f;
		}
	}
	public MathPlayerManager() {
		font = new Font(16, "Verdana", false, false);
		gapWidth = 26;
		gapHeight = 14;

		customFieldWidths = new MatchableMap<GapIdentifier, Integer>();
		customFieldHeights = new MatchableMap<GapIdentifier, Integer>();
	}

	public void setFont(Font font) {
		if (font != null) {
			this.font = font;
		}
	}

	public void setGapWidth(int w) {
		if (w >= 0) {
			gapWidth = w;
		}
	}

	public void setGapHeight(int h) {
		if (h >= 0) {
			gapHeight = h;
		}
	}

	public void setCustomFieldWidth(GapIdentifier gid, int w) {
		if (w >= 0) {
			customFieldWidths.put(gid, w);
		}
	}

	public void setCustomFieldHeight(GapIdentifier gid, int h) {
		if (h >= 0) {
			customFieldHeights.put(gid, h);
		}
	}

	public InteractionManager createMath(String source, Panel owner) {

		InteractionManager manager = new InteractionManager(owner);

		Token t = MathMLParser.parse(source);
		t.setFont(font);

		manager.setTextBoxWidth(gapWidth);
		manager.setTextBoxHeight(gapHeight);
		manager.setCustomFieldWidths(customFieldWidths);
		manager.setCustomFieldHeights(customFieldHeights);
		manager.removeTextBox();

		Size size = t.measure(manager);
		size.width = Math.ceil(size.width);
		size.height = Math.ceil(size.height);
		Surface canvas = new Surface((int) size.width, (int) size.height){
			@Override
			public void setTabIndex(int index) {
				super.setTabIndex(-1);
			}
		};
		owner.getElement().getStyle().setVerticalAlign(Math.floor(-(size.height - size.middleLine) + font.size * FONT_DECENT), Unit.PX);
		manager.setCanvas(canvas, (int) size.width, (int) size.height);
		t.render(canvas, new Area(0, 0, size), manager);
		manager.process();
		canvas.setTabIndex(-1);
		return manager;
	}
}
