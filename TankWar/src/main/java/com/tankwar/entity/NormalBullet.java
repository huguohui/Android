package com.tankwar.entity;

import android.graphics.Canvas;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.Entity;

/**
 * Defines a normal bullet.
 *
 * @since 2015/11/04
 */
public class NormalBullet extends Bullet {
	/**
	 * The constructor to do some initialize work,
	 * parameter tank tell bullet belong which tank.
	 *
	 * @param gameContext The game context.
	 * @param owner       The bullet owner tank.
	 */
	public NormalBullet(GameContext gameContext, Tank owner) {
		super(gameContext, owner);
	}

	/**
	 * The default constructor.
	 *
	 * @param gameContext The game context.
	 */
	public NormalBullet(GameContext gameContext) {
		super(gameContext);
	}

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
	 * The layer index of draw.
	 */
	@Override
	public int getIndex() {
		return 0;
	}

	/**
	 * This method implemets move behavior.
	 */
	@Override
	public void move() {

	}

	/**
	 * Per loop will call this method.
	 */
	@Override
	public void update() {

	}
}
