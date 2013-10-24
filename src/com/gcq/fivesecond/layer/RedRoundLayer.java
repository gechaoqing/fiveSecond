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

package com.gcq.fivesecond.layer;

import java.util.List;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gcq.fivesecond.core.AppStats;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.game.FiveSecondGame;
import com.gcq.fivesecond.sprite.RedRoundSprite;
import com.netthreads.libgdx.action.ActionCallBack;
import com.netthreads.libgdx.action.CallBackDelayAction;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Scene layer.
 * 
 */
public class RedRoundLayer extends Layer implements ActorEventObserver,
		ActionCallBack {
	public static final float FRAME_DURATION = 0.05f;

	private List<RedRoundSprite> pool = new Vector<RedRoundSprite>();

	private TextureDefinition definition;

	private TextureRegion textureRegion;

	private void initRedRoundSprities(int size) {
		pool.clear();
		for (int i = 0; i < size; i++) {
			RedRoundSprite sprite = new RedRoundSprite(textureRegion,
					definition.getRows(), definition.getCols(), FRAME_DURATION,
					this);
			pool.add(sprite);
		}
	}

	private Director director;

	private TextureCache textureCache;

	private AppStats appstats;

	public RedRoundLayer(float width, float height) {
		setWidth(width);
		setHeight(height);
		director = AppInjector.getInjector().getInstance(Director.class);
		textureCache = AppInjector.getInjector()
				.getInstance(TextureCache.class);
		definition = textureCache
				.getDefinition(AppTextureDefinitions.TEXTURE_REDROUND);
		textureRegion = textureCache.getTexture(definition);
		appstats = AppInjector.getInjector().getInstance(AppStats.class);
	}

	/**
	 * Called when layer is part of visible view but not yet displayed.
	 * 
	 */
	@Override
	public void enter() {
		initRedRoundSprities(FiveSecondGame.dbm.getProperties().getDifficulty());
		director.registerEventHandler(this);
		handleStartRedRound();
		call();
	}

	private void call() {
		CallBackDelayAction callBackDelay = CallBackDelayAction.$(-1, 25f,
				this);
		addAction(callBackDelay);
	}

	/**
	 * Called when layer is no longer part of visible view.
	 * 
	 */
	@Override
	public void exit() {
		cleanup();
		this.clearActions();
		director.deregisterEventHandler(this);
	}

	public void clearCallBack(RedRoundSprite rso) {
		if (pool != null) {
			for (RedRoundSprite rs : pool) {
				if (rso == rs) {
					continue;
				}
				rs.removeAction(rs.callBack());
			}
		}
	}

	private void cleanup() {
		int size = getChildren().size;
		while (size > 0) {
			Actor actor = getChildren().get(--size);
			removeActor(actor);
		}

	}

	/**
	 * Handle events.
	 * 
	 */
	@Override
	public boolean handleEvent(ActorEvent event) {
		boolean handled = false;

		switch (event.getId()) {
		case AppEvents.EVENT_START_RED_ROUND:
			handleStartRedRound();
			handled = true;
			break;
		default:
			break;
		}

		return handled;
	}

	private void handleStartRedRound() {
		for (RedRoundSprite sprite : pool) {
			sprite.resetXY();
			addActor(sprite);
			sprite.run();
		}
	}

	@Override
	public boolean removeActor(Actor actor) {
		super.removeActor(actor);

		actor.clearActions();

		return true;
	}

	@Override
	public void onCallBack() {
		int s=appstats.getScore();
		if (s>100) {
			addOnRed();
		}
	}
	
	private void addOnRed(){
		RedRoundSprite sprite = new RedRoundSprite(textureRegion,
				definition.getRows(), definition.getCols(), FRAME_DURATION,
				this);
		addActor(sprite);
		sprite.run();
	}

}
