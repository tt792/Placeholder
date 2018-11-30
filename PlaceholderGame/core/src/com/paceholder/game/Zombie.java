package com.paceholder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Zombie extends Unit {
	
	/**
	 * Which type this zombie is
	 */
	public enum zombieType {
		Big, Small, Ranged;
	}
	public zombieType zombieClass;
	
	/**
	 * Whether this zombie attacks with melee or not
	 */
	public boolean melee;
	
	/**
	 * How far the zombie can see the player from
	 */
	private int sight;
	/**
	 * Function to return the value of this zombies sight
	 */
	public int getSight() {
		return sight;
	}
	
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
	
	/**
	 * Method for creating a new Zombie
	 */
	public Zombie(zombieType givenZombieType) {
		/*
		 * if passed what type this zombie is, randomly assign one of the textures for that type of zombie
		 */
		type = Nature.Zombie;
		zombieClass = zombieType.Big;
		if (givenZombieType == zombieType.Big) {
			//randomly choose the texture for this Zombie
			sprite = new Sprite(new Texture(Gdx.files.internal("enemy.jpg")));
			
			melee = true;
			attackDelay = 1;
			sight = 5;
		} else if (givenZombieType == zombieType.Ranged) {
			//as above
		}
	}
}
