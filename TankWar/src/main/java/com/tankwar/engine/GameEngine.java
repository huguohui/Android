package com.tankwar.engine;

/**
 * Client engine implements.
 * @since 2015/11/06
 */
public class GameEngine extends Engine {

    /**
     * Construct a engine instance.
        */
    private GameEngine() {
    }

    /**
     * Initialization control.
     */
    @Override
    public void init() {

    }

    /**
     * Start command.
     */
    @Override
    public void start() {

    }

    /**
     * Pause command.
     */
    @Override
    public void pause() {

    }

    /**
     * Resume command.
     */
    @Override
    public void resume() {

    }

    /**
     * Stop command.
     */
    @Override
    public void stop() {

    }


    /**
     * Get single instance of engine.
     */
    public static Engine getEngine() {
        return new GameEngine();
    }
}


