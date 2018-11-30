package com.paceholder.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;


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
	SpriteBatch batch;
	TiledMapRenderer tiledMaprenderer;
	OrthographicCamera camera;
	
	//to be replaced when player and enemy has sprite
	Player player;
	
	Sprite enemy;
	Vector2 enemyXY = new Vector2(0,0);
	//end of temp stuff
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(5, 5, 2, 2, 0, 0);
		enemy = new Sprite(new Texture(Gdx.files.internal("Enemy.jpg")));
		player.xy.x += player.sprite.getWidth() / 2;
		player.xy.x += player.sprite.getHeight() / 2;
		enemyXY.x += enemy.getWidth() / 2;
		enemyXY.y += enemy.getHeight() / 2;
		player.sprite.setScale(0.2f);
		enemy.setScale(0.2f);
		
		//create camera for scene
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//player Movement
		player.move();
		/**
		 * TODO:
		 * 		Write code to figure out where the player is on screen and only move the camera if the player is close to the edge of the screen
		 * 		IE. move the camera in same direction as the player if the difference between the players x/y is more than 45% of the screen width then move the camera towards the player?
		 */
		//do sprite drawing within the batch.begin() and batch.end()
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		camera.position.set(player.sprite.getX() + player.sprite.getWidth() / 2, player.sprite.getY() + player.sprite.getHeight() / 2, 0);
		camera.update();
		player.sprite.draw(batch);
		enemy.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
