package com.placeholder.game;

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
	protected Vector2 xy;
	
	/**
	 * Returns the players X
	 */
	public float getX() {
		return xy.x;
	}
	
	/**
	 * Returns the players Y
	 */
	public float getY() {
		return xy.y;
	}
	
	/**
	 * Sets the players X
	 */
	public void setX(float givenX) {
		xy.x = givenX;
	}
	
	/**
	 * Sets the players Y
	 */
	public void setY(float givenY) {
		xy.x = givenY;
	}
	
	/**
	 * Sets the players XY
	 */
	public void setXY(Vector2 givenXY) {
		xy = givenXY;
	}
	
	
	
	/**
	 * The name of the entity
	 */
	public String name;
	
	/**
	 * Weather this unit is a Zombie or a Player
	 */
	public enum nature{
		Zombie, Player;
	}
	
	/**
	 * The public reference to the type of this unit
	 */
	public nature type;
	
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
	
	public int getHealth() {
		return currentHealth;
	}
	
	/**
	 * The agility of this Unit
	 * @description The speed the unit can move around the map
	 */
	public int speed;
	
	/**
	 * Function to return the value of the players speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Function to change the speed of the player
	 */
	public void changeSpeed(int givenSpeed) {
		speed += givenSpeed;
	}
	
	/**
	 * The speed of this unit when sprinting
	 */
	public int sprintSpeed;
}
