package com.mathplayer.player.interaction;

import java.util.Vector;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Point;

public class InteractionManager implements InteractionSocket {

	private Vector<Point> positions;
	private Vector<PanelGap> gaps;
	private AbsolutePanel container;
	private AbsolutePanel canvasLayer;
	private AbsolutePanel uiLayer;
	private PanelGap testGap;
	
	private int gapWidth;
	private int gapHeight;
	
	public InteractionManager(Panel owner){
		container = new AbsolutePanel();
		container.setStyleName("math-player-container");
		canvasLayer = new AbsolutePanel();
		canvasLayer.setStyleName("math-player-canvas");
		uiLayer = new AbsolutePanel();
		uiLayer.setStyleName("math-player-ui");
		
		container.add(canvasLayer, 0, 0);
		container.add(uiLayer, 0, 0);
		
		owner.add(container);

		testGap = new PanelGap();
		testGap.setStyleName("math-player-gap");
		RootPanel.get().add(testGap, 0, 0);

		positions = new Vector<Point>();
		gaps = new Vector<PanelGap>();
	}

	@Override
	public void addGap(int x, int y) {
		positions.add(new Point(x, y));		
	}
	
	public void process(){
		for (Point p : positions){
			PanelGap pg = new PanelGap();
			gaps.add(pg);
			uiLayer.add(pg, p.x, p.y);
		}
	}
	@Override
	public void setCanvas(Widget canvas, int width, int height) {
		container.setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		canvasLayer.setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		uiLayer.setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		canvasLayer.add(canvas, 0, 0);
		
	}

	@Override
	public int getTextBoxHeight() {
		return gapHeight;
	}

	@Override
	public int getTextBoxWidth() {
		return gapWidth;
	}

	@Override
	public void removeTextBox() {
		gapWidth = testGap.getGap().getOffsetWidth();
		gapHeight = testGap.getGap().getOffsetHeight();
		RootPanel.get().remove(testGap);
	}
	
	public int getGapsCount(){
		return gaps.size();
	}
	
	public TextBox getGapAt(int i){
		if (i < gaps.size())
			return gaps.get(i).getGap();
		return null;
	}
	
	public void markGap(int index, boolean correct, boolean wrong){
		if (index < gaps.size())
			gaps.get(index).mark(correct, wrong);		
	}
	
	public void unmarkGap(int index){
		if (index < gaps.size())
			gaps.get(index).unmark();		
	}

	@Override
	public void setCursorPosition(Area area) {
	}

	@Override
	public boolean isEditorMode() {
		return false;
	}
}
