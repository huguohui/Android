package com.tankwar.engine;

/**
 * Implements a physical engine.
 * @author hgh
 * @since 2015/11/06
 */
class PhysicalSubsystem extends Subsystem {
    /**
     * World subsystem reference.
     */
    private WorldSubsystem mWorldSubsystem;


    /**
     * Construct a module object by gameContext.
     */
    public PhysicalSubsystem(Engine engine) {
        super(engine);
    }

    /**
     * The work of module can do.
     */
    @Override
    public void start() {
       // mWorldSubsystem = getEngine().getSubsystems().contains();
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
