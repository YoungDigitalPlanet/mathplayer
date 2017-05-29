package com.mathplayer.player.model.layoutschematas.mmultiscripts;

import com.mathplayer.player.geom.Size;

public class DtoMMultiscriptsDimensions {

	private Size multiscriptsSize;
	private Size baseSize;
	private Size lsubSize;
	private Size lsupSize;
	private Size subSize;
	private Size supSize;
	
	private double widthOfLeftIndexes;
	private double widthOfRightIndexes;
	private double heigthOfTopIndexes;
	private double heigthOfBottomIndexes;
	private double necessaryTopAreaEnlargement;
	private double necessaryBottomAreaEnlargement;
	
	public Size getMultiscriptsSize() {
		return multiscriptsSize;
	}
	public void setMultiscriptsSize(Size multiscriptsSize) {
		this.multiscriptsSize = multiscriptsSize;
	}
	public Size getBaseSize() {
		return baseSize;
	}
	public void setBaseSize(Size baseSize) {
		this.baseSize = baseSize;
	}
	public Size getLsubSize() {
		return lsubSize;
	}
	public void setLsubSize(Size lsubSize) {
		this.lsubSize = lsubSize;
	}
	public Size getLsupSize() {
		return lsupSize;
	}
	public void setLsupSize(Size lsupSize) {
		this.lsupSize = lsupSize;
	}
	public Size getSubSize() {
		return subSize;
	}
	public void setSubSize(Size subSize) {
		this.subSize = subSize;
	}
	public Size getSupSize() {
		return supSize;
	}
	public void setSupSize(Size supSize) {
		this.supSize = supSize;
	}
	public double getWidthOfLeftIndexes() {
		return widthOfLeftIndexes;
	}
	public void setWidthOfLeftIndexes(double widthOfLeftIndexes) {
		this.widthOfLeftIndexes = widthOfLeftIndexes;
	}
	public double getWidthOfRightIndexes() {
		return widthOfRightIndexes;
	}
	public void setWidthOfRightIndexes(double widthOfRightIndexes) {
		this.widthOfRightIndexes = widthOfRightIndexes;
	}
	public double getHeigthOfTopIndexes() {
		return heigthOfTopIndexes;
	}
	public void setHeigthOfTopIndexes(double heigthOfTopIndexes) {
		this.heigthOfTopIndexes = heigthOfTopIndexes;
	}
	public double getHeigthOfBottomIndexes() {
		return heigthOfBottomIndexes;
	}
	public void setHeigthOfBottomIndexes(double heigthOfBottomIndexes) {
		this.heigthOfBottomIndexes = heigthOfBottomIndexes;
	}
	public double getNecessaryTopAreaEnlargement() {
		return necessaryTopAreaEnlargement;
	}
	public void setNecessaryTopAreaEnlargement(double necessaryTopAreaEnlargement) {
		this.necessaryTopAreaEnlargement = necessaryTopAreaEnlargement;
	}
	public double getNecessaryBottomAreaEnlargement() {
		return necessaryBottomAreaEnlargement;
	}
	public void setNecessaryBottomAreaEnlargement(double necessaryBottomAreaEnlargement) {
		this.necessaryBottomAreaEnlargement = necessaryBottomAreaEnlargement;
	}
}
