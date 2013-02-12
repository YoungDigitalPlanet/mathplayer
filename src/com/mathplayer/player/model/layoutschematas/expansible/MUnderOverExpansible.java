package com.mathplayer.player.model.layoutschematas.expansible;

import gwt.g2d.client.graphics.Surface;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.ContentToken;
import com.mathplayer.player.model.LayoutSchemata;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.signs.expansible.ExpansibleSign;

public class MUnderOverExpansible extends LayoutSchemata {

	private final MUnderOverExpansibleMeasurer underOverExpansibleMeasurer = new MUnderOverExpansibleMeasurer();
	private final MUnderOverExpansibleAreaCalculator underOverExpansibleAreaCalculator = new MUnderOverExpansibleAreaCalculator();
	private final ExpansibleSign base;
	private final Token under;
	private final Token over;
	private DtoExpansibleUnderOverDimensions dtoExpansibleUnderOverDimensions;

	public MUnderOverExpansible(ExpansibleSign base, Token under, Token over){
		this.base = base;
		this.under = under;
		this.over = over;
		
		initTokens(under, over);
	}
	
	@Override
	public Size measure(InteractionSocket socket) {
		DtoExpansibleUnderOverDimensions dimensions = getDtoExpansibleUnderOverDimensions(socket);
		Size summarizedSize = dimensions.getSummarizedSize();
		return summarizedSize;
	}

	private DtoExpansibleUnderOverDimensions getDtoExpansibleUnderOverDimensions(InteractionSocket socket) {
		if(dtoExpansibleUnderOverDimensions == null){
			dtoExpansibleUnderOverDimensions = underOverExpansibleMeasurer.measure(socket, base, under, over);
		}
		return dtoExpansibleUnderOverDimensions;
	}

	
	@Override
	public void render(Surface canvas, Area area, InteractionSocket socket){
		DtoExpansibleUnderOverDimensions dimensions = getDtoExpansibleUnderOverDimensions(socket);
		DtoExpansibleUnderOverArea areasDto = underOverExpansibleAreaCalculator.calculateArea(area, dimensions);
		
		base.renderLeftPanel(canvas, areasDto.getLeftPanelArea(), socket);
		base.renderMiddlePanel(canvas, areasDto.getMiddlePanelArea(), socket);
		base.renderRightPanel(canvas, areasDto.getRightPanelArea(), socket);
		
		renderTokenIfNotNull(over, areasDto.getOverContentArea(), canvas, socket);
		renderTokenIfNotNull(under, areasDto.getUnderContentArea(), canvas, socket);
	}

	private void renderTokenIfNotNull(Token token, Area overContentArea, Surface canvas, InteractionSocket socket) {
		if(token != null){
			token.render(canvas, overContentArea, socket);
		}
	}
	
	@Override
	public void setFont(Font font){
		this.font = font;
		base.setFont(font.clone());
		if (under != null){
			under.setFont(font.cloneShrunk());
		}
		if (over != null){
			over.setFont(font.cloneShrunk());
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(base.toString());
		appendTokenStringIfNotNull("__", under, stringBuilder);
		appendTokenStringIfNotNull("^^", over, stringBuilder);
		return stringBuilder.toString();
	}
	
	private void appendTokenStringIfNotNull(String prefix, Token token, StringBuilder stringBuilder){
		if(token != null){
			boolean isContentToken = token instanceof ContentToken;
			stringBuilder.append(prefix);
			if(isContentToken){
				stringBuilder.append("(");
			}
			stringBuilder.append(token.toString());
			if(isContentToken){
				stringBuilder.append(")");
			}
		}
	}

	@Override
	public String toMathML() {
		String mathML = null;
		if (under == null){
			mathML = buildMathML("mover", base.toMathML(), over.toMathML());
		} else if (over == null){
			mathML = buildMathML("munder", base.toMathML(), under.toMathML());
		}
		mathML = buildMathML("munderover", base.toMathML(), under.toMathML(), over.toMathML());
		return mathML;
	}

	private String buildMathML(String tagName, String ... childs){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append(tagName);
		stringBuilder.append(">");
		for (String childMathML : childs) {
			stringBuilder.append(childMathML);
		}
		stringBuilder.append("</");
		stringBuilder.append(tagName);
		stringBuilder.append(">");
		return stringBuilder.toString();
	}
}
