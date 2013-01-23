package com.mathplayer.player.model.layoutschematas.mmultiscripts;

import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;
import com.mathplayer.player.model.Token;

public class MMultiscriptsMeasurer {

	public DtoMMultiscriptsDimensions measure(InteractionSocket socket, DtoMultiscriptsTokens tokens) {
		DtoMMultiscriptsDimensions dimensions = measureTokenDimensions(tokens, socket);

		Size baseSize = tokens.getBase().measure(socket);
		dimensions.setBaseSize(baseSize);

		double widthOfLeftIndexes = findMaxWidthOfTokens(dimensions.getLsubSize(), dimensions.getLsupSize());
		double widthOfRightIndexes = findMaxWidthOfTokens(dimensions.getSubSize(), dimensions.getSupSize());
		dimensions.setWidthOfLeftIndexes(widthOfLeftIndexes);
		dimensions.setWidthOfRightIndexes(widthOfRightIndexes);

		double heigthOfTopIndexes = findMaxHeightOfTokens(dimensions.getLsupSize(), dimensions.getSupSize());
		double heigthOfBottomIndexes = findMaxHeightOfTokens(dimensions.getLsubSize(), dimensions.getSubSize());
		dimensions.setHeigthOfBottomIndexes(heigthOfBottomIndexes);
		dimensions.setHeigthOfTopIndexes(heigthOfTopIndexes);

		double upperIndexBottomLimitLineRate = MMultiscriptsProportionRates.UPPER_INDEX_BOTTOM_LIMIT_LINE_RATE.getRate();
		double necessaryTopAreaEnlargement = calculateNecessaryHeigthAreaEnlargement(baseSize.height, heigthOfTopIndexes, upperIndexBottomLimitLineRate);
		dimensions.setNecessaryTopAreaEnlargement(necessaryTopAreaEnlargement);

		double bottomIndexTopLimitLineRate = MMultiscriptsProportionRates.BOTTOM_INDEX_TOP_LIMIT_LINE_RATE.getRate();
		double necessaryBottomAreaEnlargement = calculateNecessaryHeigthAreaEnlargement(baseSize.height, heigthOfBottomIndexes, bottomIndexTopLimitLineRate);
		dimensions.setNecessaryBottomAreaEnlargement(necessaryBottomAreaEnlargement);

		Size size = calculateMultiscriptSize(baseSize, widthOfLeftIndexes, widthOfRightIndexes, necessaryTopAreaEnlargement, necessaryBottomAreaEnlargement);
		dimensions.setMultiscriptsSize(size);

		return dimensions;
	}

	private Size calculateMultiscriptSize(Size baseSize, double widthOfLeftIndexes, double widthOfRightIndexes, double necessaryTopAreaEnlargement,
			double necessaryBottomAreaEnlargement) {
		Size size = baseSize.clone();
		size.width = baseSize.width + widthOfLeftIndexes + widthOfRightIndexes;
		size.height = baseSize.height + necessaryTopAreaEnlargement + necessaryBottomAreaEnlargement;
		size.middleLine += necessaryTopAreaEnlargement;
		return size;
	}

	private DtoMMultiscriptsDimensions measureTokenDimensions(DtoMultiscriptsTokens tokens, InteractionSocket socket) {
		DtoMMultiscriptsDimensions dimensions = new DtoMMultiscriptsDimensions();

		Size subSize = findTokenSize(tokens.getSub(), socket);
		dimensions.setSubSize(subSize);

		Size supSize = findTokenSize(tokens.getSup(), socket);
		dimensions.setSupSize(supSize);

		Size leftSubSize = findTokenSize(tokens.getLeftSub(), socket);
		dimensions.setLsubSize(leftSubSize);

		Size leftSupSize = findTokenSize(tokens.getLeftSup(), socket);
		dimensions.setLsupSize(leftSupSize);

		return dimensions;
	}

	private Size findTokenSize(Token token, InteractionSocket socket) {
		if (token == null) {
			return new Size();
		} else {
			return token.measure(socket);
		}
	}

	private double findMaxWidthOfTokens(Size... sizes) {
		double maxWidth = 0d;
		for (Size size : sizes) {
			if (size.width > maxWidth) {
				maxWidth = size.width;
			}
		}
		return maxWidth;
	}

	private double findMaxHeightOfTokens(Size... sizes) {
		double maxHeigth = 0d;
		for (Size size : sizes) {
			if (size.height > maxHeigth) {
				maxHeigth = size.height;
			}
		}
		return maxHeigth;
	}

	private double calculateNecessaryHeigthAreaEnlargement(double baseHeight, double indexHeight, double reuseBaseHeigthRate) {
		double existingSpaceForIndex = baseHeight * reuseBaseHeigthRate;
		double missingSpace = indexHeight - existingSpaceForIndex;
		double necessaryHeigthAreaEnlargement;
		if (missingSpace < 0) {
			necessaryHeigthAreaEnlargement = 0;
		} else {
			necessaryHeigthAreaEnlargement = missingSpace;
		}

		return necessaryHeigthAreaEnlargement;
	}
}
