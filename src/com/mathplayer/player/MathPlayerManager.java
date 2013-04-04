package com.mathplayer.player;

import java.util.Map;

import com.google.gwt.user.client.ui.Panel;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.utils.FontAnatomy;

import eu.ydp.gwtutil.client.collections.MatchableMap;
import gwt.g2d.client.graphics.Surface;

public class MathPlayerManager {

	private Font font;
	private int gapWidth;
	private int gapHeight;
	private final Map<GapIdentifier, Integer> customFieldWidths;
	private final Map<GapIdentifier, Integer> customFieldHeights;
	private int baseline;
	
	public MathPlayerManager() {
		font = new Font(16, "Verdana", false, false);
		gapWidth = 26;
		gapHeight = 14;

		customFieldWidths = new MatchableMap<GapIdentifier, Integer>();
		customFieldHeights = new MatchableMap<GapIdentifier, Integer>();
	}

	public void setFont(Font font) {
		if (font != null){
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

	public int getBaseline() {
		return baseline;
	}

	public InteractionManager createMath(String source, Panel owner) {
		Token token = createToken(source);
		InteractionManager manager = createInteractionManager(owner);
		Size size = findSize(token, manager);
		updateBaseline(size, font.size);
		render(token, manager, size);
		manager.process();
		return manager;
	}

	private Token createToken(String source) {
		Token token = MathMLParser.parse(source);
		token.setFont(font);
		return token;
	}
	
	private InteractionManager createInteractionManager(Panel owner) {
		InteractionManager manager = new InteractionManager(owner);
		manager.setTextBoxWidth(gapWidth);
		manager.setTextBoxHeight(gapHeight);
		manager.setCustomFieldWidths(customFieldWidths);
		manager.setCustomFieldHeights(customFieldHeights);
		manager.removeTextBox();
		return manager;
	}

	private Size findSize(Token token, InteractionManager manager) {
		Size size = token.measure(manager);
		Size sizeCeiled = new Size(Math.ceil(size.width), Math.ceil(size.height));
		return sizeCeiled;
	}

	private Surface createCanvas(Size size) {
		Surface canvas = new Surface((int) size.width, (int) size.height);
		canvas.setTabIndex(-1);
		return canvas;
	}

	private void render(Token token, InteractionManager manager, Size size) {
		Surface canvas = createCanvas(size);
		manager.setCanvas(canvas, (int) size.width, (int) size.height);
		token.render(canvas, new Area(0, 0, size), manager);
	}

	private void updateBaseline(Size size, int fontSize) {
		baseline = findBaseline(size, fontSize);
	}

	private int findBaseline(Size size, int fontSize) {
		int baselineFromBottomInt = (int) Math.ceil(size.height - size.middleLine - font.size * FontAnatomy.BASELINE_FACTOR);
		return baselineFromBottomInt;
	}
}
