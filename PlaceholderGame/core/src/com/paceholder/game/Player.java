package com.paceholder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.paceholder.game.Pickup.itemType;

public class Player extends Unit {
	/**
	 * Enum for the defining classes of the player
	 */
	private enum playerType{
		Nerd, Jock, Art
	}
	
	/**
	 * The players class
	 * @Description
	 * 		Either Nerd, Jock
	 */
	private playerType playerClass;
	
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
	private int stamina;
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
	 * 		Takes the player type and builds the player around that
	 * 		Use other constructor for loading a player into the game
	 */
	public Player(playerType givenType) {
		sprite = new Sprite(new Texture(Gdx.files.internal("Player.png")));
		addItemToInventory("None", itemType.HealthItem,"You have no health item", 0, "HealthPack.png");
		type = Nature.Player;
		playerClass = givenType;
		if (givenType == playerType.Nerd) {
			//update the image for it
			addItemToInventory("Wimpy Fists", itemType.Weapon, "Your wimpy nerd fists aernt going to do anything against these zombies", 1, "Sword.png");
			maxHealth = 3;
			currentHealth = maxHealth;
			stealth = 3;
			speed = 3;
			stamina = 5;
			
		} else if (givenType == playerType.Jock) {
			//update the image for it
			addItemToInventory("Fists", itemType.Weapon, "Your wimpy nerd fists aernt going to do anything against these zombies", 3, "Sword.png");
			maxHealth = 4;
			currentHealth = maxHealth;
			stealth = 0;
			speed = 5;
			stamina = 4;
			
		} else if (givenType == playerType.Art) {
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
	public Player(int givenMaxHealth, int givenCurrentHealth, int givenStealth, int givenSpeed, int givenX, int givenY) {
		/*
		 * can get the max health, stealth, speed from what type the player is
		 * ie. if the players a Nerd they have x health, y speed and u 
		 */
		sprite = new Sprite(new Texture(Gdx.files.internal("Player.png")));
		addItemToInventory("Fists", itemType.Weapon, "These are your fists, time to go hit some zombies", 1, "Sword.png");
		addItemToInventory("None", itemType.HealthItem,"You have no health item", 0, "HealthPack.png");
		type = Nature.Player;
		maxHealth = givenMaxHealth;
		stamina = givenSpeed;
		stealth = givenStealth;
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