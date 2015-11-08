package com.tankwar.engine;

/**
 * Implements a physical engine.
 * @author hgh
 * @since 2015/11/06
 */
class PhysicalSubsystem extends Subsystem {

    /**
     * Construct a module object by gameContext.
     *
     * @param gameContext The module rely.
     */
    public PhysicalSubsystem(GameContext gameContext) {
        super(gameContext);
    }

    /**
     * The work of module can do.
     */
    @Override
    public void start() {

    }

    /**
     * Stop subsystem.
     */
    @Override
    public void stop() {
        super.stop();
    }

    /**
     * Enable a module.
     */
    @Override
    public void enable() {

    }


   /**
     * Disable a module.
     */
    @Override
    public void disable() {

    }
}
