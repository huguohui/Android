package com.tankwar.engine;

import com.tankwar.entity.absentity.Entity;

/**
 * General function of collision checking interface.
 * @author hui
 * @since 2015/11/01
 */
public interface CollisionCheckable {
    /**
     * Checks this object if collided some entity.
     * @param entity THe entity.
     * @return If collided true else false.
     */
    boolean isCollision(Entity entity);
}
