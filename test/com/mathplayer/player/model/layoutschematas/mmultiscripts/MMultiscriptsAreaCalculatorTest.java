package com.mathplayer.player.model.layoutschematas.mmultiscripts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;

public class MMultiscriptsAreaCalculatorTest {

	private MMultiscriptsAreaCalculator multiscriptsAreaCalculator = new MMultiscriptsAreaCalculator();
	private Size baseSize = new Size();
	private Size lsubSize = new Size();
	private Size lsupSize = new Size();
	private Size subSize = new Size();
	private Size supSize = new Size();
	private double heigthOfTopIndexes = 0d;
	private double widthOfLeftIndexes = 0d;
	private double necessaryTopAreaEnlargement = 0d;


	@Test
	public void testCalculateAreas_shouldCalculateAreaOfLeftSup() throws Exception {
		Area startArea = new Area(5d, 10d);
		widthOfLeftIndexes = 20d;
		heigthOfTopIndexes = 15d;
		lsupSize = new Size(5d, 5d, 0d);

		DtoMMultiscriptsDimensions dimensions = createDimensionsDto();

		DtoMultiscriptsArea areasDto = multiscriptsAreaCalculator.calculateAreas(startArea, dimensions);

		Area leftSupArea = areasDto.getLeftSup();
		assertEquals(startArea.x + (widthOfLeftIndexes - lsupSize.width), leftSupArea.x, 0.0001d);
		assertEquals(startArea.y + (heigthOfTopIndexes - lsupSize.height), leftSupArea.y, 0.0001d);
		assertSizeAndAreaParameters(lsupSize, leftSupArea);
	}

	@Test
	public void testCalculateAreas_shouldCalculateAreaOfLeftSub() throws Exception {
		Area startArea = new Area(5d, 10d);
		widthOfLeftIndexes = 20d;
		necessaryTopAreaEnlargement = 6d;
		baseSize = new Size(0d, 20d, 0d);
		lsubSize = new Size(5d, 0d, 0d);

		DtoMMultiscriptsDimensions dimensions = createDimensionsDto();

		DtoMultiscriptsArea areasDto = multiscriptsAreaCalculator.calculateAreas(startArea, dimensions);

		Area leftSubArea = areasDto.getLeftSub();
		assertEquals(startArea.x + (widthOfLeftIndexes - lsubSize.width), leftSubArea.x, 0.0001d);
		assertSizeAndAreaParameters(lsubSize, leftSubArea);
		
		double distanceRate = MMultiscriptsProportionRates.DISTANCE_BEETWEN_TOP_AND_BOTTOM_INDEX_RATE.getRate();
		double expectedYCoordinate = startArea.y + (baseSize.height / 2d) +(baseSize.height * (distanceRate /2d)) + necessaryTopAreaEnlargement;
		assertEquals(expectedYCoordinate, leftSubArea.y, 0.0001);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateAreaOfBaseElement() throws Exception {
		Area startArea = new Area(5d, 10d);
		widthOfLeftIndexes = 20d;
		necessaryTopAreaEnlargement = 6d;
		baseSize = new Size(0d, 20d, 0d);
		
		DtoMMultiscriptsDimensions dimensions = createDimensionsDto();

		DtoMultiscriptsArea areasDto = multiscriptsAreaCalculator.calculateAreas(startArea, dimensions);
		
		Area resultBaseArea = areasDto.getBase();
		assertSizeAndAreaParameters(baseSize, resultBaseArea);
		assertEquals(startArea.x + widthOfLeftIndexes, resultBaseArea.x, 0.0001d);
		assertEquals(startArea.y + necessaryTopAreaEnlargement, resultBaseArea.y, 0.0001d);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateAreaOfSup() throws Exception {
		Area startArea = new Area(5d, 10d);
		widthOfLeftIndexes = 20d;
		heigthOfTopIndexes = 15d;
		lsupSize = new Size(5d, 5d, 0d);
		supSize = new Size(123314d, 8d, 0d);
		baseSize = new Size(123d, 123, 123/2d);
		
		DtoMMultiscriptsDimensions dimensions = createDimensionsDto();

		DtoMultiscriptsArea areasDto = multiscriptsAreaCalculator.calculateAreas(startArea, dimensions);
		
		double expectedXCoordinate = widthOfLeftIndexes + baseSize.width + startArea.x;
		double expectedYCoordinate = (heigthOfTopIndexes - supSize.height) + startArea.y;
		
		Area resultSupArea = areasDto.getSup();
		assertEquals(expectedXCoordinate, resultSupArea.x, 0.0001d);
		assertEquals(expectedYCoordinate, resultSupArea.y, 0.0001d);
		assertSizeAndAreaParameters(supSize, resultSupArea);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateAreaOfSubIndex() throws Exception {
		Area startArea = new Area(5d, 10d);
		widthOfLeftIndexes = 20d;
		heigthOfTopIndexes = 15d;
		lsubSize = new Size(5d, 5d, 0d);
		subSize = new Size(123314d, 8d, 0d);
		baseSize = new Size(123d, 123, 123/2d);
		necessaryTopAreaEnlargement = 123123d;
		
		DtoMMultiscriptsDimensions dimensions = createDimensionsDto();
		
		DtoMultiscriptsArea areasDto = multiscriptsAreaCalculator.calculateAreas(startArea, dimensions);
		
		double expectedXCoordinate = widthOfLeftIndexes + baseSize.width + startArea.x;
		double distanceRate = MMultiscriptsProportionRates.DISTANCE_BEETWEN_TOP_AND_BOTTOM_INDEX_RATE.getRate();
		double spaceFromMiddleOfBaseElement = baseSize.height * (distanceRate / 2d);
		double expectedYCoordinate = (startArea.y + necessaryTopAreaEnlargement) + (baseSize.height / 2d) + spaceFromMiddleOfBaseElement;
		
		Area resultSubArea = areasDto.getSub();
		assertEquals(expectedXCoordinate, resultSubArea.x, 0.0001d);
		assertEquals(expectedYCoordinate, resultSubArea.y, 0.0001d);
		assertSizeAndAreaParameters(subSize, resultSubArea);
	}

	private void assertSizeAndAreaParameters(Size size, Area area) {
		assertEquals(size.height, area.height, 0.0001d);
		assertEquals(size.width, area.width, 0.0001d);
		assertEquals(size.middleLine, area.middleLine, 0.0001d);
	}

	private DtoMMultiscriptsDimensions createDimensionsDto() {
		DtoMMultiscriptsDimensions dimensions = new DtoMMultiscriptsDimensions();

		dimensions.setBaseSize(baseSize);
		dimensions.setHeigthOfTopIndexes(heigthOfTopIndexes);
		dimensions.setWidthOfLeftIndexes(widthOfLeftIndexes);
		dimensions.setNecessaryTopAreaEnlargement(necessaryTopAreaEnlargement);
		dimensions.setLsubSize(lsubSize);
		dimensions.setLsupSize(lsupSize);
		dimensions.setSubSize(subSize);
		dimensions.setSupSize(supSize);

		return dimensions;
	}
}
