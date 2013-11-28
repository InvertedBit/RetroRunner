package com.invertedbit.retrorunner;

import java.util.List;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

public class RunnerGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private RunnerObject runner;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Body runnerBody;
	private Body testBody;
	private Body groundBody;
	private int jump_count = MAX_JUMP_COUNT;
	
	static final int MAX_JUMP_COUNT = 5;
	static final float WORLD_TO_BOX = 0.01f;
	static final float BOX_TO_WORLD = 100f;
//	static final float FACTOR = 100f;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w*0.05f, h*0.05f);
		
		batch = new SpriteBatch();
		
		this.world = new World(new Vector2(0,-50), false);

		//this.world.setVelocityThreshold(0.1f);
		
		this.debugRenderer = new Box2DDebugRenderer();
		this.debugRenderer.setDrawVelocities(true);
		this.debugRenderer.setDrawAABBs(false);
		this.debugRenderer.setDrawJoints(true);
		
		BodyDef testBodyDef = new BodyDef();
		testBodyDef.type = BodyType.DynamicBody;
		testBodyDef.position.set(10,10);
		
		this.testBody = this.world.createBody(testBodyDef);
		PolygonShape testShape = new PolygonShape();
		testShape.setAsBox(1, 1);
		
		
		
		FixtureDef testDef = new FixtureDef();
		testDef.shape = testShape;
		testDef.density = 0.1f;
		testDef.friction = 0.1f;
		testDef.restitution = 0.1f;
		
		Fixture testFixture = testBody.createFixture(testDef);
		testShape.dispose();

//		this.testBody.setActive(false);
		
		BodyEditorLoader runnerLoader = new BodyEditorLoader(Gdx.files.internal("data/runner"));
		
		BodyDef runnerBodyDef = new BodyDef();
    runnerBodyDef.type = BodyType.DynamicBody;
	  runnerBodyDef.position.set(1,3);
	  runnerBodyDef.fixedRotation = true;
	  runnerBodyDef.linearDamping = 1f;
		
		this.runnerBody = this.world.createBody(runnerBodyDef);
		
		
	// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(3f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
//		fixtureDef.shape = circle;
		fixtureDef.density = .05f;
		fixtureDef.friction = .01f;
		fixtureDef.restitution = 0.1f; // Make it bounce a little bit
		
//		MassData md = this.runnerBody.getMassData();
		
//		md.mass *= 20;
		
//		this.runnerBody.setMassData(md);
		
		runnerLoader.attachFixture(this.runnerBody, "RunnerBody", fixtureDef, 2f);
		
		
		// Create our fixture and attach it to the body
//		Fixture fixture = this.runnerBody.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();

		BodyDef leftBodyDef = new BodyDef();
		leftBodyDef.position.set(new Vector2(1,400));
		
		Body leftBody = world.createBody(leftBodyDef);
		
		PolygonShape leftBox = new PolygonShape();
		
		leftBox.setAsBox(10.0f, camera.viewportHeight);
		
		leftBody.createFixture(leftBox, 0.0f);
		leftBox.dispose();
		
	// Create our body definition
		BodyDef groundBodyDef =new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 1));  

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(camera.viewportWidth, 1.0f);
		// Create a fixture from our polygon shape and add it to our ground body  
		groundBody.createFixture(groundBox, 0.0f); 
		// Clean up after ourselves
		groundBox.dispose();
		
		
		//this.runner = new RunnerObject(new Texture(Gdx.files.internal("data/runner.png")), 0,0,64,64);
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		List<Contact> contacts = this.world.getContactList();
		
		for(Contact contact : contacts)
		{
		  Body a = contact.getFixtureA().getBody();
		  Body b = contact.getFixtureB().getBody();
		if((a.equals(this.runnerBody) && b.equals(this.testBody)) || (a.equals(this.testBody) && b.equals(this.runnerBody)))
		{
	    
	    RopeJointDef testJointDef = new RopeJointDef();
	    testJointDef.bodyA = this.runnerBody;
	    testJointDef.bodyB = testBody;
	    testJointDef.collideConnected = true;
	    testJointDef.maxLength = 200f;
	    
	    Joint testJoint = this.world.createJoint(testJointDef);
	    
		}
		if((a.equals(this.runnerBody) && a.getPosition().y > b.getPosition().y || (b.equals(this.runnerBody) && b.getPosition().y > a.getPosition().y)))
		{
		  this.jump_count = MAX_JUMP_COUNT;
		}
		}
    
    if(Gdx.input.isKeyPressed(Keys.RIGHT))
    {
//      this.world.setGravity(this.world.getGravity().rotate(1f));
      this.runnerBody.applyLinearImpulse(new Vector2(.05f,0f), this.runnerBody.getPosition());
    }
    
    if(Gdx.input.isKeyPressed(Keys.LEFT))
    {
//      this.world.setGravity(this.world.getGravity().rotate(-1f));
      this.runnerBody.applyLinearImpulse(new Vector2(-.05f,0f), this.runnerBody.getPosition());
    }
    
    if(Gdx.input.isKeyPressed(Keys.UP) && this.jump_count > 0)
    {
      this.runnerBody.applyLinearImpulse(new Vector2(0f, .06f), this.runnerBody.getPosition());
      this.jump_count--;
    }

    
		this.camera.update();
		
		
		this.debugRenderer.render(this.world, this.camera.combined);
		
		
		this.world.step(1/60f,6,4);
		this.world.clearForces();
//		float delta = Gdx.graphics.getDeltaTime();
//		
//		camera.update();
//		
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		this.runner.draw(batch);
//		batch.end();
//		
//		this.runner.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
