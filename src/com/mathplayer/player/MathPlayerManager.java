package com.mathplayer.player;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.user.client.ui.Panel;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.*;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.utils.FontAnatomy;
import eu.ydp.gwtutil.client.collections.MatchableMap;
import java.util.Map;

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

		customFieldWidths = new MatchableMap<>();
		customFieldHeights = new MatchableMap<>();
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

	public int getBaseline() {
		return baseline;
	}

	public InteractionManager createMath(String source, Panel owner) {
		Token token = createToken(source);
		InteractionManager manager = createInteractionManager(owner);
		Size size = findSize(token, manager);
		updateBaseline(size);
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
		Size sizeCeiled = new Size(Math.ceil(size.width), Math.ceil(size.height), Math.ceil(size.middleLine));
		return sizeCeiled;
	}

	private Canvas createCanvas(Size size) {
		Canvas canvas = Canvas.createIfSupported();
		CanvasElement canvasElement = canvas.getCanvasElement();
		canvasElement.setWidth((int) size.width);
		canvasElement.setHeight((int) size.height);

		canvas.setTabIndex(-1);
		return canvas;
	}

	private void render(Token token, InteractionManager manager, Size size) {
		Canvas canvas = createCanvas(size);
		manager.setCanvas(canvas, (int) size.width, (int) size.height);
		token.render(canvas, new Area(0, 0, size), manager);
	}

	private void updateBaseline(Size size) {
		baseline = findBaseline(size);
	}

	private int findBaseline(Size size) {
		double middlelineFromBottom = size.height - size.middleLine;
		double middlelineToBaseline = font.size * FontAnatomy.BASELINE_FACTOR;
		double baselineFromBottom = middlelineFromBottom - middlelineToBaseline;
		int baselineFromBottomInt = (int) Math.ceil(baselineFromBottom);
		return baselineFromBottomInt;
	}
}
