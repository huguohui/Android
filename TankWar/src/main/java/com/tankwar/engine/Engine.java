package com.tankwar.engine;

import com.tankwar.client.Game;
import com.tankwar.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Engine abstract base class.
 * @author hgh.
 * @since 2015/11/06
 */
public abstract class Engine implements Runnable {
    /**
     * Start time.
     */
    private long mStartTime;


    /**
     * This subsystem whatever enable.
     */
    private boolean mIsEnable = true;

    /**
     * Is pause?
     */
    private boolean mIsPause = false;


    /**
     * Is stop?
     */
    private boolean mIsStop = false;


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
    private Map<Class<? extends Subsystem>, Subsystem> mSubsystems = new HashMap<>();


    /**
     * State listeners.
     */
    private List<StateListener> mStateListeners = new ArrayList<>();


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
     */
    public Engine() {
        this.mGameContext = GameContext.getGameContext();
    }


    /**
     * Get all subsystems.
     */
    public Set<Class<? extends Subsystem>> getSubsystems() {
        return this.mSubsystems.keySet();
    }


    /**
     * Get all listeners.
     */
    public List<StateListener> getStateListners() {
        return mStateListeners;
    }


    /**
     * Add a subsystem.
     */
    public void addSubsystem(Subsystem subsystem) {
        mSubsystems.put(subsystem.getClass(), subsystem);
    }


    /**
     * Get a subsystem.
     */
    public Subsystem getSubsystem(Class<? extends Subsystem> subsystem) {
        return mSubsystems.get(subsystem);
    }


    /**
     * Add a state listener.
     */
    public void addStateListener(StateListener stateListener) {
        mStateListeners.add(stateListener);
    }


    /**
     * Get game context.
     */
    public GameContext getGameContext() {
        return mGameContext;
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
    public abstract void initialize();


    /**
     * Start command.
     */
    public abstract void start();



    /**
     * Stop command.
     */
    public synchronized void stop() {
        mIsStop = true;
    }


	/**
	 * Get single instance of engine.
	 */
    public static Engine getEngine() {
        return null;
    }


    /**
     * Get stop state.
     */
    public boolean isStop() {
        return mIsStop;
    }
}