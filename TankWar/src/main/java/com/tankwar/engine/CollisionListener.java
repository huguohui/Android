package com.tankwar.engine;

/**
 * A collision event observer interface.
 * @author hgh
 * @since 2015/10/29
 */
public interface CollisionListener {
    /**
     * When this object colliding another object,
     * this method will be called.
     */
    void onCollision(Object object);
}
