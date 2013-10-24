package com.gcq.fivesecond.properties;

public class GameProperties {
	private boolean audioOn;
	private float volume;
	private int difficulty;
	public boolean isAudioOn() {
		return audioOn;
	}
	public void setAudioOn(boolean audioOn) {
		this.audioOn = audioOn;
	}
	public void setAudioOn(int audioOn) {
		if(audioOn==1){
			this.audioOn=true;
		}else{
			this.audioOn=false;
		}
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
