package com.tankwar.entity;

/**
 * A base class of describe a terrain.
 * @author hgh
 * @since 2015/11/04
 */
public abstract class Terrain extends DrawableEntity {

    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Terrain(int x, int y) {
        super(x, y);
    }
}