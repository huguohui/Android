package com.tankwar.entity.factory;

import com.tankwar.engine.entity.Entity;

/**
 * Tank factory interface.
 *
 * @since 2015/11/13
 */
public interface Factory {
	/**
	 * Create a entity.
	 */
	Entity create();
}
