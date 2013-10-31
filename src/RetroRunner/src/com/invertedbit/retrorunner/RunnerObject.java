/**
 * 
 */
package com.invertedbit.retrorunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Alex
 *
 */
public class RunnerObject extends GameObject
{

	public RunnerObject(Texture texture, float posX, float posY, float width,
			float height) {
		super(texture, posX, posY, width, height);
	}
	
	public void update()
	{
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			this.posX -= 1;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.posX += 1;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			this.posY -= 1;
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			this.posY += 1;
		}
	}
	
}
