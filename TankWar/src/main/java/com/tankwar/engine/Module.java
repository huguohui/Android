package com.tankwar.engine;

/**
 * A module implements some functions.
 * @since 2015/11/06
 */
public abstract class Module {
	/**
	 * The context of module rely.
	 */
	protected Context context;


	/**
	 * Construct a module object by context.
	 * @param context The module rely.
	 */
	public Module(Context context) {
		this.context = context;
	}


	/**
	 * The work of module can do.
	 */
	public abstract void doWork();


	/**
	 * Enable a module.
	 */
	public abstract void enable();


	/**
	 * Check if module is enable.
	 */
	public abstract boolean isEnable();


	/**
	 * Disable a module.
	 */
	public abstract void disable();
}