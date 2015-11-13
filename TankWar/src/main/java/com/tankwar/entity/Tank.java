package com.tankwar.entity;

/**
 * Tank base class.
 * @since 2015/11/04
 */
public abstract class Tank extends DrawableEntity
{
    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Tank(int x, int y) {
        super(x, y);
    }


    /**
     * Empty constructor.
     */
    public Tank() {}
}

