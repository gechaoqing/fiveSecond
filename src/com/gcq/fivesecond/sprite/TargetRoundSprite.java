/*
 * -----------------------------------------------------------------------
 * Copyright 2012 - Alistair Rutherford - www.netthreads.co.uk
 * -----------------------------------------------------------------------
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.gcq.fivesecond.sprite;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.font.GameFont;
import com.netthreads.libgdx.action.ActionCallBack;
import com.netthreads.libgdx.action.CallBackDelayAction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;

/**
 * We encapsulate our ship sprite and the way it responds to changing position.
 * 
 */
public class TargetRoundSprite extends PositionSprite implements ActionCallBack
{
	private BitmapFont targetTimer;
	CallBackDelayAction callBackDelay;
	public static int TIMER_LAST=5;
	private Director director;

	public TargetRoundSprite(TextureRegion textureRegion, int rows, int cols, float frameDuration)
	{
		super(textureRegion, rows, cols, frameDuration);
		targetTimer=new BitmapFont(Gdx.files.internal(GameFont.FONT_GOTHIC_32_FILE), Gdx.files.internal(GameFont.FONT_GOTHIC_32_IMAGE), false);
		director = AppInjector.getInjector().getInstance(Director.class);
	}
	
	public void addTimer(){
		TIMER_LAST=5;
		callBackDelay = CallBackDelayAction.$(-1,
				1.0f, this);
		addAction(callBackDelay);
	}
	
	public void removeTimer(){
		removeAction(callBackDelay);
	}


	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		targetTimer.draw(batch, TIMER_LAST+"", getX()+getWidth()/2-targetTimer.getSpaceWidth()-3, getY()+getHeight()/2+targetTimer.getLineHeight()/3);
	}

	@Override
	public void onCallBack()
	{
		TIMER_LAST--;
		if(TIMER_LAST==0){
			removeTimer();
			this.director.sendEvent(AppEvents.EVENT_END_PLAYER_ROUND, this);
		}
	}
	

	

}
