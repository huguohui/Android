package com.tankwar.entity;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;

/**
 * Describe a entity in game world.
 */
public abstract class Entity implements CollisionCheckable, CollisionListener {
	/**
	 * The entity x value.
	 */
	private int mX;


	/**
	 * The entity y value.
	 */
	private int mY;


	/**
	 * Width of entity.
	 */
	private int mWidth;


	/**
	 * Height of entity.
	 */
	private int mHeight;


    /**
     * The constructor of entity.
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Entity(int x, int y) {
        this.mX = x;
        this.mY = y;
    }


    /**
     * The empty constructor.
     */
    public Entity() {

    }


	/**
	 * Get width of entity.
	 * @return Width of entity.
	 */
	public int getWidth() {
        return mWidth;
    }


	/**
	 * Get heigth of entity.
	 * @return Height of entity.
	 */
	public int getHeight() {
        return mHeight;
    }


	/**
	 * Get the entity x value.
	 * @return The entity x value.
	 */
	public int getX() {
        return mX;
    }


	/**
	 * Get the entity y value.
	 * @return The entity y value.
	 */
	public int getY() {
        return mY;
    }


    public void setX(int x) {
        mX = x;
    }

    public void setY(int y) {
        mY = y;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setHeight(int height) {
        mHeight = height;
    }
}