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

package com.gcq.fivesecond.scene;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.layer.RedRoundLayer;
import com.gcq.fivesecond.layer.ControlLayer;
import com.gcq.fivesecond.layer.PlayerRoundLayer;
import com.gcq.fivesecond.layer.TargetRoundDiscLayer;
import com.gcq.fivesecond.layer.TargetRoundLayer;
import com.gcq.fivesecond.layer.StatsLayer;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Game scene is composed of game element layers.
 */
public class GameScene extends Scene
{
	private Layer controlLayer;
	private Layer targetRoundLayer;
	private Layer playerRoundLayer;
	private Layer redRoundLayer;
	private Layer statsLayer;
	private Layer targetRoundDiscLayer;
	
	private TextureCache textureCache;

	/**
	 * Main game scene.
	 * 
	 */
	public GameScene()
	{
		
		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);
		setBackground();
		// ---------------------------------------------------------------
		// Control layer
		// ---------------------------------------------------------------
		controlLayer = new ControlLayer();
		
		getInputMultiplexer().addProcessor(controlLayer);

		addLayer(controlLayer);

		// ---------------------------------------------------------------
		

		// ---------------------------------------------------------------
		// Asteroid Layer
		// ---------------------------------------------------------------
		redRoundLayer = new RedRoundLayer(getWidth(), getHeight());

		addLayer(redRoundLayer);
		
		targetRoundDiscLayer=new TargetRoundDiscLayer(getWidth(), getHeight());
		addLayer(targetRoundDiscLayer);

		// ---------------------------------------------------------------
		targetRoundLayer = new TargetRoundLayer(getWidth(), getHeight());

		addLayer(targetRoundLayer);
		
		playerRoundLayer = new PlayerRoundLayer(getWidth(), getHeight());
		getInputMultiplexer().addProcessor(playerRoundLayer);
		addLayer(playerRoundLayer);

		// ---------------------------------------------------------------
		// Statistics layer
		// ---------------------------------------------------------------
		statsLayer = new StatsLayer(getWidth(), getHeight());

		addLayer(statsLayer);
	}
	
	private void setBackground(){
		TextureRegion background;
		TextureDefinition definition = textureCache.getDefinition(AppTextureDefinitions.TEXTURE_GAME_BACKGROUND);
		background = textureCache.getTexture(definition);
		Image image = new Image(background);
		image.setWidth(getWidth());
		image.setHeight(getHeight());

		addActor(image);
	}

}
