package com.gcq.fivesecond.music;

public class MusicDefinition {

	private String name;
	private String path;
	public MusicDefinition(String name, String path) {
		this.name = name;
		this.path = path;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
