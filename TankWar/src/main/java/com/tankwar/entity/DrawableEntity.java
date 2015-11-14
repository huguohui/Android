package com.tankwar.entity;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import com.tankwar.engine.Drawable;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.Sprite;

/**
 * Describe a drawable entity in game world.
 * @since 2015/11/04
 */
public abstract class DrawableEntity extends Entity
        implements Drawable {
    /**
     * The constructor of entity.
     *
     * @param gameContext The game context.
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public DrawableEntity(GameContext gameContext, int x, int y) {
        super(gameContext, x, y);
    }


    /**
     * The default constructor.
     * @param gameContext The game context.
     */
    public DrawableEntity(GameContext gameContext) {
        super(gameContext);
    }
}