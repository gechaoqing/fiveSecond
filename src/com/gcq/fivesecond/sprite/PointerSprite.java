package com.gcq.fivesecond.sprite;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gcq.fivesecond.accessor.ActorAccessor;
import com.netthreads.libgdx.action.TimelineAction;
import com.netthreads.libgdx.sprite.AnimatedSprite;

public class PointerSprite extends AnimatedSprite implements TweenCallback {

	private ControlRoundSprite trs;
	public PointerSprite(TextureRegion textureRegion, int rows, int cols,
			float frameDuration,ControlRoundSprite trs) {
		super(textureRegion, rows, cols, frameDuration);
		this.trs=trs;
	}

	public void run(){
		addTimeline();
	}

	@Override
	public void onEvent(int eventType, BaseTween<?> source) {
		switch (eventType) {
		case COMPLETE:
			handleComplete();
			break;
		default:
			break;
		}
	}
	
	private void handleComplete() {
		this.setX(310);this.setY(50);
		addTimeline();
	}
	
	private void addTimeline(){
		TimelineAction timelineAction = TimelineAction.$(getTimeline());
		addAction(timelineAction);
	}
	
	private Timeline getTimeline(){
		return Timeline
				.createParallel()
				.beginParallel()
				.push(Tween.to(this, ActorAccessor.POSITION_XY, 800f)
						.target(trs.getX()+trs.getWidth()/2+15, trs.getY()-trs.getHeight()/2+5).ease(Linear.INOUT))
				.end().setCallbackTriggers(TweenCallback.COMPLETE)
				.setCallback(this).start();
	}

}
