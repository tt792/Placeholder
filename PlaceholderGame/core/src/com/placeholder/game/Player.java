package com.placeholder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.placeholder.game.Pickup.itemType;

public class Player extends Unit {	
	/**
	 * Enum for the defining classes of the player
	 */
	public enum playerType{
		Nerd, Jock, Art
	}
	
	/**
	 * The players class
	 * @Description
	 * 		Either Nerd, Jock
	 */
	private playerType playerClass;
	
	/**
	 * The level map for collision purposes
	 */
	private TiledMap map;
	
	/**
	 * The layer of the map which contains the walls which the player cannot walk over
	 */
	TiledMapTileLayer collisionLayer; //only selects the layer called "Walls"
	
	/**
	 * Returns the players class type
	 */
	public playerType getPlayerClass() {
		return playerClass;
	}
	
	/**
	 * The value of how see-able the player is
	 * @implementation 
	 * 		Used to calculate whether the player is seen by a Zombie or not <p>
	 * 		will have a function to update the Zombies range when the game starts and when the player picks up a stealth item
	 */
	private int stealth;
	/**
	 * Function to change the stealth of the player
	 * @implementation
	 * 		either send a positive or negative int to increase or decrease the players stealth
	 */
	public void changeStealth(int givenInt) {
		stealth += givenInt;
	}
	/**
	 * Function to return the value of the players stealth
	 */
	public int getStealth() {
		return stealth;
	}
	
	/**
	 * The stamina of the player, defining how far they can run
	 */
	private int stamina, maxStamina;
	/**
	 * Function to return the value of the players stamina
	 */
	public int getStamina() {
		return stamina;
	}
	
	/**
	 * The 2-slot inventory of the player of Pickup
	 *  @Description
	 *  	Uses enum types within the Pickup class to define whether the pickup is a health item or a weapon
	 *  @Implementation
	 *  	Inventory[0] - Player's Weapon <br>
	 *  	Inventory[1] - Player's Health item <p>
	 *  	test for the Pickup.itemType to decide what to do with it
	 */
	private Pickup[] inventory = new Pickup[2];
	
	/**
	 * The score of the player
	 * @description
	 * 		Will increase as the player defeats Zombies and moves through locations
	 */
	private int credits;
	/**
	 * Function to return the value of the players credits
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * Function to change the value of the players credits with
	 * @implementation
	 * 		either send a positive or negative int to increase or decrease the players credits
	 */
	public void changeCredits(int givenInt) {
		credits += givenInt;
	}
	
	/**
	 * Function to add an item to the 2-item inventory
	 * Returns boolean of whether the item was added to the inventory
	 * 
	 * @implementation
	 * 		either puts the item into the inventory or will switch out the players current item in that slot
	 * 		Do we want this to return the old item so it can be dropped?
	 */
	Pickup addItemToInventory(String givenName, itemType givenType, String givenDescription, int givenEffect, String givenFileName) {
		Pickup tempPickup = null;
		if (givenType == itemType.Weapon) {
			tempPickup = inventory[0];
			//call dropItem function
			inventory[0] = new Pickup(givenName, givenType, givenDescription, givenEffect, givenFileName);
		} else if (givenType == itemType.HealthItem) {
			tempPickup = inventory[0];
			//call dropItem function
			inventory[1] = new Pickup(givenName, givenType, givenDescription, givenEffect, givenFileName);
		} 
		return tempPickup;
	}
	
	/**
	 * dropItem function
	 *	@Description
	 *		returns the sprite to be dropped and the location to drop it from the players X and Y
	 */
	public void dropItem() {
		//TODO: implement above
	}
	
