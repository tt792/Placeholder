package com.paceholder.game;

import com.paceholder.game.Player;
import com.paceholder.game.Pickup.itemType;

class Main {
	public static void main(String[] args){
		Player player1 = new Player(5, 2, 2);
		
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