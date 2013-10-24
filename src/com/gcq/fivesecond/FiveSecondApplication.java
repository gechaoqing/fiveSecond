package com.gcq.fivesecond;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gcq.fivesecond.game.FiveSecondGame;

public class FiveSecondApplication extends AndroidApplication {
	private int screenWidth ;  
	private int screenHeight ;
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics(); 
        dm = this.getApplicationContext().getResources().getDisplayMetrics();  
              screenWidth = dm.widthPixels;
              screenHeight = dm.heightPixels;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = true;
		config.useWakelock=true;
		initialize(new FiveSecondGame(this), config);
	}
	
	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}
}
