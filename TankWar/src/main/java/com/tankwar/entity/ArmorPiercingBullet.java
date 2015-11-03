package com.tankwar.entity;

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

    @Override
    public Rect getCollisionRange() {
        return null;
    }

    @Override
    public boolean isCollision(CollisionCheckable object) {
        return false;
    }

    @Override
    public void onCollision(Object object) {

    }

    @Override
    public void onFrameUpdate() {

    }

    @Override
    public void onFrameUpdate(Drawable drawable) {

    }
}
