package com.tankwar.engine;

/**
 * Engine core class.
 * @author hgh.
 * @since 2015/11/06
 */
public abstract class Engine implements Controllable {
	/**
	 * Construct a engine instance.
	 */
	private Engine() {

	}


	/**
	 * Get single instance of engine.
	 */
	public static Engine getEngine() {
		return new Engine();
	}
}