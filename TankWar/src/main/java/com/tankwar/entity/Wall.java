package com.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.Entity;
import com.tankwar.engine.entity.Obstacle;

/**
 * A kind of obstacle, it can destroy.
 */
public class Wall extends Obstacle {
    /**
     * The constructor of entity.
     *
     * @param gameContext The game context.
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Wall(GameContext gameContext, int x, int y) {
        super(gameContext, x, y);
    }

    /**
     * Destroy a region of destroyable.
     *
     * @param r
     */
    @Override
    public void destroy(Rect r) {

    }

    /**
     * Method destroy to implements destroy a object.
     */
    @Override
    public void destroy() {

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
     * The layer index of draw.
     */
    @Override
    public int getIndex() {
        return 0;
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
