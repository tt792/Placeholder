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
	Sprite player;
	Vector2 playerXY = new Vector2(0,0);
	
	Sprite enemy;
	Vector2 enemyXY = new Vector2(0,0);
	//end of temp stuff
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Sprite(new Texture("C:\\Users\\TGWTu\\Documents\\Placeholder-Master\\Placeholder-Master\\Java Project\\PlaceholderGame\\core\\assets/Player.png"));
		enemy = new Sprite(new Texture("C:\\Users\\TGWTu\\Documents\\Placeholder-Master\\Placeholder-Master\\Java Project\\PlaceholderGame\\core\\assets/Enemy.jpg"));
		playerXY.x += player.getWidth() / 2;
		playerXY.y += player.getHeight() / 2;
		enemyXY.x += enemy.getWidth() / 2;
		enemyXY.y += enemy.getHeight() / 2;
		player.setScale(0.2f);
		enemy.setScale(0.2f);

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//player Movement
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerXY.x -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerXY.x += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			playerXY.y += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			playerXY.y -= 1;
		}
		player.setPosition(playerXY.x, playerXY.y);
		//do sprite drawing here
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
		player.draw(batch);
		enemy.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
