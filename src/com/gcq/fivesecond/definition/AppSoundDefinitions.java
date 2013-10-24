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

package com.gcq.fivesecond.definition;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Singleton;
import com.netthreads.libgdx.sound.SoundDefinition;
import com.netthreads.libgdx.sound.SoundDefinitions;

/**
 * Populate this with sound definitions.
 * 
 */
@Singleton
@SuppressWarnings("serial")
public class AppSoundDefinitions implements SoundDefinitions 
{
	public static final String SOUND_PATH = "data";
	
	public static final String SOUND_TARGET_ROUND_DISC = "targetround-disc.wav";
	public static final String SOUND_GAME_BACKGROUND="game-background.ogg";
	
	public static final List<SoundDefinition> SOUNDS = new LinkedList<SoundDefinition>()
	{
		{
			add(new SoundDefinition(SOUND_TARGET_ROUND_DISC, SOUND_PATH + "/" + SOUND_TARGET_ROUND_DISC));
			add(new SoundDefinition(SOUND_GAME_BACKGROUND, SOUND_PATH + "/" + SOUND_GAME_BACKGROUND));
		}
	};

	/**
	 * Return definitions.
	 * 
	 */
	@Override
    public List<SoundDefinition> getDefinitions()
    {
	    return SOUNDS;
    }
	
}
