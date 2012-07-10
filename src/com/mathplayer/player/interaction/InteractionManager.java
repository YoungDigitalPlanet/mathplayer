package com.mathplayer.player.interaction;

import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mathplayer.player.geom.Area;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.model.interaction.CustomFieldDescription;

public class InteractionManager implements InteractionSocket {

	private Vector<Point> gapPositions;
	private Vector<CustomFieldDescription> customFieldDescriptions;
	private Vector<PanelGap> gaps;
	private AbsolutePanel container;
	private AbsolutePanel canvasLayer;
	private AbsolutePanel uiLayer;
	private PanelGap testGap;

	private int actualGapWidth;
	private int actualGapHeight;
	private int userGapWidth;
	private int userGapHeight;

	private Map<GapIdentifier, Integer> userCustomFieldWidth;
	private Map<GapIdentifier, Integer> userCustomFieldHeight;
	
	private final static int DEFAULT_CUSTOM_FIELD_WIDTH = 48;
	private final static int DEFAULT_CUSTOM_FIELD_HEIGHT = 20;
	
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

		gapPositions = new Vector<Point>();
		gaps = new Vector<PanelGap>();
		customFieldDescriptions = new Vector<CustomFieldDescription>();

		userGapWidth = 26;
		userGapHeight = 14;
		actualGapWidth = 32;
		actualGapHeight = 20;
	}

	@Override
	public void addGap(int x, int y) {
		gapPositions.add(new Point(x, y));		
	}

	@Override
	public void addCustomField(int x, int y) {
		customFieldDescriptions.add(new CustomFieldDescription(new Point(x, y)));		
	}
	
	public void process(){
		for (Point p : gapPositions){
			PanelGap pg = new PanelGap();
			pg.getGap().setWidth(String.valueOf(userGapWidth) + "px");
			pg.getGap().setHeight(String.valueOf(userGapHeight) + "px");
			gaps.add(pg);
			uiLayer.add(pg, p.x, p.y);
		}
	}
	@Override
	public void setCanvas(Widget canvas, int width, int height) {
		container.setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		container.getParent().setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		canvasLayer.setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		uiLayer.setSize(String.valueOf(width)+"px", String.valueOf(height)+"px");
		canvasLayer.add(canvas, 0, 0);

	}

	public void removeTextBox() {
		actualGapWidth = testGap.getGap().getOffsetWidth();
		actualGapHeight = testGap.getGap().getOffsetHeight();
		RootPanel.get().remove(testGap);
	}

	public void setTextBoxHeight(int h){
		if (h >= 0){
			userGapHeight = h;
			testGap.getGap().setHeight(String.valueOf(userGapHeight)+"px");
		}
	}

	@Override
	public int getTextBoxHeight() {
		return actualGapHeight;
	}

	public void setTextBoxWidth(int w){
		if (w >= 0){
			userGapWidth = w;
			testGap.getGap().setWidth(String.valueOf(userGapWidth)+"px");
		}
	}


	public void setCustomFieldWidths(Map<GapIdentifier, Integer> customFieldWidths){
		userCustomFieldWidth = customFieldWidths;
	}
	
	@Override
	public int getCustomFieldWidth(GapIdentifier gid) {
		if (userCustomFieldWidth.containsKey(gid))
			return userCustomFieldWidth.get(gid);
		return DEFAULT_CUSTOM_FIELD_WIDTH;
	}

	public void setCustomFieldHeights(Map<GapIdentifier, Integer> customFieldHeights){
		userCustomFieldHeight = customFieldHeights;
	}

	@Override
	public int getCustomFieldHeight(GapIdentifier gid) {
		if (userCustomFieldHeight.containsKey(gid))
			return userCustomFieldHeight.get(gid);
		return DEFAULT_CUSTOM_FIELD_HEIGHT;
	}

	@Override
	public int getTextBoxWidth() {
		return actualGapWidth;
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
	
	public Vector<CustomFieldDescription> getCustomFieldDescriptions(){
		return customFieldDescriptions;
	}

	@Override
	public void setCursorPosition(Area area) {
	}

	@Override
	public boolean isEditorMode() {
		return false;
	}
}
