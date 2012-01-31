package com.mathplayer.player.interaction;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class PanelGap extends FlowPanel {

	protected TextBox gap;
	protected FlowPanel prefixPanel;
	protected FlowPanel sufixPanel;
	protected FlowPanel containerPanel;
	protected FlowPanel markPanel;
	
	public PanelGap(){
		super();
		setStyleName("math-player-gap-container");
		
		gap = new TextBox();
		gap.setStyleName("math-player-gap");
		
		containerPanel = new FlowPanel();
		containerPanel.setStyleName("math-player-gap-body");
		containerPanel.add(gap);
		
		prefixPanel = new FlowPanel();
		prefixPanel.setStyleName("math-player-gap-prefix");
		
		sufixPanel = new FlowPanel();
		sufixPanel.setStyleName("math-player-gap-sufix");
		
		markPanel = new FlowPanel();
		markPanel.setStyleName("math-player-gap-mark");
		
		add(markPanel);
		add(prefixPanel);
		add(containerPanel);
		add(sufixPanel);
	}
	
	public TextBox getGap(){
		return gap;
	}
	
	public void mark(boolean correct, boolean wrong){
		if (correct){
			markPanel.setStyleName("math-player-gap-mark-correct");				
		} else if (wrong){
			markPanel.setStyleName("math-player-gap-mark-wrong");
		} else {
			markPanel.setStyleName("math-player-gap-mark-none");
		}
	}
	
	public void unmark(){
		markPanel.setStyleName("math-player-gap-mark");			
	}
	
}
