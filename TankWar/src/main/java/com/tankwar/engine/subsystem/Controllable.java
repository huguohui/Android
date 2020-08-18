package com.tankwar.engine.subsystem;

import com.tankwar.engine.entity.Entity;
import com.tankwar.engine.entity.MovableEntity;

/**
 * A controllable something.
 *
 * @since 2015/12/08
 */
public interface Controllable {
	/**
	 * Move a distance on current direction.
	 */
	void move();

	/**
	 * Move a distance by special direction.
	 */
	void move(MovableEntity.Direction direction);

	/**
	 * Stopping!
	 */
	void stop();

	/**
	 * Attacks on current direction.
	 */
	void attack();

	/**
	 * Attacks to some target.
	 */
	void attack(Entity entity);
}
