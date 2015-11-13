package com.tankwar.entity;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import android.graphics.Rect;

/**
 * A obstacle is a entity that can't cross.
 * But maybe the obstacle could be destroy,
 * when obstacle was destroyed, it was passable.
 *
 * @since 2015/11/10
 */
public abstract class Obstacle extends Terrain
	implements CollisionCheckable, CollisionListener, Destroyable {
	/** The valid collision regions. */
	private Rect[] mDestroyableRegion;


    /** Obstacle is valid? */
    private boolean mIsValid = false;

    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Obstacle(int x, int y) {
        super(x, y);
    }


    /**
     * Get destoryable regions.
     * @return Destroyable regions.
     */
	public Rect[] getDestroyableRegions() {
		return mDestroyableRegion;
	}


    /**
     * Get the valid of obstacle.
     * @return Validate of obstacle.
     */
	public boolean isValid() {
		return mIsValid;
	}
}