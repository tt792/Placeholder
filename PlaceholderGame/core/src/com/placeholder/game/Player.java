package com.placeholder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
	 * List of pickups to be rendered
	 */
	public Pickup[] pickupList = new Pickup[0];
	
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
	Pickup addItemToInventory(Pickup item) {
		Pickup tempPickup = null;
		if (item.getType() == itemType.Weapon) {
			tempPickup = inventory[0];
			inventory[0] = item;
		} else if (item.getType() == itemType.HealthItem) {
			tempPickup = inventory[1];
			inventory[1] = item;
		}
		return tempPickup; //passing the old item back to be dropped in the world				
	}
	
	/**
	 * Constructor for first introduction of a new player
	 * Creating a new player at the start of the game
	 * @Description
	 * 		Takes the player type and builds the player around that, is for creating a new player at the beginning of the game </br>
	 * 		Use other constructor for loading a player into the game
	 */
	public Player(playerType givenType) {
		sprite = new Sprite(new Texture("testPlayer.png"));
		addItemToInventory(new Pickup("None", itemType.HealthItem,"You have no health item", 0, "Medkit1.png"));
		type = Nature.Player;
		playerClass = givenType;
		updateLevel(Placeholder.currentLevel);
		if (givenType == playerType.Nerd) {
			//update the image for it
			addItemToInventory(new Pickup("Wimpy Fists", itemType.Weapon, "Your wimpy nerd fists aernt going to do anything against these zombies", 1, "Sword.png"));
			maxHealth = 3;
			currentHealth = maxHealth;
			stealth = 3;
			speed = 3;
			sprintSpeed = 5;
			stamina = 30;
			maxStamina = stamina;
			setXY(new Vector2(0, 0)); //set to where the start of the layer is
		} else if (givenType == playerType.Jock) {
			//update the image for it
			addItemToInventory(new Pickup("Fists", itemType.Weapon, "Your strong fists allow you to easily get through these enemies", 3, "Sword.png"));
			maxHealth = 5;
			currentHealth = maxHealth;
			stealth = 0;
			speed = 2;
			sprintSpeed = 4;
			stamina = 50;
			maxStamina = stamina;
		} else if (givenType == playerType.Art) { //TODO: the third class in the game
			addItemToInventory(new Pickup("Medicinal Herbs", itemType.HealthItem,"Feels good man...", 0, "Medkit1.png"));
			//peeeeeeeeta hide yourself by drawing rocks....
		}
		addPickupToRender(new Pickup("MedKit1", itemType.Sneakers, "This will heal you 1", 5, "Speedup.png"), 16, 50); //adds an item in the world
		addPickupToRender(new Pickup("MedKit2", itemType.HealthItem, "This will heal you 2", 5, "Medkit2.png"), 64, 50);
		addPickupToRender(new Pickup("MedKit3", itemType.HealthItem, "This will heal you 3", 5, "Medkit3.png"), 16, 70);
	}
	
	public void updateLevel(int currentLevel) {
		map = new TmxMapLoader().load(Placeholder.levelList[currentLevel]);
		collisionLayer = (TiledMapTileLayer)map.getLayers().get("Walls");
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (collisionLayer.getCell(x, y) == null) {
					System.out.print("0");
				} else {
					System.out.print(collisionLayer.getCell(x,y).getTile().toString());
				}
			}
			System.out.println();
		}
		if (collisionLayer.getCell(0, 0) == null) {
			System.out.println("some string");
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
		sprite = new Sprite(new Texture("Player.png"));
		addItemToInventory(new Pickup("Fists", itemType.Weapon, "These are your fists, time to go hit some zombies", 1, "Sword.png"));
		addItemToInventory(new Pickup("None", itemType.HealthItem,"You have no health item", 0, "HealthPack.png"));
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
	public Pickup viewInventory(int num) {
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
				}
			}
		}
		//button to use a med item
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			useHealthItem();
		}
		//test for collision with an item to then pickup that item
		itemCollision();
		sprite.setPosition(xy.x, xy.y);
		updateStamina();
	}
	
	/**
	 * Function to test if the player is touching a pickup
	 */
	private void itemCollision() {
		for (int i = 0; i < pickupList.length; i++) {
			if (pickupList[i] != null) {
				if (this.sprite.getBoundingRectangle().overlaps(pickupList[i].sprite.getBoundingRectangle())) { //doesnt work sometimes for some reason
					if (pickupList[i].getType() == itemType.Weapon || pickupList[i].getType() == itemType.HealthItem) {
						pickupItem(pickupList[i]);
					} else if(pickupList[i].getType() == itemType.Sneakers){
						removePickupToRender(pickupList[i]);
						changeStealth(pickupList[i].getEffect());
					} else if(pickupList[i].getType() == itemType.Speedos) {
						removePickupToRender(pickupList[i]);
						changeSpeed(pickupList[i].getEffect());
					}
				}
			}
		}
	}
	
	/**
	 * Function to add item to inventory,
	 * removes the old item from the inventory
	 */
	private void pickupItem(Pickup item) {
		removePickupToRender(item); //stop displaying the item thats been picked up
		addPickupToRender(addItemToInventory(item), getX(), getY()); //add that item to the inventory and draw what used to be in the inventory
	}
	
	/**
	 * Function to add a pickup to be rendered
	 */
	private void addPickupToRender(Pickup item, float givenX, float givenY) {
		if (item.getName() != "None") {
			Pickup tempPickup[] = new Pickup[pickupList.length + 1];
			for (int i = 0; i < pickupList.length; i++) {
				tempPickup[i] = pickupList[i];
			}
			tempPickup[tempPickup.length - 1] = item;
			tempPickup[tempPickup.length - 1].sprite.setPosition(givenX, givenY + 64);
			pickupList = tempPickup;
		}
	}
	
	/**
	 * Function to remove a pickup from being rendered
	 * 	 * ie. when the player picks up an item
	 */
	private void removePickupToRender(Pickup item) { //stop rendering the item the player touched
		int num = 0;
		Pickup tempPickup[] = new Pickup[pickupList.length - 1];
		for (int i = 0; i < pickupList.length; i++) {
			if (pickupList[i] == item) {
				num = i;
				break;
			}
		}
		if (num == 0) {
			for (int i = 0; i < tempPickup.length; i++) {
				tempPickup[i] = pickupList[i+1];
			}
		} else {
			for (int i = 0; i < num; i++) {
				tempPickup[i] = pickupList[i];
			}
			for (int i = num + 1; i < tempPickup.length; i++) {
				tempPickup[i] = pickupList[i+1];
			}
		}
		pickupList = tempPickup;
	}
	
	/**
	 * Function to test a position for wall collision
	 * 
	 * @param direction - which direction the player is moving in to check (in caps) </br>
	 * 			- UP / RIGHT / DOWN / LEFT
	 * @return Boolean value regarding if the player can move there or not
	 */
	private boolean testForCollision(String direction) { //can use this for zombie movement
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
			addItemToInventory(new Pickup("None", itemType.HealthItem,"You have no health item", 0, "Medkit1.png"));
		}
	}
} 