package com.tankwar.engine.entity;

import com.tankwar.engine.GameContext;

/**
 * A base class of describe a terrain.
 *
 * @author hgh
 * @since 2015/11/04
 */
public abstract class Terrain extends DrawableEntity {

	/**
	 * The constructor of entity.
	 *
	 * @param gameContext The game context.
	 * @param x           The default x coordinate.
	 * @param y           The default y coordinate.
	 */
	public Terrain(GameContext gameContext, int x, int y) {
		super(gameContext, x, y);
	}

	/**
	 * Default constructor.
	 *
	 * @param gameContext The game context.
	 */
	public Terrain(GameContext gameContext) {
		super(gameContext);
	}
}