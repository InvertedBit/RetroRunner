/**
 * 
 */
package com.invertedbit.retrorunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Alex
 *
 */
public abstract class GameObject
{
	protected float posX;
	protected float posY;
	protected float width;
	protected float height;
	protected TextureRegion texture;
	protected Texture tex;
	
	public GameObject(Texture texture, float posX, float posY, float width, float height)
	{
		this.texture = new TextureRegion(texture, posX, posY, width, height);
		this.tex = texture;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
	}
	
	public abstract void update();
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(this.tex, this.posX, this.posY);
	}
}
