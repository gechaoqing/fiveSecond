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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.scene.RoundSceneHelper;
import com.netthreads.libgdx.action.ActionCallBack;
import com.netthreads.libgdx.action.CallBackDelayAction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;

/**
 * Represent a pulse sprite.
 * 
 */
public class PlayerRoundSprite extends PositionSprite implements ActionCallBack {
	private static final float CALLBACK_INTERVAL = 0.05f; // 50 milliseconds
	/**
	 * The one and only director.
	 */
	private Director director;
	private CallBackDelayAction callBackDelay;

	public PlayerRoundSprite(TextureRegion textureRegion, int rows, int cols,
			float frameDuration) {
		super(textureRegion, rows, cols, frameDuration);
		director = AppInjector.getInjector().getInstance(Director.class);
	}
	
	public void addCheck(){
		callBackDelay = CallBackDelayAction.$(-1,
				CALLBACK_INTERVAL, this);
		addAction(callBackDelay);
	}
	
	public void resetXY(){
		this.setX((director.getWidth() - getWidth() )/ 2);
		this.setY(4*getHeight()+30);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void onCallBack() {
		Stage stage = getStage();
		Actor actor = null;
		actor = RoundSceneHelper.roundIntersects(this.getX(), this.getY(),
				this.getWidth(), this.getHeight(), stage.getRoot(),
				TargetRoundSprite.class);
		if (actor != null) {
			actor.remove();
			synchronized (this) {
				this.director.sendEvent(AppEvents.EVENT_START_TARGET_ROUND_DISC,actor);
			}
		}
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this
				: null;
	}

}
