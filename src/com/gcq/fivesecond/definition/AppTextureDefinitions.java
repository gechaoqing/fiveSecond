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
import com.netthreads.libgdx.texture.TextureDefinition;
import com.netthreads.libgdx.texture.TextureDefinitions;

/**
 * You can populate this if you are not using a pre-defined packed texture
 * created using the TexturePacker class.
 * 
 */
@Singleton
@SuppressWarnings("serial")
public class AppTextureDefinitions implements TextureDefinitions 
{
	public static final String TEXTURE_PATH = "data";
	
	public static final String TEXTURE_REDROUND = "redround.png";
	public static final String TEXTURE_PLAYER_ROUND = "playerround.png";
	
	public static final String TEXTURE_TARGET_ROUND="targetround.png";
	public static final String TEXTURE_CONTROL_ROUND="controlround.png";
	public static final String TEXTURE_GAME_BACKGROUND="gamebackground.png";
	public static final String TEXTURE_MENU_BACKGROUND="menu-back-ground.png";
	
	public static final String TEXTURE_DARK_BACKGROUND="dark-back-ground.png";
	public static final String TEXTURE_LOGO="logo.png";
	
	public static final String TEXTURE_CHECKBOX_OFF="checkbox-off.png";
	public static final String TEXTURE_CHECKBOX_ON="checkbox-on.png";
	
	public static final String TEXTURE_RADIO_OFF="radio-off.png";
	public static final String TEXTURE_RADIO_ON="radio-on.png";
	
	public static final String TEXTURE_RADIO_LOW_OFF="radio-low-off.png";
	public static final String TEXTURE_RADIO_LOW_ON="radio-low-on.png";
	
	public static final String TEXTURE_RADIO_MID_OFF="radio-mid-off.png";
	public static final String TEXTURE_RADIO_MID_ON="radio-mid-on.png";
	
	public static final String TEXTURE_RADIO_HI_OFF="radio-hi-off.png";
	public static final String TEXTURE_RADIO_HI_ON="radio-hi-on.png";
	
	public static final String TEXTURE_TARGET_DISC="targetround-disc.png";
	
	public static final String TEXTURE_POINTER="pointer.png";
	

	public static final List<TextureDefinition> TEXTURES = new LinkedList<TextureDefinition>()
	{
		{
			add(new TextureDefinition(TEXTURE_REDROUND, TEXTURE_PATH + "/" + TEXTURE_REDROUND));
			add(new TextureDefinition(TEXTURE_PLAYER_ROUND, TEXTURE_PATH + "/" + TEXTURE_PLAYER_ROUND));
			
			add(new TextureDefinition(TEXTURE_TARGET_ROUND, TEXTURE_PATH + "/" + TEXTURE_TARGET_ROUND) );
			add(new TextureDefinition(TEXTURE_CONTROL_ROUND, TEXTURE_PATH + "/" + TEXTURE_CONTROL_ROUND) );
			add(new TextureDefinition(TEXTURE_GAME_BACKGROUND, TEXTURE_PATH + "/" + TEXTURE_GAME_BACKGROUND) );
			add(new TextureDefinition(TEXTURE_MENU_BACKGROUND, TEXTURE_PATH + "/" + TEXTURE_MENU_BACKGROUND) );
			
			add(new TextureDefinition(TEXTURE_LOGO, TEXTURE_PATH + "/" + TEXTURE_LOGO) );
			add(new TextureDefinition(TEXTURE_DARK_BACKGROUND, TEXTURE_PATH + "/" + TEXTURE_DARK_BACKGROUND) );
			
			add(new TextureDefinition(TEXTURE_CHECKBOX_OFF, TEXTURE_PATH + "/" + TEXTURE_CHECKBOX_OFF) );
			add(new TextureDefinition(TEXTURE_CHECKBOX_ON, TEXTURE_PATH + "/" + TEXTURE_CHECKBOX_ON) );
			add(new TextureDefinition(TEXTURE_RADIO_OFF, TEXTURE_PATH + "/" + TEXTURE_RADIO_OFF) );
			add(new TextureDefinition(TEXTURE_RADIO_ON, TEXTURE_PATH + "/" + TEXTURE_RADIO_ON) );
			
			add(new TextureDefinition(TEXTURE_RADIO_LOW_OFF, TEXTURE_PATH + "/" + TEXTURE_RADIO_LOW_OFF) );
			add(new TextureDefinition(TEXTURE_RADIO_LOW_ON, TEXTURE_PATH + "/" + TEXTURE_RADIO_LOW_ON) );
			
			add(new TextureDefinition(TEXTURE_RADIO_MID_OFF, TEXTURE_PATH + "/" + TEXTURE_RADIO_MID_OFF) );
			add(new TextureDefinition(TEXTURE_RADIO_MID_ON, TEXTURE_PATH + "/" + TEXTURE_RADIO_MID_ON) );
			
			add(new TextureDefinition(TEXTURE_RADIO_HI_OFF, TEXTURE_PATH + "/" + TEXTURE_RADIO_HI_OFF) );
			add(new TextureDefinition(TEXTURE_RADIO_HI_ON, TEXTURE_PATH + "/" + TEXTURE_RADIO_HI_ON) );
			
			add(new TextureDefinition(TEXTURE_TARGET_DISC, TEXTURE_PATH + "/" + TEXTURE_TARGET_DISC,1,10) );
			add(new TextureDefinition(TEXTURE_POINTER, TEXTURE_PATH + "/" + TEXTURE_POINTER) );
		}
	};


	/**
	 * Return texture definitions.
	 * 
	 */
	@Override
    public List<TextureDefinition> getDefinitions()
    {
	    return TEXTURES;
    }

}
