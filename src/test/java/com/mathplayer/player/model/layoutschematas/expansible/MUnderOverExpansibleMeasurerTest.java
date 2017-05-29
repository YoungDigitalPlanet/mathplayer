package com.mathplayer.player.model.layoutschematas.expansible;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Token;
import com.mathplayer.player.model.signs.expansible.ExpansibleSign;

@SuppressWarnings("PMD")
public class MUnderOverExpansibleMeasurerTest {

	private MUnderOverExpansibleMeasurer mUnderOverExpansibleMeasurer = new MUnderOverExpansibleMeasurer();
	private InteractionSocket socket;
	private ExpansibleSign expansibleSign;
	private Token under;
	private Token over;
	
	private Size underSize;
	private Size overSize;
	private Size leftPanelSize;
	private Size middlePanelSize;
	private Size rightPanelSize;
	
	@Before
	public void setUp() throws Exception {
		setUpAllMocks();
		initializeSizes();
	}

	@Test
	public void testMeasure_leftPanelIsTheBiggest() {
		leftPanelSize = new Size(20, 10, 5);
		expectCallsToMocks();
		
		DtoExpansibleUnderOverDimensions result = mUnderOverExpansibleMeasurer.measure(socket, expansibleSign, under, over);
		
		Size expectedSummarizedSize = new Size(20, 10, 5);
		verifyResult(result, expectedSummarizedSize);
		
		double halfOfHighestPanelHeight = 5;
		double halfOfMiddlePanelHeight = 0;
		double overMiddleLineContentHeight = 5;
		double underMiddleLineContentHeight = 5;
		verifyAdditionalValues(result, halfOfHighestPanelHeight, halfOfMiddlePanelHeight, overMiddleLineContentHeight, underMiddleLineContentHeight);
	}
	
	@Test
	public void testMeasure_rightPanelIsBiggerThanLeft() {
		leftPanelSize = new Size(20, 10, 5);
		rightPanelSize = new Size(25, 20, 10);
		middlePanelSize = new Size(15, 10, 5);
		expectCallsToMocks();
		
		DtoExpansibleUnderOverDimensions result = mUnderOverExpansibleMeasurer.measure(socket, expansibleSign, under, over);
		
		Size expectedSummarizedSize = new Size(45, 20, 10);
		verifyResult(result, expectedSummarizedSize);
		
		double halfOfHighestPanelHeight = 10;
		double halfOfMiddlePanelHeight = 5;
		double overMiddleLineContentHeight = 10;
		double underMiddleLineContentHeight = 10;
		verifyAdditionalValues(result, halfOfHighestPanelHeight, halfOfMiddlePanelHeight, overMiddleLineContentHeight, underMiddleLineContentHeight);
	}
	
	@Test
	public void testMeasure_rightPanelIsBiggerThanLeft_underContentIsHigherThanRightPanel() {
		leftPanelSize = new Size(20, 10, 5);
		rightPanelSize = new Size(25, 20, 10);
		middlePanelSize = new Size(15, 10, 5);
		underSize = new Size(15, 30, 15);
		expectCallsToMocks();
		
		DtoExpansibleUnderOverDimensions result = mUnderOverExpansibleMeasurer.measure(socket, expansibleSign, under, over);
		
		Size expectedSummarizedSize = new Size(60, 45, 10);
		verifyResult(result, expectedSummarizedSize);
		
		double halfOfHighestPanelHeight = 10;
		double halfOfMiddlePanelHeight = 5;
		double overMiddleLineContentHeight = 10;
		double underMiddleLineContentHeight = 35;
		verifyAdditionalValues(result, halfOfHighestPanelHeight, halfOfMiddlePanelHeight, overMiddleLineContentHeight, underMiddleLineContentHeight);
	}
	
