package com.gcq.fivesecond.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gcq.fivesecond.properties.GameProperties;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private DBHelper helper;
    private SQLiteDatabase db;
    
    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    
    public SQLiteDatabase getDB(){
    	if(db==null||!db.isOpen()){
    		return helper.getWritableDatabase();
    	}
    	return db;
    }
    
    public void updateVolume(float volume){
    	ContentValues cv = new ContentValues();
    	cv.put("volume", volume);
    	getDB().update("game_properties", cv, null, null);
    	closeDB();
    }
    
    public void updateDifficulty(int difficulty){
    	ContentValues cv = new ContentValues();
    	cv.put("difficulty", difficulty);
    	getDB().update("game_properties", cv, null, null);
    	closeDB();
    }
    
    public void updateAudioOn(boolean on){
    	ContentValues cv = new ContentValues();
    	cv.put("audio_on", on);
    	getDB().update("game_properties", cv, null, null);
    	closeDB();
    }
    
    public GameProperties getProperties(){
    	GameProperties pro=new GameProperties();
    	Cursor c = queryTheCursor("game_properties",null); 
        while (c.moveToNext()) {  
        	pro.setAudioOn(c.getInt(c.getColumnIndex("audio_on")));
        	pro.setVolume(c.getFloat(c.getColumnIndex("volume")));
        	pro.setDifficulty(c.getInt(c.getColumnIndex("difficulty")));
        }  
        c.close();
        closeDB();
        return pro;  
    }
    
    @SuppressLint("SimpleDateFormat")
	public ScoreRecord updateScore(int difficulty,int score) {
    	ContentValues cv = new ContentValues();
    	Date date = new Date();
    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time = dateformat.format(date);
    	ScoreRecord sr=new ScoreRecord(score,time,difficulty);
        cv.put("highest_score", score);
        cv.put("score_record_data",time);
        getDB().update("highest_score_record", cv, "difficulty=?", new String[]{String.valueOf(difficulty)});
        closeDB();
    	return sr;
    }
    
    public ScoreRecord getScoreRecord(int difficulty) {
    	ScoreRecord sr=new ScoreRecord();
        Cursor c = queryTheCursor("highest_score_record where difficulty=?",new String[]{String.valueOf(difficulty)}); 
        while (c.moveToNext()) {
        	sr.setHighest_score(c.getInt(c.getColumnIndex("highest_score")));
        	sr.setScore_record_data(c.getString(c.getColumnIndex("score_record_data")));
        	sr.setDifficulty(c.getInt(c.getColumnIndex("difficulty")));
        }
        c.close();
        closeDB();
        return sr;  
    }
    @SuppressLint("UseSparseArrays")
	public Map<Integer,ScoreRecord> getScoreRecords(){
    	 Map<Integer,ScoreRecord> map=new HashMap<Integer, ScoreRecord>();
    	 Cursor c = queryTheCursor("highest_score_record",null); 
         while (c.moveToNext()) {
        	ScoreRecord sr=new ScoreRecord();
         	sr.setHighest_score(c.getInt(c.getColumnIndex("highest_score")));
         	sr.setScore_record_data(c.getString(c.getColumnIndex("score_record_data")));
         	sr.setDifficulty(c.getInt(c.getColumnIndex("difficulty")));
         	map.put(sr.getDifficulty(), sr);
         }
         c.close();
         closeDB();
         return map;
    }
    private Cursor queryTheCursor(String table,String[] args) {
        Cursor c = getDB().rawQuery("SELECT * FROM "+table, args);  
        return c;  
    }
    
    public void closeDB() {
        db.close();  
    }
}
