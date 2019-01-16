package com.placeholder.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


/*
 * Player needs a sprite attribute which is given the texture for the player
 * 		- has texture
 * 		- has x and y coordinates
 * 		- has movement function called within the render fn
 * Enemy's need to have sprite attributes also
 * 		- has textures
 * 		- has x and y coordinates
 * 		- has movement function called within the render fn
 * Map
 * 		- is tileset
 * 		- render tileset with the view of the camera
 */

/**
 * The class run when executing placeholder-desktop
 */
public class Placeholder extends ApplicationAdapter {
	private SpriteBatch batch;
	private TiledMapRenderer tiledMapRenderer;
	private TiledMap tiledMap;
	private OrthographicCamera camera;
	private BitmapFont font;
	Player player;
	private float camX = 0, camY = 0;
	private Vector2 playerDiff = new Vector2(0f, 0f);
	
	
	//to be replaced when enemy has sprite
	
	Sprite enemy;
	Vector2 enemyXY = new Vector2(0,0);
	Sprite test;
	
	private boolean playerCreated = false;
	//end of temp stuff
	
	@Override
	public void create () {
		UI.create(); //Creates the UI
		//set up font for things
		font = new BitmapFont();
		
		batch = new SpriteBatch();
		/*
		 * Load and create the tiled map for the player to move around on
		 */
		tiledMap = new TmxMapLoader().load("Map1.tmx");
	    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		/*
		 * TODO:
		 * Change the enemy creation and such so its not done here
		 */
	    /* add in when dealing with the enemy
		enemy = new Sprite(new Texture(Gdx.files.internal("enemy.jpg")));
		enemyXY.x += enemy.getWidth() / 2;
		enemyXY.y += enemy.getHeight() / 2;
		enemy.setScale(0.2f);
		*/
		
		//create camera for scene
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		UI.render();
		
		//Only move and render game if out of menu
		if (!UI.inMenu) {
			if(!playerCreated) { //Creates the player once the type has been picked in the menu
				player = new Player(UI.desiredType);
				playerCreated = true;
			}
			
			player.move();
			updateCam();
			tiledMapRenderer.setView(camera); //set it so that the map is aligned to the camera
			tiledMapRenderer.render();
			
			//do sprite drawing within the batch.begin() and batch.end()
			batch.begin();
			batch.setProjectionMatrix(camera.combined);
			
			//draw the sprites of the various pickups
			for (int i = 0; i < player.pickupList.length; i++) {
				if(player.pickupList[i] != null) {
					player.pickupList[i].sprite.draw(batch);
				}
			}
			
			playerUI(batch); //draw the players UI (health etc...)
			player.sprite.draw(batch);
			batch.end();
		}
	}
	


	
	/**
	 * Function to display the player stats on screen
	 */
	public void playerUI(SpriteBatch batch) {
		float top, left;
		top = camY + (Gdx.graphics.getHeight() / 2);
		left = camX - (Gdx.graphics.getWidth() / 2);
		
		//display player health
		font.draw(batch, "Health: " , left, top);	
		for (int i = 0; i < player.getHealth(); i++) {
			font.draw(batch, "V", left + 50 + (10 * i), top);
		}
		
		//draw inventory
		font.draw(batch, "Inventory", left, top - 50);
		font.draw(batch, "Weapon:" + player.viewInventory(0).getName(), left, top - 65);
		font.draw(batch, "Health Item: " + player.viewInventory(1).getName(), left, top - 80);
		
		//draw player stats
		font.draw(batch, "Stealth: ", left, top - 105);
		font.draw(batch, "Speed: ", left, top - 130);
		font.draw(batch, "Stamina: ", left, top - 155);
	}

	/**
	 * Function to decide if the camera should move or not based on the players location
	 */
	private void updateCam() {
		/*
		 * How To?
		 * - need direction player is moving in
		 * - need to have camera center position? and then if there is too large a difference in player and camera
		 * 		position the update the camera position?
		 */
		playerDiff.x = camX - player.getX();
		playerDiff.y = camY - player.getY();
		if (Math.abs(playerDiff.x) > 10) { //if the players x is 10 in either direction of the cameras x
			//change the camera's x towards the players x
			camX -= playerDiff.x * Gdx.graphics.getDeltaTime(); //Smoothly moves the camera towards the player
		}
		if (Math.abs(playerDiff.y) > 10) { //if player is 10 away either up or down
			camY -= playerDiff.y * Gdx.graphics.getDeltaTime();
		}
		camera.position.set(new Vector3(camX, camY, 0));
		camera.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		UI.dispose();
	}
}
