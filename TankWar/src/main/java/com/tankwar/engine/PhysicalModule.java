package com.tankwar.engine;

import com.tankwar.client.GameContext;
import com.tankwar.engine.Module;

/**
 * Implements a physical engine.
 * @author hgh
 * @since 2015/11/06
 */
class PhysicalModule extends Module {

    /**
     * Construct a module object by context.
     *
     * @param context The module rely.
     */
    public PhysicalModule(GameContext context) {
        super(context);
    }

    /**
     * The work of module can do.
     */
    @Override
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
