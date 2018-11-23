package com.paceholder.game;

public class Unit {
	/**
	 * The name of the entity
	 */
	public String name;
	
	/**
	 * Weather this unit is a Zombie or a Player
	 */
	public enum Nature{
		Zombie, Player;
	}
	
	/**
	 * The value for the units attack
	 * @description Used for calculations of damaging other units
	 */
	public int Power;
	
	/**
	 * The maximum health of this Unit
	 * @description This will be set when the unit is created
	 */
	public int maxHealth;
	
	/**
	 * The current health of this Unit
	 * @description This will be decreased and increased as the unit takes damage / heals
	 */
	public int currentHealth;
	
	/**
	 * The agility of this Unit
	 * @description The speed the unit can move around the map
	 */
	public int agility;
}
