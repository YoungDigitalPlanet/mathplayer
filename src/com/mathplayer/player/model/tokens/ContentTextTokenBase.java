package com.mathplayer.player.model.tokens;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.RootPanel;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.ContentToken;
import com.mathplayer.player.model.shapes.*;
import com.mathplayer.player.style.Context;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.RuntimeMobileUserAgent;
import java.util.*;

public abstract class ContentTextTokenBase extends ContentToken {

	private static final String UNICODE_CHAR_RATIONAL = "\u211A";
	private static final String UNICODE_CHAR_DIFF = "\u2216";
	private static final String UNICODE_CHAR_INTERSECTION = "\u22C2";
	private static final String UNICODE_CHAR_UNION = "\u22C3";
	private static final String UNICODE_CHAR_NATURAL = "\u2115";
	private static final String UNICODE_CHAR_INTEGER = "\u2124";
	private static final String UNICODE_CHAR_REAL = "\u211D";
	private static final String UNICODE_CHAR_CONJUNCTION = "\u2227";
	private static final String UNICODE_CHAR_CONJUNCTION_ALT = "\u02C4";
	private static final String UNICODE_CHAR_DISJUNCTION = "\u2228";
	private static final String UNICODE_CHAR_DISJUNCTION_ALT = "\u02C5";

	List<String> mathChars = Arrays.asList(UNICODE_CHAR_REAL, UNICODE_CHAR_INTEGER, UNICODE_CHAR_NATURAL, UNICODE_CHAR_UNION,
										   UNICODE_CHAR_INTERSECTION, UNICODE_CHAR_DIFF, UNICODE_CHAR_RATIONAL, UNICODE_CHAR_CONJUNCTION,
										   UNICODE_CHAR_DISJUNCTION, UNICODE_CHAR_CONJUNCTION_ALT, UNICODE_CHAR_DISJUNCTION_ALT);

	private static final int MATH_CHARS_MARGIN = 4;
	protected String content;
	protected Context styleContext;
	protected double margin;

	public ContentTextTokenBase(String content) {
		this.content = content;
		margin = 0;
	}

	public void setStyleContext(Context styleContext) {
		this.styleContext = styleContext;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
		if (styleContext != null && font != null) {
			styleContext.applyFontStyles(font);
		}
	}

	public boolean isAlternativeMathRendering() {
		boolean alternativeMathRendering = false;
		if (UserAgentChecker.isUserAgent(RuntimeMobileUserAgent.ANDROID)) {
			for (String string : mathChars) {
				if (content.contains(string)) {
					alternativeMathRendering = true;
					break;
				}
			}
		}
		return alternativeMathRendering;
	}

	public int countAlternativeMathRendering() {
		int alternativeMathRendering = 0;
		for (String string : mathChars) {
			if (content.contains(string)) {
				alternativeMathRendering++;
			}
		}
		return alternativeMathRendering;
	}

	public double getTextWidth() {
		return isAlternativeMathRendering() ?
				getFontTextOffset() * countAlternativeMathRendering() + MATH_CHARS_MARGIN :
				getTextWidth(content, font, margin, RootPanel.get());
	}

	@Override
	public Size measure(InteractionSocket socket) {

		if (size != null) {
			return size;
		}

		size = new Size();

		size.width = getTextWidth();
		size.height = font.size;
		size.middleLine = font.size / 2;

		// fix for android when measuring italic on canvas
		if (isStackAndroidBrowser()) {
			if (font.italic) {
				size.width += size.width * .17;
			}
			size.height += size.height * .1;
		}

		return size.clone();
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		boolean alternativeMathRendering = isAlternativeMathRendering();
		Context2d context2d = canvas.getContext2d();
		context2d.setFont(font.toString());
		context2d.setFillStyle(font.color.toString());
		context2d.setStrokeStyle(font.color.toString());

		if (alternativeMathRendering) {
			int currentPosition = MATH_CHARS_MARGIN / 2;
			Size currentCharMeasuredSize = null;
			for (char c : content.toCharArray()) {

				String currentChar = new Character(c).toString();

				if (currentChar.equals(UNICODE_CHAR_REAL)) {
					RealNumbersSign sign = new RealNumbersSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_INTEGER)) {
					IntegersSign sign = new IntegersSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_NATURAL)) {
					NaturalsSign sign = new NaturalsSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_UNION)) {
					UnionSign sign = new UnionSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_INTERSECTION)) {
					IntersectionSign sign = new IntersectionSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_DIFF)) {
					DiffSign sign = new DiffSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_RATIONAL)) {
					RationalSign sign = new RationalSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_CONJUNCTION) || currentChar.equals(UNICODE_CHAR_CONJUNCTION_ALT)) {
					ConjunctionSign sign = new ConjunctionSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else if (currentChar.equals(UNICODE_CHAR_DISJUNCTION) || currentChar.equals(UNICODE_CHAR_DISJUNCTION_ALT)) {
					DisjunctionSign sign = new DisjunctionSign(exactArea.x + currentPosition, exactArea.y, getFontTextOffset());
					sign.render(canvas, area, socket);
					currentCharMeasuredSize = sign.measure(socket);
				} else {
					context2d.fillText(currentChar, exactArea.x + currentPosition, exactArea.y + getFontTextOffset());
					currentCharMeasuredSize.width = context2d.measureText(currentChar).getWidth();
				}
				currentPosition += currentCharMeasuredSize.width;
			}
		} else {
			context2d.fillText(content, exactArea.x + font.size * margin, exactArea.y + getFontTextOffset());
		}
	}

	@Override
	public String toString() {
		return content;
	}

	@Override
	public abstract String toMathML();
}
