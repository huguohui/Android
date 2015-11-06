package com.tankwar.engine.

/**
 * Common control interface.
 * @since 2015/11/06
 */
public interface Controlable {
	/**
	 * Initialization control.
	 */
	public abstract void init();


	/**
	 * Start command.
	 */
	public abstract void start();


	/**
	 * Pause command.
	 */
	public abstract void pause();


	/**
	 * Resume command.
	 */
	public abstract void resume();


	/**
	 * Stop command.
	 */
	public abstract void stop();
}