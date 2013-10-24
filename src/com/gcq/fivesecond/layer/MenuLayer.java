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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gcq.fivesecond.definition.AppEvents;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

/**
 * Scene layer.
 * 
 */
public class MenuLayer extends Layer
{
	private static final String UI_START_FILE = "data/five-second-ui-start.json";
	
	private static final String UI_SETTING_FILE = "data/five-second-ui-setting.json";
	
	private static final String UI_EXIT_FILE="data/five-second-ui-exit.json";
	
	private Table table;
	private Skin start_skin;
	private Skin setting_skin;
	private Skin exit_skin;
	private Button startButton;
	private Button settingsButton;
	private Button exitButton;
//	private Button aboutButton;

	/**
	 * The one and only director.
	 */
	private Director director;

	/**
	 * Construct the screen.
	 * 
	 * @param stage
	 */
	public MenuLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);
		
		Gdx.input.setCatchBackKey(true);

		loadTextures();

		buildElements();
	}

	/**
	 * Load view textures.
	 * 
	 */
	private void loadTextures()
	{
		setting_skin = new Skin(Gdx.files.internal(UI_SETTING_FILE));
		start_skin= new Skin(Gdx.files.internal(UI_START_FILE));
		exit_skin=new Skin(Gdx.files.internal(UI_EXIT_FILE));
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{

		// ---------------------------------------------------------------
		// Buttons.
		// ---------------------------------------------------------------
		startButton = new TextButton("  开始游戏", start_skin);
		startButton.padBottom(5f);
		settingsButton = new TextButton("  游戏设置", setting_skin);
		settingsButton.padBottom(5f);
		exitButton=new TextButton("  退出游戏", exit_skin);
		exitButton.padBottom(5f);
//		aboutButton = new TextButton("About", skin);

		// ---------------------------------------------------------------
		// Table
		// ---------------------------------------------------------------
		table = new Table();

		table.setSize(getWidth(), getHeight());

		table.add(startButton).padBottom(20f);
		table.row();
		table.add(settingsButton).padBottom(20f);
		table.row();
		table.add(exitButton);
		table.setTransform(true);
		table.setOrigin(table.getPrefWidth() / 2, table.getPrefHeight() / 2+50);
		
		// Listener.
		startButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_GAME_SCENE, event.getRelatedActor());
			}

		});

		// Listener.
		settingsButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_SETTINGS_SCENE, event.getRelatedActor());
			}

		});
		
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				super.clicked(event, x, y);
				System.exit(0);
			}
		});

//		// Listener.
//		aboutButton.addListener(new ClickListener()
//		{
//			@Override
//			public void clicked(InputEvent event, float x, float y)
//			{
//				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_ABOUT_SCENE, event.getRelatedActor());
//			}
//
//		});

		// Add table to view
		addActor(table);

	}
	
	private int x;

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		this.x = x;
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (x - this.x > 20) {
			director.sendEvent(AppEvents.EVENT_TRANSITION_TO_SETTINGS_SCENE, this);
			return true;
		}
		return false;
	}
	
	

}
