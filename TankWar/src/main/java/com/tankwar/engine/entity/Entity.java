package com.tankwar.engine.entity;

import com.tankwar.engine.GameContext;

/**
 * Describe a entity in game world.
 */
public abstract class Entity {
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

    /** The context. */
    private GameContext mGameContext;


    /**
     * The constructor of entity.
     * @param gameContext The game context.
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Entity(GameContext gameContext, int x, int y) {
        this.mGameContext = gameContext;
        this.mX = x;
        this.mY = y;
    }


    /**
     * The empty constructor.
     */
    public Entity(GameContext gameContext) {
        this.mGameContext = gameContext;
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

    public GameContext getGameContext() {
        return mGameContext;
    }

    public void setGameContext(GameContext gameContext) {
        mGameContext = gameContext;
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