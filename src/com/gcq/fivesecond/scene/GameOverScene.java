package com.gcq.fivesecond.scene;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.layer.GameOverLayer;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

public class GameOverScene extends Scene {

	private GameOverLayer gol;
	private TextureCache textureCache;
	
	public GameOverScene(){
		gol=new GameOverLayer(getWidth(), getHeight());
		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);
		setBackground();
		getInputMultiplexer().addProcessor(gol);
		addLayer(gol);
	}
	
	private void setBackground(){
		TextureRegion background;
		TextureDefinition definition = textureCache.getDefinition(AppTextureDefinitions.TEXTURE_DARK_BACKGROUND);
		background = textureCache.getTexture(definition);
		Image image = new Image(background);
		image.setWidth(getWidth());
		image.setHeight(getHeight());
		addActor(image);
	}
}
