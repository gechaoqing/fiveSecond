package com.gcq.fivesecond.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.netthreads.libgdx.sprite.FrameSprite;

public class PositionSprite extends FrameSprite {

	Vector2 position;
	Vector2 targetPosition;
	Vector2 nextPosition;
	public PositionSprite(TextureRegion texture, int rows, int cols,
			float frameDuration) {
		super(texture, rows, cols, frameDuration, true);
		position = new Vector2(0,0);
		targetPosition = new Vector2(position);
		nextPosition = new Vector2();
	}
	
	public void setX(float x)
	{
		super.setX(x);
		position.x = x;
		targetPosition.set(x, getY());
	}

	public void setY(float y)
	{
		super.setY(y);
		position.y = y;
		targetPosition.set(getX(), y);
	}
	
	public void setXY(float x,float y){
		super.setX(x);
		super.setY(y);
	}

	
	public void trackToXY(float x, float y) {
		Vector2 current = recalcPosition();
		position.set(current);
		targetPosition.set(x, y);
	}

	Vector2 recalcPosition() {
		nextPosition.set(targetPosition);
		nextPosition.sub(position);
		nextPosition.add(position);
		return nextPosition;
	}

}