	/**
	 * Constructor for first introduction of a new player
	 * Creating a new player at the start of the game
	 * @Description
	 * 		Takes the player type and builds the player around that, is for creating a new player at the beginning of the game </br>
	 * 		Use other constructor for loading a player into the game
	 */
	public Player(playerType givenType) {
		sprite = new Sprite(new Texture(Gdx.files.internal("testPlayer.png")));
		addItemToInventory("None", itemType.HealthItem,"You have no health item", 0, "HealthPack.png");
		type = Nature.Player;
		playerClass = givenType;
		map = new TmxMapLoader().load("E:\\Downloads\\Placeholder-master2\\Placeholder-master\\PlaceholderGame\\core\\assets\\Tiles for testing\\Map1.tmx");
		collisionLayer = (TiledMapTileLayer)map.getLayers().get("Walls");
		if (givenType == playerType.Nerd) {
			//update the image for it
			addItemToInventory("Wimpy Fists", itemType.Weapon, "Your wimpy nerd fists aernt going to do anything against these zombies", 1, "Sword.png");
			maxHealth = 3;
			currentHealth = maxHealth;
			stealth = 3;
			speed = 1; //change after testing
			sprintSpeed = 20; ///change after testing
			stamina = 500; //change after testing
			maxStamina = stamina;
			setXY(new Vector2(0, 0)); //set to where the start of the layer is
		} else if (givenType == playerType.Jock) {
			//update the image for it
			addItemToInventory("Fists", itemType.Weapon, "Your strong fists allow you to easily get through these enemies", 3, "Sword.png");
			maxHealth = 4;
			currentHealth = maxHealth;
			stealth = 0;
			speed = 5;
			sprintSpeed = 7;
			stamina = 40;
			maxStamina = stamina;
		} else if (givenType == playerType.Art) { //TODO: the third class in the game
			addItemToInventory("Medicinal Herbs", itemType.HealthItem,"Feels good man...", 0, "HealthPack.png");
			//peeeeeeeeta hide yourself by drawing rocks....
		}
	}
	
	/**
	 * The constructor for creating a player
	 * @Requires
	 * 		Inventory setting up <br>
	 * 			Weapon is fists, no health item <br>
	 * 			When loading the game will need to change this to take the current weapons of the player </p>
	 * 		What items the player had
	 */
	public Player(int givenMaxHealth, int givenCurrentHealth, int givenStealth, int givenSpeed, int givenStamina, int givenSprintSpeed, int givenX, int givenY) {
		/*
		 * can get the max health, stealth, speed from what type the player is
		 * ie. if the players a Nerd they have x health, y speed and u 
		 */
		sprite = new Sprite(new Texture(Gdx.files.internal("Player.png")));
		addItemToInventory("Fists", itemType.Weapon, "These are your fists, time to go hit some zombies", 1, "Sword.png");
		addItemToInventory("None", itemType.HealthItem,"You have no health item", 0, "HealthPack.png");
		type = Nature.Player;
		maxHealth = givenMaxHealth;
		speed = givenSpeed;
		stamina = givenStamina;
		maxStamina = stamina;
		stealth = givenStealth;
		sprintSpeed = givenSprintSpeed;
		currentHealth = givenCurrentHealth;
		xy = new Vector2(givenX, givenY);
	}
	
	/**
	 * Returns the Pickup from slot [num] in the inventory
	 * num <== {0, 1}
	 */
	Pickup viewInventory(int num) {
		if ((num != 0) && (num != 1)){
			return null;
		}
		return inventory[num];
	}
	
