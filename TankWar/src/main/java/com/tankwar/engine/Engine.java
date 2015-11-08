package com.tankwar.engine;

import com.tankwar.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Engine core class.
 * @author hgh.
 * @since 2015/11/06
 */
public abstract class Engine implements Runnable {
    /**
     * Start time.
     */
    private long mStartTime;


    /**
     * Game context.
     */
    private GameContext mGameContext;


    /**
     * All entity.
     */
    private List<Entity> mEntitys;


    /**
     * Modules.
     */
    private List<Subsystem> mSubsystems = new ArrayList<>();


    /**
     * State listeners.
     */
    private List<EngineStateListener> mStateListeners = new ArrayList<>();


    /**
     * All threads.
     */
    private List<Thread> mAllocedThread = new ArrayList<>();


    /**
     * Singleton instance.
     */
    private static Engine mEngine;


    /**
     * Constructor.
     * @param gameContext Game gameContext.
     */
    public Engine(GameContext gameContext) {
        this.mGameContext = gameContext;
    }


    /**
     * Get all subsystems.
     */
    public List<Subsystem> getSubsystems() {
        return this.mSubsystems;
    }


    /**
     * Get all listeners.
     */
    public List<EngineStateListener> getStateListners() {
        return mStateListeners;
    }


    /**
     * Add a subsystem.
     */
    public void addSubsystem(Subsystem subsystem) {
        mSubsystems.add(subsystem);
    }


    /**
     * Add a state listener.
     */
    public void addStateListener(EngineStateListener stateListener) {
        mStateListeners.add(stateListener);
    }


    /**
     * Alloc a thread by a runnable.
     */
    public Thread allocThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        mAllocedThread.add(thread);
        return thread;
    }


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