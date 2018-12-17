package com.paceholder.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Unit {
	/**
	 * The sprite of the Unit
	 */
	Sprite sprite;
	
	/**
	 * The X and Y Coordinate of the Unit
	 * @implementation
	 * 		Used to track the current location of the Unit along the X and Y axis
	 */
	public Vector2 xy;
	
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
	 * The public reference to the type of this unit
	 */
	public Nature type;
	
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
	public int speed;
	
	/**
	 * The speed of this unit when sprinting
	 */
	public int sprintSpeed;
}
