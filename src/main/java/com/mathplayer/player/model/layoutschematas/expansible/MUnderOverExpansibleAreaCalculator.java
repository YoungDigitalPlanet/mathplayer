package com.mathplayer.player.model.layoutschematas.expansible;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;

public class MUnderOverExpansibleAreaCalculator {

	public DtoExpansibleUnderOverArea calculateArea(Area baseArea, DtoExpansibleUnderOverDimensions dimensions) {

		Size leftPanelSize = dimensions.getLeftPanelSize();
		double middleLineOfExpression = dimensions.getSummarizedSize().middleLine;

		Area leftPanelArea = calculateLeftPanelArea(baseArea, leftPanelSize, middleLineOfExpression);
		Area middlePanelArea = calculateMiddlePanelArea(baseArea, middleLineOfExpression, dimensions);
		Area rightPanelArea = calculateRightPanelArea(baseArea, middlePanelArea, dimensions, middleLineOfExpression);

		Area overContentArea = calculateOverContentArea(middlePanelArea, dimensions);
		Area underContentArea = calculateUnderContentArea(middlePanelArea, dimensions);

		DtoExpansibleUnderOverArea dtoExpansibleUnderOverArea = createAreasDto(leftPanelArea, middlePanelArea, rightPanelArea, overContentArea, underContentArea);

		return dtoExpansibleUnderOverArea;
	}

	private DtoExpansibleUnderOverArea createAreasDto(Area leftPanelArea, Area middlePanelArea, Area rightPanelArea, Area overContentArea, Area underContentArea) {
		DtoExpansibleUnderOverArea dtoExpansibleUnderOverArea = new DtoExpansibleUnderOverArea();
		dtoExpansibleUnderOverArea.setLeftPanelArea(leftPanelArea);
		dtoExpansibleUnderOverArea.setMiddlePanelArea(middlePanelArea);
		dtoExpansibleUnderOverArea.setOverContentArea(overContentArea);
		dtoExpansibleUnderOverArea.setUnderContentArea(underContentArea);
		dtoExpansibleUnderOverArea.setRightPanelArea(rightPanelArea);
		return dtoExpansibleUnderOverArea;
	}

	private Area calculateRightPanelArea(Area baseArea, Area middlePanelArea, DtoExpansibleUnderOverDimensions dimensions, double middleLineOfExpression) {
		Size middlePanelSize = dimensions.getMiddlePanelSize();
		double xCoordinate = middlePanelArea.x + middlePanelSize.width;

		Size rightPanelSize = dimensions.getRightPanelSize();
		double offsetFromTopEdge = calculateOffsetOfSidePanelToTopEdge(middleLineOfExpression, rightPanelSize.height);
		double yCoordinate = baseArea.y + offsetFromTopEdge;

		Area rightPanelArea = new Area(xCoordinate, yCoordinate, rightPanelSize);
		return rightPanelArea;
	}

	private Area calculateLeftPanelArea(Area baseArea, Size leftPanelSize, double middleLineOfExpression) {
		double offsetFromTopEdge = calculateOffsetOfSidePanelToTopEdge(middleLineOfExpression, leftPanelSize.height);
		double yCoordinate = baseArea.y + offsetFromTopEdge;

		double xCoordinate = baseArea.x;

		Area leftPanelArea = new Area(xCoordinate, yCoordinate, leftPanelSize);
		return leftPanelArea;
	}

	private double calculateOffsetOfSidePanelToTopEdge(double middleLineOfExpresion, double panelHeight) {
		double halfOfPanelHeight = panelHeight / 2d;
		double offsetFromTopEdge = middleLineOfExpresion - halfOfPanelHeight;

		return offsetFromTopEdge;
	}

	private Area calculateMiddlePanelArea(Area baseArea, double middleLineOfExpression, DtoExpansibleUnderOverDimensions dimensions) {
		double halfOfMiddlePanelHeight = dimensions.getHalfOfMiddlePanelHeight();
		double offsetFromTopOfExpresion = middleLineOfExpression - halfOfMiddlePanelHeight;
		double yCoordinate = baseArea.y + offsetFromTopOfExpresion;

		Size leftPanelSize = dimensions.getLeftPanelSize();
		double xCoordinate = baseArea.x + leftPanelSize.width;

		Area middlePanelArea = new Area(xCoordinate, yCoordinate, dimensions.getMiddlePanelSize());
		return middlePanelArea;
	}

	private Area calculateOverContentArea(Area middlePanelArea, DtoExpansibleUnderOverDimensions dimensions) {
		Size overSize = dimensions.getOverSize();

		double widthOfMiddlePanel = dimensions.getMiddlePanelSize().width;
		double centerAdjustment = calculateCenterAdjustment(overSize.width, widthOfMiddlePanel);
		
		double xCoordinate = middlePanelArea.x + centerAdjustment;
		double yCoordinate = middlePanelArea.y - overSize.height;

		Area overContentArea = new Area(xCoordinate, yCoordinate, overSize);
		return overContentArea;
	}

	private double calculateCenterAdjustment(double contentWidth, double widthOfMiddlePanel) {
		double centerAdjustment = (widthOfMiddlePanel - contentWidth) / 2d;
		return centerAdjustment;
	}

	private Area calculateUnderContentArea(Area middlePanelArea, DtoExpansibleUnderOverDimensions dimensions) {
		Size middlePanelSize = dimensions.getMiddlePanelSize();
		Size underSize = dimensions.getUnderSize();

		double centerAdjustment = calculateCenterAdjustment(underSize.width, middlePanelSize.width);
		double xCoordinate = middlePanelArea.x + centerAdjustment;
		double yCoordinate = middlePanelArea.y + middlePanelSize.height;

		Area underContentArea = new Area(xCoordinate, yCoordinate, underSize);
		return underContentArea;
	}

}
