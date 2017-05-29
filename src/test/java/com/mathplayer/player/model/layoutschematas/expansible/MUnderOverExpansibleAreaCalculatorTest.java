package com.mathplayer.player.model.layoutschematas.expansible;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Size;

@SuppressWarnings("PMD")
public class MUnderOverExpansibleAreaCalculatorTest {

	private MUnderOverExpansibleAreaCalculator underOverExpansibleAreaCalculator = new MUnderOverExpansibleAreaCalculator();
	private double halfOfHighestPanelHeight;
	private double halfOfMiddlePanelHeight;
	private double overMiddleLineContentHeight;
	private double underMiddleLineContentHeight;
	private Size leftPanelSize;
	private Size rightPanelSize;
	private Size middlePanelSize;
	private Size overSize;
	private Size summarizedSize;
	private Size underSize;
	
	@Before
	public void setUp() throws Exception {
		halfOfHighestPanelHeight = 0;
		halfOfMiddlePanelHeight = 0;
		overMiddleLineContentHeight = 0;
		underMiddleLineContentHeight = 0;
		leftPanelSize = new Size(0, 0, 0);
		rightPanelSize = new Size(0, 0, 0);
		middlePanelSize = new Size(0, 0, 0);
		overSize = new Size(0, 0, 0);
		summarizedSize = new Size(0, 0, 0);
		underSize = new Size(0, 0, 0);
	}

	@Test
	public void testCalculateArea_shouldCalculateAreaForLeftPanel_withoutOffsetFromTop() {
		Area baseArea = new Area(5, 6);
		leftPanelSize = new Size(10,10,10);
		summarizedSize = new Size(10, 20, 5);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5;
		double expectedY = 6;
		verifyArea(expectedX, expectedY, result.getLeftPanelArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateAreaForLeftPanel_withOffsetFromTop() {
		Area baseArea = new Area(5, 6);
		leftPanelSize = new Size(10,10,10);
		summarizedSize = new Size(1234, 1234, 30);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5;
		double expectedY = 6 + 25;
		verifyArea(expectedX, expectedY, result.getLeftPanelArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateAreaForMiddlePanel_withoutOffsetFromTop() {
		Area baseArea = new Area(5, 6);
		leftPanelSize = new Size(10,10,10);
		middlePanelSize = new Size(5,5,5);
		summarizedSize = new Size(1234, 1234, 15);
		halfOfMiddlePanelHeight = 15;
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5 + leftPanelSize.width;
		double expectedY = 6;
		verifyArea(expectedX, expectedY, result.getMiddlePanelArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateAreaForMiddlePanel_withOffsetFromTop() {
		Area baseArea = new Area(5, 6);
		leftPanelSize = new Size(10,10,10);
		middlePanelSize = new Size(5,5,5);
		summarizedSize = new Size(1234, 1234, 30);
		halfOfMiddlePanelHeight = 15;
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5 + leftPanelSize.width;
		double expectedY = 6 + 15;
		verifyArea(expectedX, expectedY, result.getMiddlePanelArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateRightPanelArea_withoutOffsetFromTop() {
		Area baseArea = new Area(5, 6);
		leftPanelSize = new Size(10,10,10);
		middlePanelSize = new Size(5,5,5);
		summarizedSize = new Size(1234, 1234, 30);
		halfOfMiddlePanelHeight = 15;

		rightPanelSize = new Size(15, 60, 30);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5 + leftPanelSize.width + middlePanelSize.width;
		double expectedY = 6;
		verifyArea(expectedX, expectedY, result.getRightPanelArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateRightPanelArea_withOffsetFromTop() {
		Area baseArea = new Area(5, 6);
		leftPanelSize = new Size(10,10,10);
		middlePanelSize = new Size(5,5,5);
		summarizedSize = new Size(1234, 1234, 40);
		halfOfMiddlePanelHeight = 15;
		
		rightPanelSize = new Size(15, 60, 30);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5 + leftPanelSize.width + middlePanelSize.width;
		double expectedY = 6 + 10;
		verifyArea(expectedX, expectedY, result.getRightPanelArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateOverContentArea_withoutOffsetAndCenter() {
		Area baseArea = new Area(5, 6);
		middlePanelSize = new Size(20,30,15);
		summarizedSize = new Size(1234, 1234, 40);
		halfOfMiddlePanelHeight = 15;
		overSize = new Size(20, 25, 12.5d);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5;
		double expectedY = 6;
		verifyArea(expectedX, expectedY, result.getOverContentArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateOverContentArea_withOffsetAndCenter() {
		Area baseArea = new Area(5, 6);
		middlePanelSize = new Size(30,30,15);
		summarizedSize = new Size(1234, 1234, 40);
		halfOfMiddlePanelHeight = 15;
		overSize = new Size(20, 10, 12.5d);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5 + 5;
		double expectedY = 6 + 15;
		verifyArea(expectedX, expectedY, result.getOverContentArea());
		verifySizesCorrectlySet(result);
	}
	
	@Test
	public void testCalculateArea_shouldCalculateUnderContentArea_withCenter() {
		Area baseArea = new Area(5, 6);
		middlePanelSize = new Size(30,30,15);
		summarizedSize = new Size(1234, 1234, 15);
		halfOfMiddlePanelHeight = 15;
		underSize = new Size(20, 120, 60);
		
		DtoExpansibleUnderOverDimensions dimensions = createDimensions();
		DtoExpansibleUnderOverArea result = underOverExpansibleAreaCalculator.calculateArea(baseArea, dimensions);
		
		double expectedX = 5 + 5;
		double expectedY = 6 + middlePanelSize.height;
		verifyArea(expectedX, expectedY, result.getUnderContentArea());
		verifySizesCorrectlySet(result);
	}
	
	private void verifyArea(double expectedX, double expectedY, Area area) {
		assertEquals(expectedX, area.x, 0.0001d);
		assertEquals(expectedY, area.y, 0.0001d);
	}

	private void verifySizesCorrectlySet(DtoExpansibleUnderOverArea result) {
		verifySameSizes(leftPanelSize, result.getLeftPanelArea().getSize());
		verifySameSizes(rightPanelSize, result.getRightPanelArea().getSize());
		verifySameSizes(middlePanelSize, result.getMiddlePanelArea().getSize());
		verifySameSizes(underSize, result.getUnderContentArea().getSize());
		verifySameSizes(overSize, result.getOverContentArea().getSize());
	}

	private void verifySameSizes(Size expectedSize, Size currentSize) {
		assertEquals(expectedSize.height, currentSize.height, 0.0001d);
		assertEquals(expectedSize.width, currentSize.width, 0.0001d);
		assertEquals(expectedSize.middleLine, currentSize.middleLine, 0.0001d);
	}

	private DtoExpansibleUnderOverDimensions createDimensions() {
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

}
