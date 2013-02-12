package com.mathplayer.player.model.layoutschematas.expansible;

import com.google.common.primitives.Doubles;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.signs.expansible.ExpansibleSign;

public class MUnderOverExpansibleMeasurer {

	public DtoExpansibleUnderOverDimensions measure(InteractionSocket socket, ExpansibleSign expansibleSign, Token under, Token over){
		DtoExpansibleUnderOverDimensions dimensions = measureDimensions(socket, expansibleSign, under, over);

		expandMiddlePanelWidthAccordingToContent(dimensions.getMiddlePanelSize(), dimensions.getUnderSize(), dimensions.getOverSize());
		
		return dimensions;
	}

	private DtoExpansibleUnderOverDimensions measureDimensions(InteractionSocket socket, ExpansibleSign expansibleSign, Token under, Token over) {
		Size leftPanelSize = expansibleSign.measureLeftPanel(socket);
		Size rightPanelSize = expansibleSign.measureRightPanel(socket);
		Size middlePanelSize = expansibleSign.measureMiddlePanel(socket);
		Size underSize = measureSizeOfToken(under, socket);
		Size overSize = measureSizeOfToken(over, socket);
		
		double tokenWidth = leftPanelSize.width + rightPanelSize.width + Doubles.max(underSize.width, overSize.width);
		
		double halfOfHighestPanelHeight = Doubles.max(leftPanelSize.height, rightPanelSize.height) / 2d;
		double halfOfMiddlePanelHeight = middlePanelSize.height / 2d;
		
		double overMiddleLineContentHeight = Doubles.max(overSize.height + halfOfMiddlePanelHeight, halfOfHighestPanelHeight);
		double underMiddleLineContentHeight = Doubles.max(underSize.height + halfOfMiddlePanelHeight, halfOfHighestPanelHeight);
		double tokenHeight = overMiddleLineContentHeight + underMiddleLineContentHeight;
		
		double middleLine = overMiddleLineContentHeight;
		
		Size summarizedSize = new Size(tokenWidth, tokenHeight, middleLine);
		
		return createDimensionsDto(leftPanelSize, rightPanelSize, middlePanelSize, underSize, overSize, halfOfHighestPanelHeight, halfOfMiddlePanelHeight,
				overMiddleLineContentHeight, underMiddleLineContentHeight, summarizedSize);
	}

	private void expandMiddlePanelWidthAccordingToContent(Size middlePanelSize, Size underSize, Size overSize) {
		double widestContentWidth = findWidest(underSize, overSize);
		middlePanelSize.width = widestContentWidth;
	}

	private DtoExpansibleUnderOverDimensions createDimensionsDto(Size leftPanelSize, Size rightPanelSize, Size middlePanelSize, Size underSize, Size overSize,
			double halfOfHighestPanelHeight, double halfOfMiddlePanelHeight, double overMiddleLineContentHeight, double underMiddleLineContentHeight,
			Size summarizedSize) {
		DtoExpansibleUnderOverDimensions dimensions = new DtoExpansibleUnderOverDimensions();
		dimensions.setHalfOfHighestPanelHeight(halfOfHighestPanelHeight);
		dimensions.setHalfOfMiddlePanelHeight(halfOfMiddlePanelHeight);
		dimensions.setLeftPanelSize(leftPanelSize);
		dimensions.setRightPanelSize(rightPanelSize);
		dimensions.setMiddlePanelSize(middlePanelSize);
		dimensions.setOverMiddleLineContentHeight(overMiddleLineContentHeight);
		dimensions.setUnderMiddleLineContentHeight(underMiddleLineContentHeight);
		dimensions.setOverSize(overSize);
		dimensions.setUnderSize(underSize);
		dimensions.setSummarizedSize(summarizedSize);
		return dimensions;
	}
	
	private double findWidest(Size underSize, Size overSize) {
		double widest = Doubles.max(underSize.width, overSize.width);
		return widest;
	}
	
	private Size measureSizeOfToken(Token token, InteractionSocket socket){
		Size size = null;
		if(token == null){
			size = new Size(0, 0, 0);
		}else{
			size = token.measure(socket);
		}
		return size;
	}
}
