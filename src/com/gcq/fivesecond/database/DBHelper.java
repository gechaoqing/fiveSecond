package com.gcq.fivesecond.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "gcq_five_second_80A358103043222926.db";

	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS highest_score_record"
				+ "(difficulty INTEGER PRIMARY KEY,highest_score INTEGER,score_record_data VARCHAR(20))");
		db.execSQL("INSERT INTO highest_score_record VALUES(5,0,'')");
		db.execSQL("INSERT INTO highest_score_record VALUES(10,0,'')");
		db.execSQL("INSERT INTO highest_score_record VALUES(15,0,'')");
		db.execSQL("CREATE TABLE IF NOT EXISTS game_properties"
				+ "(audio_on BOOLEAN,volume FLOAT,difficulty INTEGER)");
		db.execSQL("INSERT INTO game_properties VALUES(0,0.1,5)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
