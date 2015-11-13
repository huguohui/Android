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
}
