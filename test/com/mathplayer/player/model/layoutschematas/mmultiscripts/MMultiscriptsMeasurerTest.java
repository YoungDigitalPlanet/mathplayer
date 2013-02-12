package com.mathplayer.player.model.layoutschematas.mmultiscripts;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Token;

@SuppressWarnings("PMD")
public class MMultiscriptsMeasurerTest {

	private MMultiscriptsMeasurer multiscriptsMeasurer = new MMultiscriptsMeasurer();
	private Size baseSize = new Size();
	private Size leftSubSize = new Size();
	private Size leftSupSize = new Size();
	private Size supSize = new Size();
	private Size subSize = new Size();
	
	private Token base;
	private Token leftSub;
	private Token leftSup;
	private Token sub;
	private Token sup;
	private InteractionSocket socket = Mockito.mock(InteractionSocket.class);
	
	@Test
	public void testMeasure_shouldCalculateHeigthOfTopIndexes() throws Exception {
		supSize = new Size(0d, 10d, 5d);
		leftSupSize = new Size(0d, 20d, 10d);
		
		prepareMockedTokens();
		
		sub = null;
		leftSub = null;
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		assertEquals(20d, dimensions.getHeigthOfTopIndexes(), 0.001d);
		assertEquals(0d, dimensions.getHeigthOfBottomIndexes(), 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateHeigthOfBottomIndexes() throws Exception {
		subSize = new Size(0d, 20d, 5d);
		leftSubSize = new Size(0d, 10d, 10d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		assertEquals(20d, dimensions.getHeigthOfBottomIndexes(), 0.001d);
		assertEquals(0d, dimensions.getHeigthOfTopIndexes(), 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateWidthOfLeftIndexes() throws Exception {
		leftSupSize = new Size(5d, 0d, 0d);
		leftSubSize = new Size(10d, 0d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		assertEquals(10d, dimensions.getWidthOfLeftIndexes(), 0.001d);
		assertEquals(0d, dimensions.getWidthOfRightIndexes(), 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateWidthOfRightIndexes() throws Exception {
		supSize = new Size(10d, 0d, 0d);
		subSize = new Size(5d, 0d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		assertEquals(10d, dimensions.getWidthOfRightIndexes(), 0.001d);
		assertEquals(0d, dimensions.getWidthOfLeftIndexes(), 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateNecessaryTopAreaEnlargement() throws Exception {
		supSize = new Size(0d, 5d, 0d);
		leftSupSize = new Size(0d, 10d, 0d);
		baseSize = new Size(0d, 10d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		double rate = MMultiscriptsProportionRates.UPPER_INDEX_BOTTOM_LIMIT_LINE_RATE.getRate();
		assertEquals(10d - (10d * rate), dimensions.getNecessaryTopAreaEnlargement(), 0.001d);
		assertEquals(0d, dimensions.getNecessaryBottomAreaEnlargement(), 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateNecessaryBottomAreaEnlargement() throws Exception {
		subSize = new Size(0d, 5d, 0d);
		leftSubSize = new Size(0d, 10d, 0d);
		baseSize = new Size(0d, 10d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		double rate = MMultiscriptsProportionRates.BOTTOM_INDEX_TOP_LIMIT_LINE_RATE.getRate();
		assertEquals(10d - (10d * rate), dimensions.getNecessaryBottomAreaEnlargement(), 0.001d);
		assertEquals(0d, dimensions.getNecessaryTopAreaEnlargement(), 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateMultiscriptHeight() throws Exception {
		baseSize = new Size(0d, 10d, 0d);
		
		subSize = new Size(0d, 4d, 0d);
		leftSubSize = new Size(0d, 8d, 0d);
		
		supSize = new Size(0d, 5d, 0d);
		leftSupSize = new Size(0d, 12d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		double distanceRate = MMultiscriptsProportionRates.DISTANCE_BEETWEN_TOP_AND_BOTTOM_INDEX_RATE.getRate();
		double expectedHeight = 8d + 12d + (distanceRate * 10d);

		assertEquals(expectedHeight, dimensions.getMultiscriptsSize().height, 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateMultiscriptMiddleLine() throws Exception {
		baseSize = new Size(0d, 10d, 5d);
		
		subSize = new Size(0d, 40d, 0d);
		leftSubSize = new Size(0d, 8d, 0d);
		
		supSize = new Size(0d, 5d, 0d);
		leftSupSize = new Size(0d, 12d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		double rate = MMultiscriptsProportionRates.UPPER_INDEX_BOTTOM_LIMIT_LINE_RATE.getRate();
		double expectedMiddleLine = (leftSupSize.height - (baseSize.height * rate)) + baseSize.middleLine;
		
		assertEquals(expectedMiddleLine, dimensions.getMultiscriptsSize().middleLine, 0.001d);
	}
	
	@Test
	public void testMeasure_shouldCalculateMultiscriptWidth() throws Exception {
		baseSize = new Size(10d, 0d, 0d);
		
		subSize = new Size(4d, 0d, 0d);
		supSize = new Size(5d, 0d, 0d);
		
		leftSubSize = new Size(8d, 0d, 0d);
		leftSupSize = new Size(12d, 0d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		double expectedWidth = 10d + 5d + 12d;
		
		assertEquals(expectedWidth, dimensions.getMultiscriptsSize().width, 0.001d);
	}
	
	@Test
	public void testMeasuer_shouldVerifyCorrectSizesAssignment() throws Exception {
		baseSize = new Size(10d, 0d, 0d);
		subSize = new Size(4d, 0d, 0d);
		supSize = new Size(5d, 0d, 0d);
		leftSubSize = new Size(8d, 0d, 0d);
		leftSupSize = new Size(12d, 0d, 0d);
		
		prepareMockedTokens();
		
		DtoMultiscriptsTokens tokens = new DtoMultiscriptsTokens(base, sup, sub, leftSub, leftSup);
		DtoMMultiscriptsDimensions dimensions = multiscriptsMeasurer.measure(socket, tokens);
		
		assertEquals(baseSize, dimensions.getBaseSize());
		assertEquals(subSize, dimensions.getSubSize());
		assertEquals(supSize, dimensions.getSupSize());
		assertEquals(leftSubSize, dimensions.getLsubSize());
		assertEquals(leftSupSize, dimensions.getLsupSize());
	}

	private void prepareMockedTokens() {
		base = Mockito.mock(Token.class);
		when(base.measure(socket))
			.thenReturn(baseSize);
			
		leftSub = Mockito.mock(Token.class);
		when(leftSub.measure(socket))
			.thenReturn(leftSubSize);
		
		leftSup = Mockito.mock(Token.class);
		when(leftSup.measure(socket))
			.thenReturn(leftSupSize);
		
		sub = Mockito.mock(Token.class);
		when(sub.measure(socket))
			.thenReturn(subSize);
		
		sup = Mockito.mock(Token.class);
		when(sup.measure(socket))
			.thenReturn(supSize);
	}
}
