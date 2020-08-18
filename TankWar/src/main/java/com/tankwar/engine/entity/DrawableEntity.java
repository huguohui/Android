package com.tankwar.engine.entity;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.subsystem.Drawable;
import com.tankwar.engine.subsystem.Sprite;

/**
 * Describe a drawable entity in game world.
 *
 * @since 2015/11/04
 */
public abstract class DrawableEntity extends Entity
		implements Drawable {
	/**
	 * The sprite of drawable entity.
	 */
	private Sprite mSprite;

	/**
	 * The constructor of entity.
	 *
	 * @param gameContext The game context.
	 * @param x           The default x coordinate.
	 * @param y           The default y coordinate.
	 */
	public DrawableEntity(GameContext gameContext, int x, int y) {
		super(gameContext, x, y);
		gameContext.getEngine().getGraphicsSubsystem().addDrawable(this);
	}

	/**
	 * The default constructor.
	 *
	 * @param gameContext The game context.
	 */
	public DrawableEntity(GameContext gameContext) {
		super(gameContext);
		gameContext.getEngine().getGraphicsSubsystem().addDrawable(this);
	}

	public Sprite getSprite() {
		return mSprite;
	}

	public void setSprite(Sprite sprite) {
		mSprite = sprite;
	}
}