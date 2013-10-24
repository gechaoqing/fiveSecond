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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.font.GameFont;
import com.gcq.fivesecond.game.FiveSecondGame;
import com.gcq.fivesecond.properties.GameProperties;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Scene layer.
 * 
 */
public class SettingsLayer extends Layer {
	private static final String UI_FILE = "data/five-second-setting-layer-ui.json";
	private static final String TITLE_FONT = "title-font";

	private Table table;
	private TextureRegion background;
	private Skin skin;

	/**
	 * The one and only director.
	 */
	private Director director;

	/**
	 * Singletons
	 */
	private TextureCache textureCache;

	/**
	 * Construct layer.
	 * 
	 * @param width
	 * @param height
	 */
	public SettingsLayer(float width, float height) {
		setWidth(width);
		setHeight(height);

		director = AppInjector.getInjector().getInstance(Director.class);

		textureCache = AppInjector.getInjector()
				.getInstance(TextureCache.class);

		Gdx.input.setCatchBackKey(true);

		loadTextures();

		buildElements();
	}

	/**
	 * Load view textures.
	 * 
	 */
	private void loadTextures() {
		TextureDefinition definition = textureCache
				.getDefinition(AppTextureDefinitions.TEXTURE_DARK_BACKGROUND);
		background = textureCache.getTexture(definition);

		skin = new Skin(Gdx.files.internal(UI_FILE));
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements() {
		// ---------------------------------------------------------------
		// Background.
		// ---------------------------------------------------------------
		Image image = new Image(background);

		image.setWidth(getWidth());
		image.setHeight(getHeight());

		addActor(image);

		// ---------------------------------------------------------------
		// Elements
		// ---------------------------------------------------------------
		final Label titleLabel = new Label("游戏设置", skin, TITLE_FONT,
				Color.YELLOW);

		final Label level = new Label("难度", skin);

		final CheckBox checkBox = new check(textureCache,
				AppTextureDefinitions.TEXTURE_CHECKBOX_ON,
				AppTextureDefinitions.TEXTURE_CHECKBOX_OFF).getCheckBox("");
		GameProperties gp = FiveSecondGame.dbm.getProperties();
		checkBox.setChecked(gp.isAudioOn());
		final Label soundLabel = new Label("声音", skin);

		final Slider slider = new Slider(0, 10, 1, false, skin);
		slider.setValue(gp.getVolume() * 10);
		final CheckBox radio_low = new check(textureCache,
				AppTextureDefinitions.TEXTURE_RADIO_LOW_ON,
				AppTextureDefinitions.TEXTURE_RADIO_LOW_ON).getCheckBox(""), radio_mid = new check(
				textureCache, AppTextureDefinitions.TEXTURE_RADIO_MID_ON,
				AppTextureDefinitions.TEXTURE_RADIO_MID_OFF).getCheckBox(""), radio_hi = new check(
				textureCache, AppTextureDefinitions.TEXTURE_RADIO_HI_ON,
				AppTextureDefinitions.TEXTURE_RADIO_HI_OFF).getCheckBox("");
		int dif = gp.getDifficulty();
		if (dif == 15) {
			radio_hi.setChecked(true);
			radio_mid.setChecked(true);
		} else if (dif == 10) {
			radio_hi.setChecked(false);
			radio_mid.setChecked(true);
		} else {
			radio_hi.setChecked(false);
			radio_mid.setChecked(false);
		}
		// ---------------------------------------------------------------
		// Table
		// ---------------------------------------------------------------
		table = new Table();

		table.setSize(getWidth(), getHeight());

		table.row().height(150f);
		table.add(titleLabel).colspan(3);
		table.row().height(70f);
		table.add(level).colspan(3);
		table.row().height(70f);
		table.add(radio_low);
		table.add(radio_mid).uniform().padLeft(-16f).width(158f);
		table.add(radio_hi).uniform().padLeft(-14f).width(158f);
		table.row().height(70f);
		table.add(soundLabel).colspan(3);
		table.row().height(70f);
		table.add(checkBox).left().padLeft(5f);
		table.add(slider).colspan(2).width(245f);
		table.setTransform(true);
		table.setOrigin(table.getPrefWidth() / 2, table.getPrefHeight() / 2);

		addActor(table);

		// Handlers
		checkBox.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				boolean setting = checkBox.isChecked();
				// gameProperties.setAudioOn(setting);
				FiveSecondGame.dbm.updateAudioOn(setting);
			}

		});

		radio_low.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				radio_hi.setChecked(false);
				radio_mid.setChecked(false);
				// gameProperties.setDifficulty(1);
				FiveSecondGame.dbm.updateDifficulty(5);
			}
		});

		radio_mid.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				radio_hi.setChecked(false);
				radio_mid.setChecked(true);
				// gameProperties.setDifficulty(2);
				FiveSecondGame.dbm.updateDifficulty(10);
			}
		});

		radio_hi.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				boolean hi = radio_hi.isChecked();
				if (hi) {
					radio_mid.setChecked(true);
					// gameProperties.setDifficulty(3);
					FiveSecondGame.dbm.updateDifficulty(15);
				} else {
					// gameProperties.setDifficulty(2);
					FiveSecondGame.dbm.updateDifficulty(10);
				}
			}
		});

		slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;
				FiveSecondGame.dbm.updateVolume(slider.getValue() / 10);
				// if (slider.getValue() == 0) {
				// gameProperties.setVolume(0);
				//
				// } else {
				// gameProperties.setVolume(slider.getValue() / 10);
				// }

			}

		});
	}

	/**
	 * Catch escape key.
	 * 
	 */
	@Override
	public boolean keyUp(int keycode) {
		boolean handled = false;

		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, this);

			handled = true;
		}

		return handled;
	}

	private int x;

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		this.x = x;
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (this.x - x > 20) {
			director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, this);
			return true;
		}
		return false;
	}

	class check {
		SpriteDrawable on;
		SpriteDrawable off;

		public check(TextureCache textureCache, String onimg, String offimg) {
			TextureDefinition definition = textureCache.getDefinition(onimg);
			TextureRegion region = textureCache.getTexture(definition);
			Sprite spon = new Sprite(region.getTexture());
			on = new SpriteDrawable(spon);
			definition = textureCache.getDefinition(offimg);
			region = textureCache.getTexture(definition);
			Sprite spoff = new Sprite(region.getTexture());
			off = new SpriteDrawable(spoff);
		}

		public CheckBox getCheckBox(String title) {
			return new CheckBox(title, getCheckStyle());
		}

		public ButtonGroup getRadios() {
			ButtonGroup buttonGroup = new ButtonGroup();
			String[] str = { "低", "中", "高" };
			for (int i = 0; i < 3; i++) {
				CheckBox checkBox = new CheckBox(str[i], getCheckStyle());
				checkBox.setPosition(10 + 30 * i, 20);
				buttonGroup.add(checkBox);
			}
			return buttonGroup;
		}

		private CheckBox.CheckBoxStyle getCheckStyle() {
			CheckBox.CheckBoxStyle cbstyle = new CheckBox.CheckBoxStyle();
			cbstyle.checkboxOn = on;
			cbstyle.checkboxOff = off;
			cbstyle.font = new BitmapFont(
					Gdx.files.internal(GameFont.FONT_GAME_SETTING_FILE),
					Gdx.files.internal(GameFont.FONT_GAME_SETTING_IMAGE), false);
			cbstyle.fontColor = Color.WHITE;
			cbstyle.checkedFontColor = Color.YELLOW;
			cbstyle.downFontColor = Color.GRAY;
			return cbstyle;
		}
	}

}
