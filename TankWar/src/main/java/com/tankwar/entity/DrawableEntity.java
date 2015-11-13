package com.tankwar.entity;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import com.tankwar.engine.Drawable;
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
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public DrawableEntity(int x, int y) {
        super(x, y);
    }


    public DrawableEntity() {}
}