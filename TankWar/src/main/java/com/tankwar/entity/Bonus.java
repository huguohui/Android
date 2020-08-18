package com.tankwar.entity;

import com.tankwar.engine.subsystem.CollisionCheckable;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.engine.entity.Entity;

final public class Bonus extends Entity implements CollisionCheckable {

	/**
	 * The constructor of entity.
	 *
	 * @param gameContext The game context.
	 * @param x           The default x coordinate.
	 * @param y           The default y coordinate.
	 */
	public Bonus(GameContext gameContext, int x, int y) {
		super(gameContext, x, y);
		this.setX((int) Math.abs(Math.random() * 10240 % (WorldSubsystem.WORLD_WIDTH - 0)));
		this.setY((int) Math.abs(Math.random() * 10240 % (WorldSubsystem.WORLD_HEIGHT - 0)));
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
}
