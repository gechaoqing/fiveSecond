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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gcq.fivesecond.core.AppStats;
import com.gcq.fivesecond.font.GameFont;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.scene.Layer;

/**
 * Game statistics view.
 * 
 * Note: I have reused the fps StringBuilder technique from Robert Greens website:
 * http://www.rbgrn.net/content/290-light-racer-20-days-32-33-getting-great -game-performance
 * 
 */
public class StatsLayer extends Layer
{

	private static final String TEXT_FPS = "fps:";
	private static final String TEXT_SCORE = "Score:";

	private BitmapFont smallFont;
	private BitmapFont largeFont;

	static final char chars[] = new char[100];
	static final StringBuilder textStringBuilder = new StringBuilder(100);

	private AppStats appStats;

	public StatsLayer(float width, float height)
	{
		this.setWidth(width);
		this.setHeight(height);

		appStats = AppInjector.getInjector().getInstance(AppStats.class);
		
		buildElements();
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{
		smallFont = new BitmapFont(Gdx.files.internal(GameFont.FONT_GOTHIC_16_FILE), Gdx.files.internal(GameFont.FONT_GOTHIC_16_IMAGE), false);
		largeFont = new BitmapFont(Gdx.files.internal(GameFont.FONT_GOTHIC_20_FILE), Gdx.files.internal(GameFont.FONT_GOTHIC_20_IMAGE), false);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		// Frames Per Second.
		textStringBuilder.setLength(0);
		textStringBuilder.append(TEXT_FPS);
		textStringBuilder.append(Gdx.graphics.getFramesPerSecond());
		textStringBuilder.getChars(0, textStringBuilder.length(), chars, 0);

		smallFont.draw(batch, textStringBuilder, 10, 15);

		textStringBuilder.setLength(0);
		textStringBuilder.append(TEXT_SCORE);
		textStringBuilder.append(appStats.getScore());
		textStringBuilder.getChars(0, textStringBuilder.length(), chars, 0);

		largeFont.draw(batch, textStringBuilder, 10, this.getHeight() - 20);
	}
}
