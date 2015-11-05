package com.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.tankwar.engine.CollisionCheckable;

/**
 * A subclass of bullet, to denoting a armor piercing bullet.
 *
 * @author hui
 * @version 1.0
 * @since 2015/10/30
 */
public class ArmorPiercingBullet extends Bullet {

    /**
     * Checks this object if collisded some entity.
     *
     * @param entity If collided true else false.
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
    public void onCollision(Object object) {

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
    public void onFrameUpdate(Canvas canvas) {

    }
}
