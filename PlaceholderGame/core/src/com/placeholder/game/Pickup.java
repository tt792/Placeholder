package com.placeholder.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for all pickups including weapons
 */
public class Pickup {
	
	/**
	 * Sprite of the Pickups, used for drawing and moving the 
	 */
	Sprite sprite = null;
	
	/**
	 * Used to distinguish between an inventory item being a health item and a weapon
	 */
	public enum itemType{
		Weapon, HealthItem, Sneakers, Speedos;
	}
	
	/**
	 * Defines whether this item is a Weapon or HealthItem
	 */
	private itemType type;
	
	/**
	 * A describer for this pickup
	 */
	private String description;
	
	/**
	 * The placement of this pickup
	 */
	private Vector2 xy;
	public Vector2 getXY() {
		return xy;
	}
	
	/**
	 * which level the item shows up in
	 */
	private int level;
	public int getLevel() {
		return level;
	}
	
	/**
	 * The name of this Pickup
	 */
	private String name;
	
	/**
	 * The effect of this pickup <br>
	 * Changes what it does based on what type this pickup has <br>
	 * either the Damage of a weapon or the Health Bonus of at HealthItem
	 */
	private int effect;
	
	/**
	 * Returns the Name of this Pickup
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the type of this Pickup
	 */
	public itemType getType() {
		return type;
	}
	
	/**
	 * Returns the description of this Pickup
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the effect of this Pickup
	 */
	public int getEffect() {
		return effect;
	}
	
	/**
	 * Constructs a Pickup for the player
	 * 
	 * @Implementation
	 * 		Requires <br>
	 * 		- Name <br>
	 * 		- Type <br>
	 * 		- Effect <br>
	 * 		- Texture <br>
	 */
	public Pickup(String givenName, itemType givenType, String givenDescription, int givenEffect, String givenFileName, Vector2 givenXY, int givenLevel) {
		name = givenName;
		type = givenType;
		description = givenDescription;
		effect = givenEffect;
		sprite = new Sprite(new Texture(givenFileName));
		xy = givenXY;
		level = givenLevel;
	}
}
