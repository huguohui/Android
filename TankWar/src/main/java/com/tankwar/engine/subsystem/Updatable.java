package com.tankwar.engine.subsystem;

/**
 * Per game main loop will trigger a tick.
 *
 * @author hgh
 * @since 2015/11/09
 */
public interface Updatable {
	/**
	 * Per loop will call this method.
	 */
	void update();
}
