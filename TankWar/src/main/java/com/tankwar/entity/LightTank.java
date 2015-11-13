package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * A light tank.
 */
final public class LightTank extends Tank {

    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public LightTank(int x, int y) {
        super(x, y);
    }


    /**
     * Empty constructor.
     */
    public LightTank() {
        super();
    }


    /**
     * Checks this object if collided some entity.
     *
     * @param entity@return If collided true else false.
     */
    @Override
    public boolean isCollision(Entity entity) {
        return false;
    }

    /**
     * When this object colliding another object,
     * this method will be called.
     *
     * @param object The entity.
     */
    @Override
    public void onCollision(Entity object) {

    }


    /**
     * When screen was updated,
     * this method will be called.
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

    }
}
