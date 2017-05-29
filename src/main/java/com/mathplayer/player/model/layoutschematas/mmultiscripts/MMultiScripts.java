package com.mathplayer.player.model.layoutschematas.mmultiscripts;

import com.google.gwt.canvas.client.Canvas;
import com.mathplayer.player.geom.*;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.*;
import com.mathplayer.player.model.tokens.MEmpty;

public class MMultiScripts extends LayoutSchemata {

	private MMultiscriptsMeasurer multiscriptsMeasurer = new MMultiscriptsMeasurer();
	private MMultiscriptsAreaCalculator multiscriptsAreaCalculator = new MMultiscriptsAreaCalculator();
	private Token base;
	private Token sub;
	private Token sup;
	private Token lsub;
	private Token lsup;
	private DtoMMultiscriptsDimensions dtoMMultiscriptsDimensions;

	public MMultiScripts(Token base, Token sub, Token sup, Token lsub, Token lsup) {
		this.base = base;
		this.sub = sub;
		this.sup = sup;
		this.lsub = lsub;
		this.lsup = lsup;

		changeMEmptyTokensToNulls();
		initTokens(base, sub, sup, lsub, lsup);
	}

	private void changeMEmptyTokensToNulls() {
		if (sub instanceof MEmpty)
			sub = null;
		if (sup instanceof MEmpty)
			sup = null;
		if (lsub instanceof MEmpty)
			lsub = null;
		if (lsup instanceof MEmpty)
			lsup = null;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
		base.setFont(font.clone());
		setFontsInChildTokens(font, sub, sup, lsub, lsup);
	}

	private void setFontsInChildTokens(Font font, Token... tokens) {
		for (Token token : tokens) {
			if (token != null) {
				token.setFont(font.cloneShrunk());
			}
		}
	}

	@Override
	public Size measure(InteractionSocket socket) {
		if (size == null) {
			DtoMMultiscriptsDimensions dtoMMultiscriptsDimensions = getMultiscriptDimensions(socket);
			size = dtoMMultiscriptsDimensions.getMultiscriptsSize();
		}

		return size;
	}

	@Override
	public void render(Canvas canvas, Area area, InteractionSocket socket) {
		super.render(canvas, area, socket);

		DtoMMultiscriptsDimensions multiscriptDimensions = getMultiscriptDimensions(socket);
		DtoMultiscriptsArea dtoMultiscriptsArea = multiscriptsAreaCalculator.calculateAreas(exactArea, multiscriptDimensions);

		renderTokenIfNotNull(canvas, dtoMultiscriptsArea.getLeftSup(), socket, lsup);
		renderTokenIfNotNull(canvas, dtoMultiscriptsArea.getLeftSub(), socket, lsub);
		renderTokenIfNotNull(canvas, dtoMultiscriptsArea.getBase(), socket, base);
		renderTokenIfNotNull(canvas, dtoMultiscriptsArea.getSup(), socket, sup);
		renderTokenIfNotNull(canvas, dtoMultiscriptsArea.getSub(), socket, sub);
	}

	private DtoMMultiscriptsDimensions getMultiscriptDimensions(InteractionSocket socket) {
		if (dtoMMultiscriptsDimensions == null) {
			DtoMultiscriptsTokens tokensDto = new DtoMultiscriptsTokens(base, sup, sub, lsub, lsup);
			dtoMMultiscriptsDimensions = multiscriptsMeasurer.measure(socket, tokensDto);
		}

		return dtoMMultiscriptsDimensions;
	}

	private void renderTokenIfNotNull(Canvas canvas, Area area, InteractionSocket socket, Token token) {
		if (token != null) {
			token.render(canvas, area, socket);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(base.toString());
		appendChildTokenString(sub, "_", sb);
		appendChildTokenString(sup, "^", sb);
		appendChildTokenString(lsub, "__", sb);
		appendChildTokenString(lsup, "^^", sb);
		return sb.toString();
	}

	private void appendChildTokenString(Token token, String prefix, StringBuilder sb) {
		if (token != null) {
			sb.append(prefix);

			boolean isContentToken = token instanceof ContentToken;
			if (isContentToken) {
				sb.append("(");
			}

			sb.append(token.toString());

			if (isContentToken) {
				sb.append(")");
			}
		}
	}

	@Override
	public String toMathML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<mmultiscripts>");
		sb.append(base.toMathML());

		appendTokenMathMLOrNoneString(sub, sb);
		appendTokenMathMLOrNoneString(sup, sb);
		sb.append("<mprescripts/>");

		appendTokenMathMLOrNoneString(lsub, sb);
		appendTokenMathMLOrNoneString(lsup, sb);
		sb.append("</mmultiscripts>");

		return sb.toString();
	}

	private void appendTokenMathMLOrNoneString(Token token, StringBuilder sb) {
		if (token == null) {
			sb.append("<none/>");
		} else {
			String tokenMathML = token.toMathML();
			sb.append(tokenMathML);
		}
	}

}
