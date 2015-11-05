package com.tankwar.entity;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import com.tankwar.engine.Drawable;

/**
 * Describe a drawable entity in game world.
 * @since 2015/11/04
 */
public abstract class DrawableEntity
        implements Drawable, CollisionCheckable, CollisionListener {
	/**
	 * The entity x value.
	 */
	protected int x;


	/**
	 * The entity y value.
	 */
	protected int y;


	/**
	 * Width of entity.
	 */
	protected int width;


	/**
	 * Height of entity.
	 */
	protected int height;


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