package com.mathplayer.player.model.layoutschematas.expansible;

import com.mathplayer.player.geom.Size;

public class DtoExpansibleUnderOverDimensions {

	private Size summarizedSize;
	private Size leftPanelSize;
	private Size rightPanelSize;
	private Size middlePanelSize;
	private Size underSize;
	private Size overSize;
	private double halfOfHighestPanelHeight;
	private double halfOfMiddlePanelHeight;
	private double overMiddleLineContentHeight;
	private double underMiddleLineContentHeight;
	
	public Size getSummarizedSize() {
		return summarizedSize;
	}
	public void setSummarizedSize(Size summarizedSize) {
		this.summarizedSize = summarizedSize;
	}
	public Size getLeftPanelSize() {
		return leftPanelSize;
	}
	public void setLeftPanelSize(Size leftPanelSize) {
		this.leftPanelSize = leftPanelSize;
	}
	public Size getRightPanelSize() {
		return rightPanelSize;
	}
	public void setRightPanelSize(Size rightPanelSize) {
		this.rightPanelSize = rightPanelSize;
	}
	public Size getMiddlePanelSize() {
		return middlePanelSize;
	}
	public void setMiddlePanelSize(Size middlePanelSize) {
		this.middlePanelSize = middlePanelSize;
	}
	public Size getUnderSize() {
		return underSize;
	}
	public void setUnderSize(Size underSize) {
		this.underSize = underSize;
	}
	public Size getOverSize() {
		return overSize;
	}
	public void setOverSize(Size overSize) {
		this.overSize = overSize;
	}
	public double getHalfOfHighestPanelHeight() {
		return halfOfHighestPanelHeight;
	}
	public void setHalfOfHighestPanelHeight(double halfOfHighestPanelHeight) {
		this.halfOfHighestPanelHeight = halfOfHighestPanelHeight;
	}
	public double getHalfOfMiddlePanelHeight() {
		return halfOfMiddlePanelHeight;
	}
	public void setHalfOfMiddlePanelHeight(double halfOfMiddlePanelHeight) {
		this.halfOfMiddlePanelHeight = halfOfMiddlePanelHeight;
	}
	public double getOverMiddleLineContentHeight() {
		return overMiddleLineContentHeight;
	}
	public void setOverMiddleLineContentHeight(double overMiddleLineContentHeight) {
		this.overMiddleLineContentHeight = overMiddleLineContentHeight;
	}
	public double getUnderMiddleLineContentHeight() {
		return underMiddleLineContentHeight;
	}
	public void setUnderMiddleLineContentHeight(double underMiddleLineContentHeight) {
		this.underMiddleLineContentHeight = underMiddleLineContentHeight;
	}
}
