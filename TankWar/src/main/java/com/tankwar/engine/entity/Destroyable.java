package com.tankwar.engine.entity;

import android.graphics.Rect;

/**
 * Destroyable is a kind of object, that could be destroy.
 * @since 2015/11/10
 */
public interface Destroyable {
	/**
	 * Destroy a region of destroyable.
	 */
	void destroy(Rect r);


	/**
	 * Destroy full region of destroyable.
	 */
	void destroy();
}