	/**
	 * Function taking keyboard input and moving the player
	 * @input
	 * 		Keyboard Input: <br>
	 * 			W - Y+ <br>
	 * 			A - X- <br>
	 * 			S - Y- <br>
	 * 			D - X+ <br>
	 * Change how much the inputs move the player when we have a map
	 */
	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (sprintHeld() && stamina > 0) {
				if (testForCollision("UP")) {
					xy.y += sprintSpeed;
					stamina -= 1;
				}
			} else {
				if (testForCollision("UP")) {
					xy.y += speed;
					updateStamina();
				}
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (sprintHeld() && stamina > 0) {
				if (testForCollision("DOWN")) {
					xy.y -= sprintSpeed;
					stamina -= 1;
				}
			} else {
				if (testForCollision("DOWN")) {
					xy.y -= speed;
					updateStamina();
				}
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (sprintHeld() && stamina > 0) {
				if (testForCollision("LEFT")) {
					xy.x -= sprintSpeed;
					stamina -= 1;
				}
			} else {
				if (testForCollision("LEFT")) {
					xy.x -= speed;
					updateStamina();
				}
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (sprintHeld() && stamina > 0) {
				if (testForCollision("RIGHT")) {
					xy.x += sprintSpeed;
					stamina -= 1;
				}
			} else {
				if (testForCollision("RIGHT")) {
					xy.x += speed;
					updateStamina();
				}
			}
		}
		sprite.setPosition(xy.x, xy.y);
		
		//do some collision checking here
		/*
		 * before move player
		 * check the tile that is next to the player in that direction
		 * ie. if "W" is pressed then check the tile which is to the right by ~10px
		 * if that tile has the "Wall" property then dont allow the movement
		 * so will need to get the tilemaps here
		 */
	}
	
	/**
	 * Function to test a position for wall collision
	 * 
	 * @param direction - which direction the player is moving in to check (in caps) </br>
	 * 			- UP / RIGHT / DOWN / LEFT
	 * @return Boolean value regarding if the player can move there or not
	 */
	private boolean testForCollision(String direction) {		
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0, offset = 5;
		//currently only testing the middle of the side player is moving in
		//later change to be testing multiple points
		
		
		//playerx/y refer to the sprites bottom left corner, so need to change it depending on which side needs checking
		if (direction == "UP") { //x + 1/2 and y + height
			x1 = (int) ((xy.x + offset) / collisionLayer.getTileWidth()); //testing the left of sprite
			y1 = (int) ((xy.y + sprite.getHeight()) / collisionLayer.getTileHeight()); //testing the top of the sprite
			x2 = (int) ((xy.x + sprite.getWidth() - offset) / collisionLayer.getTileWidth()); //testing the right of sprite
			y2 = y1;
			if (testLocation(x1,y1) && testLocation(x2,y2)) { //if neither of the corners on the side which the player is moving toward detect a wall
				return true;
			} else {
				return false;
			}
		}
		if (direction == "DOWN") { //x + 1/2 and y + 0
			x1 = (int) ((xy.x + 5) / collisionLayer.getTileWidth());
			y1 = (int) ((xy.y) / collisionLayer.getTileHeight());
			x2 = (int) ((xy.x + sprite.getWidth() - offset) / collisionLayer.getTileWidth());
			y2 = y1;
			if (testLocation(x1,y1) && testLocation(x2,y2)) { //if neither of the corners on the side which the player is moving toward detect a wall
				return true;
			} else {
				return false;
			}
		}
		if (direction == "LEFT") { //x + 0 and y + 1/2
			x1 = (int) ((xy.x) / collisionLayer.getTileWidth());
			y1 = (int) ((xy.y + offset) / collisionLayer.getTileHeight());
			x2 = x1;
			y2 = (int) ((xy.y + sprite.getHeight() - offset) / collisionLayer.getTileHeight());
			if (testLocation(x1,y1) && testLocation(x2,y2)) { //if neither of the corners on the side which the player is moving toward detect a wall
				return true;
			} else {
				return false;
			}
		}
		if (direction == "RIGHT") { //x + sprite and y + 1/2
			x1 = (int) ((xy.x + sprite.getWidth()) / collisionLayer.getTileWidth());
			y1 = (int) ((xy.y + offset) / collisionLayer.getTileHeight());
			x2 = x1;
			y2 = (int) ((xy.y + sprite.getHeight() - offset) / collisionLayer.getTileHeight());
			if (testLocation(x1,y1) && testLocation(x2,y2)) { //if neither of the corners on the side which the player is moving toward detect a wall
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Function performing check on a location to test if its walkable or not
	 */
	private boolean testLocation(int givenX, int givenY) {
		if (collisionLayer.getCell(givenX, givenY) != null) {
			if (collisionLayer.getCell(givenX, givenY).getTile().getProperties().containsKey("Blocked")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Function to increase the stamina of the player if they are not currently running+
	 */
	private void updateStamina() {
		if (stamina < maxStamina && !sprintHeld()) {
			stamina += 1;
		}
	}
	
	/**
	 * Function to return if the left_shift key is pressed
	 */
	private boolean sprintHeld() {
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * Changes the players health depending on the healthItem pickup in the players inventory
	 */
	void useHealthItem() {
		if (inventory[1].getName() != "None") { //if the name is None then the player has no health item
			if (currentHealth + inventory[1].getEffect() <= maxHealth) { //add the health buff to the players health, limiting it to the max health of the player
				currentHealth += inventory[1].getEffect();
			} else {
				currentHealth = maxHealth;
			}
			addItemToInventory("None", itemType.HealthItem,"You have no health item", 0, "HealthPack.png");
		}
	}
} 