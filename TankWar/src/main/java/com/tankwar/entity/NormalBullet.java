package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Defines a normal bullet.
 * @since 2015/11/04
 */
public class NormalBullet extends Bullet {
    /**
     * Checks this object if collisded some entity.
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
     * @param object
     */
    @Override
    public void onCollision(Entity object) {

    }

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
     * When screen was updated,
     * this method will be called.
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

    }

    /**
     * This method implemets move behavior.
     */
    @Override
    public void move() {

    }

    /**
     * Get sprite graphics.
     */
    @Override
    public Bitmap[] getSprite() {
        return new Bitmap[0];
    }
}
