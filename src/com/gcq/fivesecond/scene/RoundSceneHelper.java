package com.gcq.fivesecond.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.netthreads.libgdx.scene.SceneHelper;

public class RoundSceneHelper extends SceneHelper {
	
	private static final Vector2 point = new Vector2();

	public static boolean isRoundIntersect(float sourceX, float sourceY, float sourceWidth, float sourceHeight, float targetX, float targetY, float targetWidth, float targetHeight)
	{
		float round1_center_x=sourceX+sourceWidth/2;
		float round1_center_y=sourceY+sourceHeight/2;
		
		float round2_center_x=targetX+targetWidth/2;
		float round2_center_y=targetY + targetHeight/2;
		
		float temp_A, temp_B;
		temp_A = round1_center_x>round2_center_x ? (round1_center_x-round2_center_x) : (round2_center_x-round1_center_x);  // 横向距离 (取正数，因为边长不能是负数)
		temp_B = round1_center_y>round2_center_y ? (round1_center_y-round2_center_y) : (round2_center_y-round1_center_y);  // 竖向距离 (取正数，因为边长不能是负数)
		float dis=(float)Math.sqrt(temp_A*temp_A + temp_B*temp_B);  // 计算
		
		if(dis>(sourceHeight+targetHeight)/2){
			return false;
		}
		return true;
	}
	
	public static Actor roundIntersects(float x, float y, float width, float height, Group group, Class<?> targetClass)
	{
		SnapshotArray<Actor> children = group.getChildren();

		Actor hit = null;
		int index = children.size - 1;
		while (hit == null && index >= 0)
		{
			Actor child = children.get(index);

			point.x = x;
			point.y = y;

			group.localToDescendantCoordinates(child, point);

			// If child is our target class then immediately check for
			// intersection
			if (child.getClass().equals(targetClass))
			{
				if (isRoundIntersect(point.x, point.y, width, height,0, 0, child.getWidth(), child.getHeight()))
				{
					hit = child;
				}
			}
			else if (child instanceof Group)
			{
				hit = roundIntersects(point.x, point.y, width, height, (Group) child, targetClass);
			}

			index--;
		}

		return hit;
	}
}
