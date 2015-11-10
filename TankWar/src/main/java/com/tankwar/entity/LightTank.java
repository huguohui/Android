package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * A light tank.
 */
final public class LightTank extends Tank {
    /**
     * Get width of entity.
     *
     * @return Width of entity.
     */
    @Override
    public int getWidth() {
        return 0;
    }

    /**
     * Get heigth of entity.
     *
     * @return Height of entity.
     */
    @Override
    public int getHeight() {
        return 0;
    }

    /**
     * Get the entity x value.
     *
     * @return The entity x value.
     */
    @Override
    public int getX() {
        return 0;
    }

    /**
     * Get the entity y value.
     *
     * @return The entity y value.
     */
    @Override
    public int getY() {
        return 0;
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

    /**
     * Get sprite graphics.
     */
    @Override
    public Bitmap[] getSprite() {
        return new Bitmap[0];
    }
}
