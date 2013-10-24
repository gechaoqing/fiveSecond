

package com.gcq.fivesecond.layer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.sprite.TargetRoundSprite;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Layer;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

/**
 * Scene layer.
 * 
 */
public class TargetRoundLayer extends Layer implements ActorEventObserver
{

	public static final float TEXTURE_FRAME_DURATION = 0.5f;
	private TargetRoundSprite targetRound;


	/**
	 * The one and only director.
	 */
	private Director director;
	
	

	/**
	 * Singletons.
	 */
	private TextureCache textureCache;

	
	
	/**
	 * Create layer.
	 * 
	 * @param stage
	 */
	public TargetRoundLayer(float width, float height)
	{
		setWidth(width);
		setHeight(height);
		director = AppInjector.getInjector().getInstance(Director.class);
		textureCache = AppInjector.getInjector().getInstance(TextureCache.class);
		
		buildElements();
	}

	/**
	 * Build view elements.
	 * 
	 */
	private void buildElements()
	{
		TextureDefinition definition = textureCache.getDefinition(AppTextureDefinitions.TEXTURE_TARGET_ROUND);
		TextureRegion textureRegion = textureCache.getTexture(definition);

		targetRound = new TargetRoundSprite(textureRegion, definition.getRows(), definition.getCols(), TEXTURE_FRAME_DURATION);
	}
	
	@Override
	public void enter()
	{
		setPosition();
		director.registerEventHandler(this);
	}

	@Override
	public void exit()
	{
		cleanup();
		director.deregisterEventHandler(this);
	}
	
	@Override
	public boolean handleEvent(ActorEvent event) {
		boolean handled = false;
		switch (event.getId())
		{
		case AppEvents.EVENT_NEXT_DEST:
			nextPosition();
			handled = true;
			break;
		default:
			break;
		}
		return handled;
	}
	
	public void nextPosition()
	{
		setPosition();
		TargetRoundSprite.TIMER_LAST=5;
	}
	
	public void setPosition(){
		float w=targetRound.getWidth();
		float h=targetRound.getHeight();
		targetRound.setX((float)(Math.random()*(director.getWidth()-2*w)+w));
		targetRound.setY((float)(Math.random()*(director.getHeight()-4*h)+h*3));
		resetActor();
	}
	
	private void resetActor(){
		addActor(targetRound);
		targetRound.addTimer();
	}
	
	private void cleanup()
	{
		int size = getChildren().size;
		while (size > 0)
		{
			Actor actor = getChildren().get(--size);

			removeActor(actor);
		}

	}
	
	@Override
	public boolean removeActor(Actor actor)
	{
		super.removeActor(actor);

		actor.clearActions();

		return true;
	}
}
