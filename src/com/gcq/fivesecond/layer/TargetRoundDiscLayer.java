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

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.gcq.fivesecond.core.AppStats;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.definition.AppSoundDefinitions;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.game.FiveSecondGame;
import com.gcq.fivesecond.properties.GameProperties;
import com.gcq.fivesecond.sprite.TargetRoundDiscSprite;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.sound.SoundCache;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Scene layer.
 * 
 */
public class TargetRoundDiscLayer extends Layer implements ActorEventObserver
{
	public static final float FRAME_DURATION = 0.03f;

	/**
	 * The one and only director.
	 */
	private Director director;
	
    private SoundCache soundCache;
    
    private GameProperties gp;
	
	private AppStats appstats;

	/**
	 * Singletons.
	 */
	private TextureCache textureCache;
	
	private Pool<TargetRoundDiscSprite> pool = new Pool<TargetRoundDiscSprite>(1)
			{
				@Override
				protected TargetRoundDiscSprite newObject()
				{
					return createSprite();
				}
			};

	/**
	 * Create target disapear layer.
	 * 
	 * @param stage
	 */
	public TargetRoundDiscLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);

		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);
		soundCache = AppInjector.getInjector().getInstance(SoundCache.class);
		appstats=AppInjector.getInjector().getInstance(AppStats.class);
		
	}

	/**
	 * Called when layer is part of visible view but not yet displayed.
	 * 
	 */
	@Override
	public void enter()
	{
		director.registerEventHandler(this);
		gp=FiveSecondGame.dbm.getProperties();
	}

	/**
	 * Called when layer is no longer part of visible view.
	 * 
	 */
	@Override
	public void exit()
	{
		cleanup();

		director.deregisterEventHandler(this);
	}

	/**
	 * Pooled layers need cleanup view elements.
	 * 
	 */
	private void cleanup()
	{
		int size = getChildren().size;
		while (size > 0)
		{
			Actor actor = getChildren().get(--size);
			removeActor(actor);
		}
	}

	/**
	 * Create new sprite.
	 * 
	 * @return A new sprite.
	 */
	private TargetRoundDiscSprite createSprite()
	{
		TextureDefinition definition = textureCache.getDefinition(AppTextureDefinitions.TEXTURE_TARGET_DISC);
		TextureRegion textureRegion = textureCache.getTexture(definition);
		TargetRoundDiscSprite sprite = new TargetRoundDiscSprite(textureRegion, definition.getRows(), definition.getCols(), FRAME_DURATION);
		return sprite;
	}

	/**
	 * Event handler will listen for pulse fire event and kick off pulse when one is received.
	 * 
	 * Event handler will listen for signal that pulse is finished and remove it from view.
	 * 
	 */
	@Override
	public boolean handleEvent(ActorEvent event)
	{
		boolean handled = false;

		switch (event.getId())
		{
		case AppEvents.EVENT_START_TARGET_ROUND_DISC:
			handleStart(event.getActor());
			handled = true;
			break;
		case AppEvents.EVENT_END_TARGET_ROUND_DISC:
			handleEnd(event.getActor());
			handled = true;
			break;
		default:
			break;
		}

		return handled;
	}

	/**
	 * Add sprite to view.
	 * 
	 * @param source
	 *            The source actor.
	 */
	private void handleStart(Actor source)
	{
		// The starting position of pulse is source of 'fire pulse' i.e. our
		// ship.
		appstats.incScore();
		if (gp.isAudioOn())
		{
			soundCache.get(AppSoundDefinitions.SOUND_TARGET_ROUND_DISC).play(gp.getVolume());
		}
		float x = source.getX();
		float y = source.getY();
		// Get free sprite from pool.
		TargetRoundDiscSprite sprite = pool.obtain();
		sprite.setPosition(x, y);
		addActor(sprite);
		sprite.resetAnimation();
	}

	/**
	 * Remove sprite from view.
	 * 
	 * @param source
	 *            The source actor.
	 */
	public void handleEnd(Actor source)
	{
		removeActor(source);
		director.sendEvent(AppEvents.EVENT_NEXT_DEST, source);
	}

	/**
	 * We override the removeActor to ensure we clear actions and re-pool item.
	 * 
	 */
	@Override
	public boolean removeActor(Actor actor)
	{
		super.removeActor(actor);
		actor.clearActions();
		pool.free((TargetRoundDiscSprite)actor);
		return true;
	}

}
