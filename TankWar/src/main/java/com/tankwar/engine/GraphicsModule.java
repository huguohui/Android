package com.tankwar.engine;

import com.tankwar.client.GameContext;

/**
 * The engine of handling graphics.
 * @since 2015/11/06
 */

public class GraphicsModule extends Module {
	/**
	 * Only constructor.
	 */
	public GraphicsModule(GameContext context) {
        super(context);
	}


	/**
	 * Do some work.
	 */
	public void doWork() {

	}

    /**
     * Enable a module.
     */
    @Override
    public void enable() {

    }

    /**
     * Check if module is enable.
     */
    @Override
    public boolean isEnable() {
        return false;
    }

    /**
     * Disable a module.
     */
    @Override
    public void disable() {

    }
}