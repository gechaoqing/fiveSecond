package com.gcq.fivesecond.accessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

public class ActorAccessor implements TweenAccessor<Actor> {
	
	public static final int POSITION_XY = 1;
	public static final int SCALE_XY = 2;
	public static final int CPOS_XY=3;
	public static final int OPACITY=4;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType)
		{
			case POSITION_XY:
				returnValues[0] = target.getX();
				returnValues[1] = target.getY();
				return 2;
				
			case SCALE_XY:
				returnValues[0] = target.getScaleX();
				returnValues[1] = target.getScaleY();
				return 2;
			case CPOS_XY:
	            returnValues[0] = target.getX()+ target.getWidth() / 2;
	            returnValues[1] = target.getY() + target.getHeight() / 2;
	            return 2;
			case OPACITY:
	            returnValues[0] = target.getColor().a;
	            return 1;
			default:
				assert false;
				return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType)
		{
			case POSITION_XY:
				target.setX(newValues[0]);
				target.setY(newValues[1]);
				break;
			case SCALE_XY:
				target.setScaleX(newValues[0]);
				target.setScaleY(newValues[1]);
				break;
			case CPOS_XY:
	            target.setX(newValues[0] - target.getWidth() / 2);
	            target.setY(newValues[1] - target.getHeight() / 2);
	            break;
			case OPACITY:
	            Color c = target.getColor();
	            c.set(c.r, c.g, c.b, newValues[0]);
	            target.setColor(c);
	            break;
			default:
				assert false;
		}
	}

}
