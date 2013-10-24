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

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Circ;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Quart;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.layer.RedRoundLayer;
import com.gcq.fivesecond.scene.RoundSceneHelper;
import com.netthreads.libgdx.action.ActionCallBack;
import com.netthreads.libgdx.action.CallBackDelayAction;
import com.netthreads.libgdx.action.TimelineAction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.SceneHelper;
import com.netthreads.libgdx.sprite.AnimatedSprite;
import com.netthreads.libgdx.tween.ActorAccessor;

/**
 * Represent an asteroid sprite.
 * 
 * An asteroid rotates at a random speed as it moves from it's starting point to
 * the bottom.
 * 
 * An asteroid checks for collision with other actors and generates the
 * appropriate event.
 * 
 */
public class RedRoundSprite extends AnimatedSprite implements TweenCallback,
		ActionCallBack {
	private static final int MIN_DURATION = 2;
	private static final int MAX_DURATION = 2;
	private static final float CALLBACK_INTERVAL = 0.05f; // 50 milliseconds
	private static final TweenEquation[] EQUATIONS = { Linear.INOUT, Cubic.OUT,
			Circ.OUT, Quad.OUT, Quart.OUT, Sine.OUT };
	private float[] orig = { 0, 0 };
	
	private RedRoundLayer rrl;

	/**
	 * The one and only director.
	 */
	private Director director;
	
	private CallBackDelayAction callBackDelay;
	
	public CallBackDelayAction callBack(){
		return callBackDelay;
	}

	public RedRoundSprite(TextureRegion textureRegion, int rows, int cols,
			float frameDuration,RedRoundLayer rrl) {
		super(textureRegion, rows, cols, frameDuration);
		this.rrl=rrl;
		director = AppInjector.getInjector().getInstance(Director.class);
		orig[0] = (director.getWidth() - getWidth()) / 2;
		orig[1] = (director.getHeight() - getHeight()) / 2;
		this.setPosition(orig[0], orig[1]);
	}

	public void resetXY() {
		this.setPosition(orig[0], orig[1]);
	}

	public float[] getOrig() {
		return orig;
	}

	public void setOrig(float[] orig) {
		this.orig = orig;
	}

	/**
	 * Run actions for actor.
	 * 
	 * 
	 * @return The main actions object.
	 */
	public void run() {
		float width = director.getWidth();
		float height = director.getHeight();
		float randomX = (float) (Math.random() * (width / 2));
		float randomY = (float) (Math.random() * (height / 2));

		// ---------------------------------------------------------------
		// Animation.
		// ---------------------------------------------------------------
		int durationMove = (int) ((Math.random() * MAX_DURATION + MIN_DURATION) * 1000);

		Timeline timeline = Timeline
				.createParallel()
				.beginParallel()
				.push(Tween.to(this, ActorAccessor.POSITION_XY, durationMove)
						.target(randomX * 2, randomY * 2).ease(Linear.INOUT))
				.end().setCallbackTriggers(TweenCallback.COMPLETE)
				.setCallback(this).start();

		TimelineAction timelineAction = TimelineAction.$(timeline);

		addAction(timelineAction);

		// ---------------------------------------------------------------
		// CallBack
		// ---------------------------------------------------------------
		callBackDelay = CallBackDelayAction.$(-1,
				CALLBACK_INTERVAL, this);
		addAction(callBackDelay);
	}

	/**
	 * Action callback.
	 * 
	 */
	@Override
	public void onCallBack() {
		handleCheck();
	}

	/**
	 * Handles pulse action complete.
	 * 
	 */
	@Override
	public void onEvent(int eventType, BaseTween<?> source) {
		switch (eventType) {
		case COMPLETE:
			handleComplete();
			break;
		default:
			break;
		}
	}

	/**
	 * Handles pulse action complete.
	 * 
	 */
	private void handleComplete() {
		int durationMove = (int) ((Math.random() * MAX_DURATION + MIN_DURATION) * 1000);
		int equa = (int) (Math.random() * (EQUATIONS.length - 1));
		Timeline t = Timeline
				.createParallel()
				.beginParallel()
				.push(Tween
						.to(this, ActorAccessor.POSITION_XY, durationMove)
						.target((float) Math.random()
								* (director.getWidth() - 35),
								(float) Math.random()
										* (director.getHeight() - 35))
						.ease(EQUATIONS[equa])).end()
				.setCallbackTriggers(TweenCallback.COMPLETE).setCallback(this)
				.start();
		TimelineAction timelineAction = TimelineAction.$(t);
		addAction(timelineAction);
	}

	/**
	 * Triggered on call back action.
	 * 
	 */
	private void handleCheck() {
		// Check for collision with other actor.
		Actor target = collisionCheck();
		if (target != null) {
			synchronized (target) {
				rrl.clearCallBack(this);
				this.director.sendEvent(AppEvents.EVENT_END_PLAYER_ROUND,
						target);
			}
		}
	}

	/**
	 * Check for collision with another actor on the stage.
	 * 
	 * @return Target object if collision or null if none.
	 */
	private Actor collisionCheck() {
		Actor actor = null;
		Stage stage = getStage();
		if (stage != null) {
			actor = RoundSceneHelper.roundIntersects(this.getX(), this.getY(),
					this.getWidth(), this.getHeight(), stage.getRoot(),
					PlayerRoundSprite.class);
		}
		return actor;
	}

	/**
	 * Override the hit method here to ensure our collision detection helper
	 * class {@link SceneHelper} can make a match against an actual game element
	 * like this Asteroid class. I.e if you call the super-class method you will
	 * get a match against a FrameSprite which isn't what we want.
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this
				: null;
	}
}
