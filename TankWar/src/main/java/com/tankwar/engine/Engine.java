package com.tankwar.engine;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Engine core class.
 * @author hgh.
 * @since 2015/11/06
 */
public abstract class Engine {
    /**
     * Start time.
     */
    protected long startTime;


    /**
     * Modules.
     */
    protected List<Module> modules;


    /**
     * State listeners.
     */
    protected List<EngineStateListener> stateListeners;


    /**
     * All threads.
     */
    private List<Thread> threads;


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


	/**
	 * Get single instance of engine.
	 */
    public static Engine getEngine() {
        return null;
    }
}