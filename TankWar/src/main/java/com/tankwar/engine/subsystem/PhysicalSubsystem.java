package com.tankwar.engine.subsystem;

import com.tankwar.engine.Engine;
import com.tankwar.engine.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a physical engine.
 *
 * @author hgh
 * @since 2015/11/06
 */
public class PhysicalSubsystem extends Subsystem {
	/**
	 * World subsystem reference.
	 */
	private WorldSubsystem mWorldSubsystem;

	/**
	 * The collidable enitys.
	 */
	private List<Entity> mCollisionCheckables = new ArrayList<>();

	/**
	 * Construct a physical subsystem object.
	 *
	 * @param engine Game engine.
	 */
	public PhysicalSubsystem(Engine engine) {
		super(engine);
		mWorldSubsystem = getEngine().getWorldSubsystem();
	}

	/**
	 * Enable a module.
	 */
	@Override
	public void enable() {
		super.enable();
	}

	/**
	 * Disable a module.
	 */
	@Override
	public void disable() {
		super.disable();
	}

	/**
	 * Add a entity that would like to check collision.
	 *
	 * @param entity A {@lick Entity}.
	 */
	public void addEntity(Entity entity) {
		this.mCollisionCheckables.add(entity);
	}

	/**
	 * Get all entitys.
	 *
	 * @return All {@link Entity}s.
	 */
	public List<Entity> getEntities() {
		return this.mCollisionCheckables;
	}

	/**
	 * Game loop tick.
	 */
	public void update() {
		Entity temp = null;
		List<Entity> entities = getEngine().getEntities();
		int pos = 1, size = entities.size();

		for (int j = 0; j < size; j++) {
			temp = entities.get(j);
			for (int i = j + 1; i < size; i++) {
				Entity entity = entities.get(i);
				if (temp != null && entity != null && !temp.equals(entity)) {
					if (temp instanceof CollisionCheckable && temp instanceof CollisionListener
							&& entity instanceof CollisionCheckable) {
						if (((CollisionCheckable) temp).isCollision(entity)) {
							((CollisionListener) entity).onCollision(temp);
							((CollisionListener) temp).onCollision(entity);
						} else {

						}
					}
				}
			}
		}

	}
}
