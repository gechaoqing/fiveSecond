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

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.definition.AppMusicDefinitions;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.game.FiveSecondGame;
import com.gcq.fivesecond.music.MusicCache;
import com.gcq.fivesecond.properties.GameProperties;
import com.gcq.fivesecond.sprite.ControlRoundSprite;
import com.gcq.fivesecond.sprite.PlayerRoundSprite;
import com.gcq.fivesecond.sprite.PointerSprite;
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
public class PlayerRoundLayer extends Layer implements ActorEventObserver {

	public static final float FRAME_DURATION = 0.01f;

	private PlayerRoundSprite PlayerRound;
	private ControlRoundSprite controlRound;
	private PointerSprite pointer;
	private float offsetX;
	private float offsetY;

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * The one and only director.
	 */
	private Director director;

	/**
	 * Singletons.
	 */
	private TextureCache textureCache;

	
	private MusicCache musicCache;

	

	/**
	 * Create pulse group layer.
	 * 
	 * @param stage
	 */
	public PlayerRoundLayer(float width, float height) {
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);
		textureCache = AppInjector.getInjector()
				.getInstance(TextureCache.class);
		
		musicCache= AppInjector.getInjector().getInstance(MusicCache.class);
		buildElements();
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements() {
		TextureDefinition definition = textureCache
				.getDefinition(AppTextureDefinitions.TEXTURE_PLAYER_ROUND);
		TextureRegion textureRegion = textureCache.getTexture(definition);

		PlayerRound = new PlayerRoundSprite(textureRegion,
				definition.getRows(), definition.getCols(), FRAME_DURATION);

		definition = textureCache
				.getDefinition(AppTextureDefinitions.TEXTURE_CONTROL_ROUND);
		textureRegion = textureCache.getTexture(definition);

		controlRound = new ControlRoundSprite(textureRegion,
				definition.getRows(), definition.getCols(),FRAME_DURATION, PlayerRound);
		definition = textureCache
				.getDefinition(AppTextureDefinitions.TEXTURE_POINTER);
		textureRegion = textureCache.getTexture(definition);
		pointer=new PointerSprite(textureRegion, definition.getRows(), definition.getCols(), FRAME_DURATION, controlRound);
		pointer.setRotation(30);
		offsetX = (PlayerRound.getWidth() - controlRound.getWidth()) / 2;
		offsetY = 2 * controlRound.getHeight();
	}
	

	@Override
	public void enter() {
		PlayerRound.resetXY();
		controlRound.resetXY(PlayerRound);
		addActor(PlayerRound);
		addActor(controlRound);
		pointer.setX(310);
		pointer.setY(50);
		addActor(pointer);
		pointer.run();
		PlayerRound.addCheck();
		director.registerEventHandler(this);
		GameProperties gp= FiveSecondGame.dbm.getProperties();
		if (gp.isAudioOn())
		{
			Music music=musicCache.get(AppMusicDefinitions.MUSIC_GAME_BACKGROUND);
			music.setLooping(true);
			music.setVolume(gp.getVolume());
			music.play();
		}
	}

	/**
	 * Called when layer is no longer part of visible view.
	 * 
	 */
	@Override
	public void exit() {
		cleanup();
		director.deregisterEventHandler(this);
	}

	private static boolean drag = false;

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		float touchX = (x * director.getScaleFactorX()) - 35;
		float touchY = director.getHeight() - (y * director.getScaleFactorY())
				- 30;
		float dx = Math.abs(touchX - controlRound.getX());
		float dy = Math.abs(touchY - controlRound.getY());
		if (dx <= 15 && dy <= 15) {
			removeActor(this.pointer);
			drag = true;
		} else {
			drag = false;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (drag) {
			float touchX = (x * director.getScaleFactorX());
			float touchY = director.getHeight()
					- (y * director.getScaleFactorY());
			this.PlayerRound.setXY(touchX - offsetX - 35, touchY + offsetY
					- 35);
			this.controlRound.setXY(touchX - 35, touchY - 30);
			return true;
		}
		return false;
	}

	@Override
	public boolean handleEvent(ActorEvent event) {
		boolean handled = false;
		switch (event.getId()) {
		case AppEvents.EVENT_END_PLAYER_ROUND:
			gameOver();
			handled = true;
			break;
		default:
			break;
		}

		return handled;
	}

	private synchronized void gameOver() {
		cleanup();
		musicCache.get(AppMusicDefinitions.MUSIC_GAME_BACKGROUND).stop();
		director.sendEvent(AppEvents.EVENT_TRANSITION_TO_GAME_OVER_SCENE, this);
	}

	private void cleanup() {
		int size = getChildren().size;
		while (size > 0) {
			Actor actor = getChildren().get(--size);
			removeActor(actor);
		}
	}

	@Override
	public boolean removeActor(Actor actor) {
		super.removeActor(actor);

		actor.clearActions();

		return true;
	}


}
