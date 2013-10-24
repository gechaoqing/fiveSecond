package com.gcq.fivesecond.music;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Singleton;

@Singleton
public class MusicCache implements Disposable {

	private Map<String, Music> data;
	private Map<String, MusicDefinition> definitions;
	
	public MusicCache(){
		data = new HashMap<String, Music>();
		definitions = new HashMap<String, MusicDefinition>();
	}
	
	public void load(List<MusicDefinition> definitions){
		for(MusicDefinition definition:definitions){
			add(definition);
		}
	}
	
	private void add(MusicDefinition definition){
		Music music=null;
		music =Gdx.audio.newMusic(Gdx.files.internal(definition.getPath()));
		String name=definition.getName();
		data.put(name, music);
		definitions.put(name,definition);
	}
	
	public Music get(String name){
		return data.get(name);
	}
	
	public Map<String,Music> getData(){
		return data;
	}
	@Override
	public void dispose() {
		Collection<Music> values= data.values();
		for(Music music:values){
			music.dispose();
		}
		data.clear();
		definitions.clear();
	}

}
