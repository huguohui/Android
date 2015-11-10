package com.tankwar.entity;

import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import android.graphcis.Rect;

/**
 * A obstacle is a enity that cant't cross.
 * But maybe the obstacle could be destory,
 * when obstacle was destoryed, it was passable.
 *
 * @since 2015/11/10
 */
public abstract Obstacle extends Entity
	implements CollisionCheckable, CollisionListener, Destoryable {
	/** The vaild collision regions. */
	private Rect[] mDestoryableRegion;

	/** The obstacle whatever is valid.(Not complete destory)*/
	private boolean mIsValid = true;

	public Rect[] getDestoryableRegions() {
		return mDestoryableRegion;
	}


	public boolean isValid() {
		return mIsVaild;
	}
}