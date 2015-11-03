package com.tankwar.engine;

import android.graphics.Rect;

/**
 * General function of collision checking interface.
 * @author hui
 * @since 2015/11/01
 */
public interface CollisionCheckable {



    /**
     * Get a rect of this object collision range.
     * @return A rect of this object collision range.
     */
    Rect getCollisionRange();


    /**
     *
     * @param object
     * @return If collided, true else false.
     */
    boolean isCollision(CollisionCheckable object);
}
