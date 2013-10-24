package com.gcq.fivesecond.definition;

import java.util.LinkedList;
import java.util.List;

import com.gcq.fivesecond.music.MusicDefinition;
import com.gcq.fivesecond.music.MusicDefinitions;
import com.google.inject.Singleton;

@Singleton
public class AppMusicDefinitions implements MusicDefinitions {

public static final String MUSIC_PATH = "data";
	
	public static final String MUSIC_GAME_BACKGROUND="game-background.ogg";
	
	@SuppressWarnings("serial")
	public static final List<MusicDefinition> MUSICS = new LinkedList<MusicDefinition>()
	{
		{
			add(new MusicDefinition(MUSIC_GAME_BACKGROUND, MUSIC_PATH + "/" + MUSIC_GAME_BACKGROUND));
		}
	};
	@Override
	public List<MusicDefinition> getDefinitions() {
		return MUSICS;
	}

}
