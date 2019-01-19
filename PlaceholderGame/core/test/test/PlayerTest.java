package test;

import static org.junit.Assert.*;



import org.junit.Test;

import com.placeholder.game.Pickup.itemType;
import com.placeholder.game.Player;


import com.placeholder.game.Player.playerType;

public class PlayerTest extends gamestart {

	/**
	 * HOW TO RUN TESTS
	 * To run tests, right click on the file in package explorer, go to run configurations and press Junit Test
	 */
	
	/**
	 * CreatePlayer
	 *	@Description
	 *		returns a player object
	 */	
	public Player CreatePlayer() {
		//create nerd player object
		Player PlayerObj = new Player(playerType.Nerd);
		return PlayerObj;
	}
	@Test
	//ID U1 
	public void StartGamePlayerConstructorTest_Nerd() {
		
		//create nerd player object
		Player nerd_PlayerObj = new Player(playerType.Nerd);
		
		//Test initial values are correct
		assertEquals(nerd_PlayerObj.getStealth(), 3);
		assertEquals(nerd_PlayerObj.getStamina(), 30);
		assertEquals(nerd_PlayerObj.maxHealth, 3);
		assertEquals(nerd_PlayerObj.sprintSpeed, 5);
		
		//Test starting assignment is correct
		assertEquals(nerd_PlayerObj.currentHealth, nerd_PlayerObj.maxHealth);
			
	}
	@Test
	//ID U2
	public void StartGamePlayerConstructorTest_Jock() {
		
		//create jock player object
		Player jock_PlayerObj = new Player(playerType.Jock);
		System.out.println(jock_PlayerObj.getHealth());
		
		//Test initial values are correct
		assertEquals(jock_PlayerObj.getStealth(),0);
		assertEquals(jock_PlayerObj.getStamina(),40);
		assertEquals(jock_PlayerObj.maxHealth,4);
		assertEquals(jock_PlayerObj.speed,5);
		
		//Test starting assignment is correct
		assertEquals(jock_PlayerObj.currentHealth, jock_PlayerObj.maxHealth);
		
		
	}
		
	//////Power-up play interaction tests
	@Test
	//ID U3
	public void PickupfirstItemTest_Weapon() {
		
		//create player object
		Player playerObj = CreatePlayer();
		
		//pickup weapon 
		//playerObj.addItemToInventory(new Pickup("sword", itemType.Weapon, "Normal Sword", 10, "Sword.png", null, 0));
		
		
		//test if the object is in the inventory
		assertEquals(playerObj.viewInventory(0).getName(),"sword");
		assertEquals(playerObj.viewInventory(0).getEffect(),10);
		assertEquals(playerObj.viewInventory(0).getType(),itemType.Weapon);
		assertEquals(playerObj.viewInventory(0).getDescription(),"Normal Sword"); 
		
		
	}
	
	@Test
	//ID U4 
	public void PickupHealthItemWhenWeaponEquiped() {
		
		//create player object
		Player playerObj = CreatePlayer();
				
		//pickup weapon 
		//playerObj.addItemToInventory(new Pickup("sword", itemType.Weapon, "Normal Sword", 10, "Sword.png", null, 0));
		
		//pickup health 
		//playerObj.addItemToInventory(new Pickup("healthpack", itemType.HealthItem, "Basic HealthPack", 1, "Medkit.png", null, 0));
		
		//test if the object is in the inventory
		assertEquals(playerObj.viewInventory(1).getName(),"healthpack");
		assertEquals(playerObj.viewInventory(1).getEffect(),1);
		assertEquals(playerObj.viewInventory(1).getType(),itemType.HealthItem);
		assertEquals(playerObj.viewInventory(1).getDescription(),"Basic HealthPack");
		
		
	}
	@Test
	//ID U5
	public void PickupWeaponWhenWeaponEquiped() {
		
		//create player object
		Player playerObj = CreatePlayer();
		 
		//Pickup first weapon
		//playerObj.addItemToInventory(new Pickup("knife", itemType.Weapon, "Basic knife", 1, "Sword.png", null, 0));
		//test if the object is in the inventory
		assertEquals(playerObj.viewInventory(0).getName(),"knife");
		assertEquals(playerObj.viewInventory(0).getEffect(),1);
		
		//pickup 2nd weapon 
		//playerObj.addItemToInventory(new Pickup("sword", itemType.Weapon, "Normal Sword", 10, "Sword.png", null, 0));	
				
		//test if the object is in the inventory
		assertEquals(playerObj.viewInventory(0).getName(),"sword");
		assertEquals(playerObj.viewInventory(0).getEffect(),10);
		assertEquals(playerObj.viewInventory(0).getType(),itemType.Weapon);
		assertEquals(playerObj.viewInventory(0).getDescription(),"Normal Sword"); 
		
		
	}
	
	@Test
	//ID U7
	public void PickupIncreaseStats_1() {
		//create player object
		Player playerObj = CreatePlayer();
						
		int startSpeed = playerObj.speed;
		//pickup speed item 
		//playerObj.addItemToInventory(new Pickup("speed item", itemType.Speedos, "", 3, "Medkit.png", null, startSpeed));
				
		assertEquals(playerObj.speed, startSpeed + 3 );
		
	}
	
	@Test
	//ID U8
	public void PickupIncreaseStats_2() {
		//create player object
		Player playerObj = CreatePlayer();
								
		int startStealth = playerObj.getStealth();
		//pickup Stealth item 
		//playerObj.addItemToInventory(new Pickup("sneakers item", itemType.Sneakers, "", 1, "Medkit.png", null, startStealth));
						
		assertEquals(playerObj.getStealth(), startStealth + 1 );
	}
	
	
	
	
	 
	
}
