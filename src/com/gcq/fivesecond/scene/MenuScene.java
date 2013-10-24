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
import com.gcq.fivesecond.layer.MenuLayer;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Menu scene.
 * 
 */
public class MenuScene extends Scene
{
	private Layer menuLayer;
//	private Layer starsLayer;
	private TextureCache textureCache;
	
	private Director director;
	

	public MenuScene()
	{
		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);
		director = AppInjector.getInjector().getInstance(Director.class);
		setBackground();
		// ---------------------------------------------------------------
		// Stars layer.
		// ---------------------------------------------------------------
//		starsLayer = new StarsLayer(getWidth(), getHeight());
//
//		addLayer(starsLayer);
		
		// ---------------------------------------------------------------
		// Main menu layer.
		// ---------------------------------------------------------------
		menuLayer = new MenuLayer(getWidth(), getHeight());
		getInputMultiplexer().addProcessor(menuLayer);
		addLayer(menuLayer);
	}
	
	private void setBackground(){
		TextureRegion background;
		TextureDefinition definition = textureCache.getDefinition(AppTextureDefinitions.TEXTURE_MENU_BACKGROUND);
		background = textureCache.getTexture(definition);
		Image image = new Image(background);
		image.setWidth(getWidth());
		image.setHeight(getHeight());

		addActor(image);
		
		definition=textureCache.getDefinition(AppTextureDefinitions.TEXTURE_LOGO);
		background=textureCache.getTexture(definition);
		
		image= new Image(background);
		image.setX((director.getWidth()-image.getWidth())/2);
		image.setY(director.getHeight()-133*2);
		
		addActor(image);
	}

}
