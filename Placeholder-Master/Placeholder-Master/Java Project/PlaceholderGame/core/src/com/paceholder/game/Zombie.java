package com.paceholder.game;

public class Zombie extends Unit {
	
	/**
	 * Which type this zombie is
	 */
	public enum type {
		Big, Small, Ranged;
	}
	
	/**
	 * The X Coordinate of the Zombie
	 */
	public int x;
	
	/**
	 * The Y Coordinate of the Zombie
	 */
	public int y;
	
	/**
	 * Whether this zombie attacks with melee or not
	 */
	public boolean melee;
	
	/**
	 * How far the zombie can see the player from
	 */
	public int sight;
	
	/**
	 * The time the zombies must wait between attacks
	 */
	public int attackDelay;
	
	/**
	 * The last millisecond the zombie attacked
	 */
	public long lastAttack = 0;
	/**
	 * Function to test if this zombie can see the player
	 * 
	 * @implementation
	 * 	Calculate if the zombie can see the player
	 * 	Calculation is: if the player is within the sight of zombie - player.stealth;
	 */
	public boolean canSeePlayer() {
		return false;
	}
	
	/**
	 * Function for controlling the zombies movement
	 * 
	 * @implementation
	 * 	using an A* pathing method to calculate the best path between different locations
	 * 	will move randomly in a slow motion from randomly selected points to other randomly selected points
	 *	
	 *	If the zombie can see the player then move towards the player
	 *	also use the attack function to test if the zombie is in striking distance of the player
	 */
	public void move() {
		
	}
	
	/**
	 * Function for controlling the zombies attacks to the player
	 * 
	 * @implementation
	 * 	when within striking distance swipe at the player
	 */
	public void attack() {
		if (melee) {
			if (System.currentTimeMillis() >= lastAttack + attackDelay) {
				//Add attacking code
				
				lastAttack = System.currentTimeMillis();
			}
		}
	}
}
