package com.paceholder.game;

import com.paceholder.game.Pickup.itemType;

public class Player extends Unit {
	
	/**
	 * The value of how see-able the player is
	 * @implementation 
	 * 		Used to calculate wether the player is seen by a zombie or not <p>
	 * 		will have a function to update the zombies range when the game starts and when the player picks up a stealth item
	 */
	public int stealth;
	
	/**
	 * The stamina of the player, defining how far they can run
	 */
	public int maxEnergy;
	
	/**
	 * The 2-slot inventory of the player
	 * <p>
	 * Still need to finally decide on what data type this should be <br>
	 * As it is cant reference the items in Weapon or HealthItem as are using their super
	 * Could have both Weapon and HealthItem as the same class and just use the type to differentiate them
	 * </p>
	 *  @Description
	 *  	Uses enum types within the Pickup class to define whether the pickup is a health item or a weapon
	 *  @Implementation
	 *  	Inventory[0] - Player's Weapon <br>
	 *  	Inventory[1] - Player's Health item <p>
	 *  	test for the Pickup.itemType to decide what to do with it
	 */
	public Pickup[] inventory = new Pickup[2];
	
	/**
	 * The score of the player
	 * @description
	 * 		Will increase as the player defeats zombies and moves through locations
	 */
	public int credits;
	
	/**
	 * Function to add an item to the 2-item inventory
	 * 
	 * @implementation
	 * 		either puts the item into the inventory or will switch out the players current item in that slot
	 * 		Do we want this to return the old item so it can be dropped?
	 */
	private void addItemToInventory(String givenName, itemType givenType, String givenDescription, int givenEffect) {
		if (givenType == itemType.Weapon) {
			inventory[0] = new Pickup(givenName, givenType, givenDescription, givenEffect);
		} else if (givenType == itemType.HealthItem) {
			inventory[1] = new Pickup(givenName, givenType, givenDescription, givenEffect);
		}
	}
	
	/**
	 * The constructor for creating a player
	 * @Requires
	 * 		Inventory setting up <br>
	 * 			Weapon is fists, no health item <br>
	 * 			When loading the game will need to change this to take the current weapons of the player </p>
	 * 		Health setting up
	 */
	public Player(int givenMaxHealth) {
		inventory[0] = new Pickup("Fists", itemType.Weapon, "These are your fists, time to go hit some zombies", 1);
		inventory[1] = new Pickup("None", itemType.HealthItem,"You have no health item", 0);
		maxHealth = givenMaxHealth;
		currentHealth = maxHealth;
	}
	
	/**
	 * Returns the Pickup from slot [num] in the inventory
	 * num <== {0, 1}
	 */
	private Pickup viewInventory(int num) {
		if ((num != 0) && (num != 1)){
			return null;
		}
		return inventory[num];
	}
	
	/**
	 * Changes the players health depending on the healthItem pickup in the players inventory
	 */
	private void useHealthItem() {
		if (inventory[1] != null) {
			if (currentHealth + inventory[1].getEffect() <= maxHealth) {
				currentHealth += inventory[1].getEffect();
			} else {
				currentHealth = maxHealth;
			}
			inventory[1] = null;
		}
	}
	
	
	
	public static void main(String[] args){
		Player player1 = new Player(3);
		
		System.out.println(player1.currentHealth);
		player1.useHealthItem();
		System.out.println(player1.currentHealth);
		player1.currentHealth = 1;
		player1.addItemToInventory("Health Pack", itemType.HealthItem, "This is a health pack", 5);
		System.out.println(player1.inventory[1].getDescription());
		System.out.println(player1.currentHealth);
		player1.useHealthItem();
		System.out.println(player1.currentHealth);
	}
} 