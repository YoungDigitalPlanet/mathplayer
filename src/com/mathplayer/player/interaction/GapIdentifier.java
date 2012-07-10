package com.mathplayer.player.interaction;

import java.util.Arrays;
import java.util.List;

import eu.ydp.gwtutil.client.util.Matchable;

public final class GapIdentifier extends Matchable {

	private String type;
	private String id;

	private GapIdentifier(){
	}
	
	public static GapIdentifier createTypeIdentifier(String type){
		GapIdentifier gid = new GapIdentifier();
		gid.type = type;
		return gid;
	}
	
	public static GapIdentifier createIdIdentifier(String id){
		GapIdentifier gid = new GapIdentifier();
		gid.id = id;
		return gid;
	}
	
	public static GapIdentifier createCombinedIdentifier(String type, String id){
		GapIdentifier gid = new GapIdentifier();
		gid.id = id;
		gid.type = type;
		return gid;
	}
		
	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	@Override
	public List<Object> getMatchValues() {
		return Arrays.asList(new Object[]{getId(), getType()});
	} 
	
}
