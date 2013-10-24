package com.gcq.fivesecond.database;

public class ScoreRecord {

	private int highest_score;
	private String score_record_data;
	private int difficulty;
	public ScoreRecord(){}
	public ScoreRecord(int highest_score,String score_record_data,int difficulty){
		this.setHighest_score(highest_score);
		this.setScore_record_data(score_record_data);
		this.setDifficulty(difficulty);
	}
	public int getHighest_score() {
		return highest_score;
	}
	public void setHighest_score(int highest_score) {
		this.highest_score = highest_score;
	}
	public String getScore_record_data() {
		return score_record_data;
	}
	public void setScore_record_data(String score_record_data) {
		this.score_record_data = score_record_data;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	
	
}
