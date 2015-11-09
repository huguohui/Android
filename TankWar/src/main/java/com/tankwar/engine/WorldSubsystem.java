package com.tankwar.engine;

/**
 * The game world subsystem implements.
 * @since 2015/11/08
 */
public class WorldSubsystem extends Subsystem {
    /** World top margin.*/
    public final static int WORLD_TOP = 20;


    /** World left margin. */
    public final static int WORLD_LEFT = 20;


    /** World right margin. */
    public final static int WORLD_RIGHT = 20;


    /** World bottom margin.*/
    public final static int WORLD_BOTTOM = 20;


    /** World height.*/
    public static int WORLD_HEIGHT = 13 * 28;



    /**
     * Construct a module object by gameContext.
     */
    public WorldSubsystem(Engine engine) {
        super(engine);
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


    /**
     * Game loop tick.
     */
    public void tick() {
        
    }


    /**
     */
}
