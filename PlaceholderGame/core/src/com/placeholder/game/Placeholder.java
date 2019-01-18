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
	public static String[] levelList = new String[2]; //stores the level names from file storage
	public static int currentLevel = 0; //number reference to the level number for levelList
	
	
	//to be replaced when enemy has sprite
	
	Sprite enemy;
	Vector2 enemyXY = new Vector2(0,0);
	Sprite test;
	
	private boolean playerCreated = false;
	//end of temp stuff
	
	@Override
	public void create () {
		UI.create(); //Creates the UI
		Gdx.graphics.setTitle("Generic: Zombie Game"); //Sets the window title
		
		//set up font for things
		font = new BitmapFont();
		//list of names of maps
		levelList[0] = "Map1.tmx";
		levelList[1] = "Map2.tmx";
		
		batch = new SpriteBatch();
		/*
		 * Load and create the tiled map for the player to move around on
		 */
		tiledMap = new TmxMapLoader().load(levelList[currentLevel]);
	    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		/*
		 * TODO:
		 * Change the enemy creation and such so its not done here
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
			changeLevel(); //test if the player needs to change level
		}
	}
	
	/**
	 * Function to test for the end of the game
	 * @implementation (F16) Used to test whether the player has won the game
	 */
	private boolean endGame() {
		boolean done = true;
		for (int i = 0; i < player.getPickupsCollected().length; i++) {
			if (player.getPickupsCollected()[i] == false) {
				done = false;
			}
		}
		return done;
	}
	
	/**
	 * Tests for the need to change the level when the player is in the correct area
	 * @implementation (F1) Used to change between the different areas in the game
	 */
	private void changeLevel() {
		//if the player is in a doorway of a level then need to change the level
		if (currentLevel == 0) {
			if ((int) (player.getX() / 32) == 1 && (int) (Math.round(player.getY()) / 32) == 1) { //if the player is in the position
				//currently hardcoded, should change this to be in an array or so at some point
				if (levelList[currentLevel] == "Map1.tmx") { //if in the first map TODO change this to be the name of each level
					currentLevel = 1;
				}
				player.setXY(new Vector2(5 * 32, 5 * 32));
				player.updateLevel(currentLevel);
				updateLevel();
			}
		} else if(currentLevel == 1) {
			if ((int) (player.getX() / 32) == 1 && (int) (Math.round(player.getY()) / 32) == 1) {
				if (levelList[currentLevel] == "Map2.tmx") { //if in the first map TODO change this to be the name of each level
					currentLevel = 0;
				}
				player.setXY(new Vector2(0 * 32, 0 * 32));
				player.updateLevel(currentLevel);
				updateLevel();
			}
		}
	}
	
	/**
	 * Updates the variables for the drawn map
	 * @implementation (F1) Used to update the tiledMap and tiledMapRenderer when the area changes
	 */
	private void updateLevel() {
		tiledMap = new TmxMapLoader().load(levelList[currentLevel]);
	    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	
	/**
	 * Function to display the player stats on screen
	 */
	public void playerUI(SpriteBatch batch) {
		float top, left, bottom;
		top = camY + (Gdx.graphics.getHeight() / 2) - 5;
		left = camX - (Gdx.graphics.getWidth() / 2) + 5;
		bottom = camY - (Gdx.graphics.getHeight() / 2) + 5;
		
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
		font.draw(batch, "Stealth: ", left, bottom + 90);
		for (int i = 0; i < player.getStealth(); i++) {
			font.draw(batch, "-", left + (10 * i), bottom + 75);
		}
		font.draw(batch, "Speed: ", left, bottom + 60);
		for (int i = 0; i < player.getSpeed(); i++) {
			font.draw(batch, "-", left + (10 * i), bottom + 45);
		}
		font.draw(batch, "Stamina: ", left, bottom + 30);
		for (int i = 0; i < player.getStamina(); i++) {
			font.draw(batch, "-", left + (10 * i), bottom + 15);
		}
		
		//draw player quest status
		font.draw(batch, "Objectives: ", left, top - 115);
		font.draw(batch, "Find all the powerups!", left, top - 130);
		for (int i = 0; i < player.getPickupsCollected().length; i++) {
			if (i == 0) {
				font.draw(batch, "Sneakers Collected: ", left, top - 145 - (20 * i));
			} else if (i == 1) {
				font.draw(batch, "Speedos Collected: ", left, top - 145 - (20 * i));
			}
			if (player.getPickupsCollected()[i] == true) {
				font.draw(batch, "DONE", left + 180, top - 145 - (20 * i));
			} else if (player.getPickupsCollected()[i] == false) {
				font.draw(batch, "INCOMPLETE", left + 180, top - 145 - (20 * i));			
			}
		}
		
		//draw player won game or not
		if (endGame()) {
			font.draw(batch, "YOU WIN!!!", left, top - 200);
		}
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
