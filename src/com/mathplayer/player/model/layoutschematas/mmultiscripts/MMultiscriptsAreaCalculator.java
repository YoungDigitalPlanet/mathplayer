package com.mathplayer.player.model.layoutschematas.mmultiscripts;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;

public class MMultiscriptsAreaCalculator {

	public DtoMultiscriptsArea calculateAreas(Area startArea, DtoMMultiscriptsDimensions dimensions){
		
		Area baseArea = calculateBaseArea(startArea, dimensions);
		Area leftSupArea = calculateLeftSupArea(startArea, dimensions);
		
		double rightIndexesXCoordinate = calculateXCoordinateOfRightIndexes(startArea.x, dimensions);
		double bottomIndexesYCoordinate = calculateYCooridnateOfBottomIndexes(baseArea);
		
		Area leftSubArea = calculateLeftSubArea(startArea, bottomIndexesYCoordinate, dimensions);
		Area supArea = calculateSupArea(startArea.y, rightIndexesXCoordinate, dimensions);
		Area subArea = calculateSubArea(bottomIndexesYCoordinate, rightIndexesXCoordinate, dimensions.getSubSize());
		
		DtoMultiscriptsArea multiscriptsArea = createDtoMultiscriptArea(baseArea, leftSupArea, leftSubArea, supArea, subArea);
		
		return multiscriptsArea;
	}

	private Area calculateBaseArea(Area startArea, DtoMMultiscriptsDimensions dimensions) {
		double widthOfLeftIndexes = dimensions.getWidthOfLeftIndexes();
		double necessaryTopAreaEnlargement = dimensions.getNecessaryTopAreaEnlargement();
		Size baseSize = dimensions.getBaseSize();
		
		double xCoordinate = startArea.x + widthOfLeftIndexes;
		double yCoordinate = startArea.y + necessaryTopAreaEnlargement;
		
		return new Area(xCoordinate, yCoordinate, baseSize);
	}

	private Area calculateLeftSupArea(Area startArea, DtoMMultiscriptsDimensions dimensions) {
		double widthOfLeftIndexes = dimensions.getWidthOfLeftIndexes();
		double heigthOfTopIndexes = dimensions.getHeigthOfTopIndexes();
		Size lsupSize = dimensions.getLsupSize();
		
		double xCoordinateShift = widthOfLeftIndexes - lsupSize.width;
		double xCoordinate = startArea.x + xCoordinateShift;
		
		double yCoordinateShift = heigthOfTopIndexes - lsupSize.height;
		double yCoordinate = startArea.y + yCoordinateShift;
		
		Area leftSupArea = new Area(xCoordinate, yCoordinate, lsupSize);
		return leftSupArea;
	}
	
	private double calculateXCoordinateOfRightIndexes(double startAreaX, DtoMMultiscriptsDimensions dimensions) {
		double widthOfLeftIndexes = dimensions.getWidthOfLeftIndexes();
		double widthOfBaseElement = dimensions.getBaseSize().width;

		double xCoordinate = startAreaX + widthOfLeftIndexes + widthOfBaseElement;
		return xCoordinate;
	}

	private double calculateYCooridnateOfBottomIndexes(Area baseArea) {
		double baseElementHeight = baseArea.height;
		double distanceBeetwenIndexesRate = MMultiscriptsProportionRates.DISTANCE_BEETWEN_TOP_AND_BOTTOM_INDEX_RATE.getRate();
		
		double halfOfDistanceBeetwenIndexes = (distanceBeetwenIndexesRate / 2d) * baseElementHeight;
		double halfOfBaseElementHeight = baseElementHeight / 2;
		
		double yCoordinate = baseArea.y + halfOfBaseElementHeight + halfOfDistanceBeetwenIndexes;
		return yCoordinate;
	}
	
	private Area calculateLeftSubArea(Area startArea, double bottomIndexesYCoordinate, DtoMMultiscriptsDimensions dimensions) {
		double widthOfLeftIndexes = dimensions.getWidthOfLeftIndexes();
		Size lsubSize = dimensions.getLsubSize();
		
		double xCoordinateShift = widthOfLeftIndexes - lsubSize.width;
		double xCoordinate = startArea.x + xCoordinateShift;
		
		return new Area(xCoordinate, bottomIndexesYCoordinate, lsubSize);
	}
	
	private Area calculateSubArea(double bottomIndexesYCoordinate, double rightIndexesXCoordinate, Size subSize) {
		return new Area(rightIndexesXCoordinate, bottomIndexesYCoordinate, subSize);
	}

	private Area calculateSupArea(double startAreaY, double rightIndexesXCoordinate, DtoMMultiscriptsDimensions dimensions) {
		Size supSize = dimensions.getSupSize();
		double heigthOfTopIndexes = dimensions.getHeigthOfTopIndexes();
		double yCoordinateShift = heigthOfTopIndexes - supSize.height;
		double yCoordinate = startAreaY + yCoordinateShift;
		
		return new Area(rightIndexesXCoordinate, yCoordinate, supSize);
	}

	
	private DtoMultiscriptsArea createDtoMultiscriptArea(Area baseArea, Area leftSupArea, Area leftSubArea, Area supArea, Area subArea) {
		DtoMultiscriptsArea multiscriptsArea = new DtoMultiscriptsArea();
		multiscriptsArea.setBase(baseArea);
		multiscriptsArea.setLeftSub(leftSubArea);
		multiscriptsArea.setLeftSup(leftSupArea);
		multiscriptsArea.setSup(supArea);
		multiscriptsArea.setSub(subArea);
		return multiscriptsArea;
	}
}
