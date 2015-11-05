package com.tankwar.engine;

import android.graphics.Rect;

import com.tankwar.entity.Entity;

/**
 * General function of collision checking interface.
 * @author hui
 * @since 2015/11/01
 */
public interface CollisionCheckable {
    /**
     * Checks this object if collisded some entity.
     * @param entity.
     * @return If collided true else false.
     */
    boolean isCollision(Entity entity);
}
