package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * A kind of obstacle, it can destroy.
 */
public class Wall extends Obstacle {
    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Wall(int x, int y) {
        super(x, y);
    }

    /**
     * Method destory to implements destory a object.
     */
    @Override
    public void destory() {

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
     * Checks this object if collided some entity.
     *
     * @param entity THe entity.
     * @return If collided true else false.
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
}