	@Test
	public void testMeasure_middlePanelIsHighest() {
		leftPanelSize = new Size(20, 10, 5);
		rightPanelSize = new Size(25, 20, 10);
		middlePanelSize = new Size(15, 30, 5);
		underSize = new Size(15, 10, 15);
		expectCallsToMocks();
		
		DtoExpansibleUnderOverDimensions result = mUnderOverExpansibleMeasurer.measure(socket, expansibleSign, under, over);
		
		Size expectedSummarizedSize = new Size(60, 40, 15);
		verifyResult(result, expectedSummarizedSize);
		
		double halfOfHighestPanelHeight = 10;
		double halfOfMiddlePanelHeight = 15;
		double overMiddleLineContentHeight = 15;
		double underMiddleLineContentHeight = 25;
		verifyAdditionalValues(result, halfOfHighestPanelHeight, halfOfMiddlePanelHeight, overMiddleLineContentHeight, underMiddleLineContentHeight);
	}
	
	@Test
	public void testMeasure_overContentIsHighestAndWiderThanUnderContent() {
		leftPanelSize = new Size(20, 10, 5);
		rightPanelSize = new Size(25, 20, 10);
		middlePanelSize = new Size(15, 10, 5);
		overSize = new Size(15, 30, 15);
		underSize = new Size(10, 5, 2.5d);
		expectCallsToMocks();
		
		DtoExpansibleUnderOverDimensions result = mUnderOverExpansibleMeasurer.measure(socket, expansibleSign, under, over);
		
		Size expectedSummarizedSize = new Size(60, 45, 35);
		verifyResult(result, expectedSummarizedSize);
		
		double halfOfHighestPanelHeight = 10;
		double halfOfMiddlePanelHeight = 5;
		double overMiddleLineContentHeight = 35;
		double underMiddleLineContentHeight = 10;
		verifyAdditionalValues(result, halfOfHighestPanelHeight, halfOfMiddlePanelHeight, overMiddleLineContentHeight, underMiddleLineContentHeight);
	}
	
	private void verifyResult(DtoExpansibleUnderOverDimensions result, Size expectedSummarizedSize) {
		assertEquals(underSize, result.getUnderSize());
		assertEquals(overSize, result.getOverSize());
		assertEquals(leftPanelSize, result.getLeftPanelSize());
		assertEquals(middlePanelSize, result.getMiddlePanelSize());
		assertEquals(rightPanelSize, result.getRightPanelSize());
		
		Size summarizedSize = result.getSummarizedSize();
		assertEquals(expectedSummarizedSize.height, summarizedSize.height, 0.0001d);
		assertEquals(expectedSummarizedSize.width, summarizedSize.width, 0.0001d);
		assertEquals(expectedSummarizedSize.middleLine, summarizedSize.middleLine, 0.0001d);
	}

	private void verifyAdditionalValues(DtoExpansibleUnderOverDimensions result, double halfOfHighestPanelHeight, double halfOfMiddlePanelHeight,	double overMiddleLineContentHeight,	double underMiddleLineContentHeight){
		assertEquals(halfOfHighestPanelHeight, result.getHalfOfHighestPanelHeight(), 0.0001d);
		assertEquals(halfOfMiddlePanelHeight, result.getHalfOfMiddlePanelHeight(), 0.0001d);
		assertEquals(overMiddleLineContentHeight, result.getOverMiddleLineContentHeight(), 0.0001d);
		assertEquals(underMiddleLineContentHeight, result.getUnderMiddleLineContentHeight(), 0.0001d);
	}

	private void expectCallsToMocks() {
		when(expansibleSign.measureLeftPanel(socket))
			.thenReturn(leftPanelSize);
		
		when(expansibleSign.measureMiddlePanel(socket))
			.thenReturn(middlePanelSize);
		
		when(expansibleSign.measureRightPanel(socket))
			.thenReturn(rightPanelSize);
		
		when(under.measure(socket))
			.thenReturn(underSize);
		
		when(over.measure(socket))
			.thenReturn(overSize);
	}

	private void initializeSizes() {
		underSize = new Size(0, 0, 0);
		overSize = new Size(0, 0, 0);
		leftPanelSize = new Size(0, 0, 0);
		middlePanelSize = new Size(0, 0, 0);
		rightPanelSize = new Size(0, 0, 0);
	}

	private void setUpAllMocks() {
		socket = Mockito.mock(InteractionSocket.class);
		expansibleSign = Mockito.mock(ExpansibleSign.class);
		under = Mockito.mock(Token.class);
		over = Mockito.mock(Token.class);
	}
}
