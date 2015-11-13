package com.tankwar.entity;


/**
 * Movable enity.
 * @since 2015/11/09
 */
public abstract class MovableEntity extends DrawableEntity implements Movable {

    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public MovableEntity(int x, int y) {
        super(x, y);
    }
}