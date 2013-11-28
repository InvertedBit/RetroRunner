/**
 * 
 */
package com.invertedbit.retrorunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Alex
 *
 */
public class RunnerObject extends GameObject
{
  
  private RunnerState state = RunnerState.Standing;

	public RunnerObject(Texture texture, float posX, float posY, float width,
			float height) {
		super(texture, posX, posY, width, height);
	}
	
	public void update(double delta)
	{

    if(!Gdx.input.isKeyPressed(Keys.LEFT) && ! Gdx.input.isKeyPressed(Keys.RIGHT))
      this.vX = 0;
    
    if(!Gdx.input.isKeyPressed(Keys.UP) && ! Gdx.input.isKeyPressed(Keys.DOWN))
      this.vY = 0;
    
    if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			this.vX = -10;
		}
    if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.vX = 10;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			this.vY = -10;
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			this.vY = 10;
		}
		
    this.posX += vX;
    this.posY += vY;
	}
	
}
