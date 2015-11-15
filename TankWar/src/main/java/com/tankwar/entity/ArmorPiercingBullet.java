package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.GameContext;

/**
 * A subclass of bullet, to denoting a armor piercing bullet.
 *
 * @author hui
 * @version 1.0
 * @since 2015/10/30
 */
public class ArmorPiercingBullet extends Bullet {

    /**
     * The constructor to do some initialize work,
     * parameter tank tell bullet belong which tank.
     *
     * @param gameContext The game context.
     * @param owner       The bullet owner tank.
     */
    public ArmorPiercingBullet(GameContext gameContext, Tank owner) {
        super(gameContext, owner);
    }

    /**
     * The default constructor.
     *
     * @param gameContext The game context.
     */
    public ArmorPiercingBullet(GameContext gameContext) {
        super(gameContext);
    }

    /**
     * Checks this object if collided some entity.
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
     * @param object The entity.
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
     * This method implements move behavior.
     */
    @Override
    public void move() {

    }
}
