package com.gcq.fivesecond.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ControlRoundSprite extends PositionSprite {

	public ControlRoundSprite(TextureRegion texture, int rows, int cols,
			float frameDuration,PlayerRoundSprite playerRound) {
		super(texture, rows, cols, frameDuration);
	}
	
	public void resetXY(PlayerRoundSprite playerRound){
		this.setX(playerRound.position.x+playerRound.getWidth()/2-getWidth()/2);
		this.setY(playerRound.position.y-2*getHeight());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
