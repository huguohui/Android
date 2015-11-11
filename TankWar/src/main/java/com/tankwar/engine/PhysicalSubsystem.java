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
     * Construct a physical subsystem object.
     * @param engine Game engine.
     */
    public PhysicalSubsystem(Engine engine) {
        super(engine);
    }

    /**
     * Start physical subsystem.
     */
    @Override
    public void start() {
        mWorldSubsystem = (WorldSubsystem)getEngine().getSubsystem(WorldSubsystem.class);
        
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
        super.enable();
    }


   /**
     * Disable a module.
     */
    @Override
    public void disable() {
        super.disable();
    }


    /**
     * Game loop tick.
     */
    public void tick() {
        
    }
}