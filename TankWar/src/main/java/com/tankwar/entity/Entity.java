package com.tankwar.entity;

/**
 * Describe a entity in game world.
 */
public abstract class Entity implements CollisionCheckable, CollisionListener {
	/**
	 * The entity x value.
	 */
	private int x;


	/**
	 * The entity y value.
	 */
	private int y;


	/**
	 * Width of entity.
	 */
	private int width;


	/**
	 * Height of entity.
	 */
	private int height;


	/**
	 * Get width of entity.
	 * @return Width of entity.
	 */
	public abstract int getWidth();


	/**
	 * Get heigth of entity.
	 * @return Height of entity.
	 */
	public abstract int getHeight();


	/**
	 * Get the entity x value.
	 * @return The entity x value.
	 */
	public abstract int getX();


	/**
	 * Get the entity y value.
	 * @return The entity y value.
	 */
	public abstract int getY();
}