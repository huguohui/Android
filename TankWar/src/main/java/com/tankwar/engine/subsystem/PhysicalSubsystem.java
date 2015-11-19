package com.tankwar.engine.subsystem;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import com.tankwar.engine.Engine;
import com.tankwar.entity.absentity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a physical engine.
 * @author hgh
 * @since 2015/11/06
 */
public class PhysicalSubsystem extends Subsystem {
    /**
     * World subsystem reference.
     */
    private WorldSubsystem mWorldSubsystem;

    /** The collidable enitys. */
    private List<Entity> mCollisionCheckables = new ArrayList<>();


    /**
     * Construct a physical subsystem object.
     * @param engine Game engine.
     */
    public PhysicalSubsystem(Engine engine) {
        super(engine);
        mWorldSubsystem = (WorldSubsystem)getEngine().getSubsystem(WorldSubsystem.class);
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
     * @param entity A {@lick Entity}.
     */
    public void addEntity(Entity entity) {
        this.mCollisionCheckables.add(entity);
    }


    /**
     * Get all entitys.
     * @return All {@link Entity}s.
     */
    public List<Entity> getEntitys() {
        return this.mCollisionCheckables;
    }


    /**
     * Game loop tick.
     */
    public void tick() {
        Entity temp = null;
        for (Entity entity : mCollisionCheckables) {
            if (temp == null)
                temp = entity;

            if (temp != null && entity != null && !temp.equals(entity)) {
                if (temp instanceof CollisionCheckable && temp instanceof CollisionListener
                        && entity instanceof CollisionCheckable) {
                    if (((CollisionCheckable) temp).isCollision(entity)) {
                        ((CollisionListener) entity).onCollision(temp);
                    }
                }
            }
        }

    }
}
