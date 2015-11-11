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
    private GameContext mGameContext = GameContext.getGameContext();


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
     * The engine power.
     */
    private Thread mPower;


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
     * Get get game instance.
     */
    public final Game getGame() {
        return this.getGameContext().getGame();
    }


    /**
     * Get game context.
     */
    public final GameContext getGameContext() {
        return mGameContext;
    }


    /**
     * Alloc a thread by a runnable.
     */
    public synchronized Thread allocThread(Runnable runnable) {
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


    /**
     * Set if stop.
     * @param mIsStop  stop?
     */
    public void setIsStop(boolean mIsStop) {
        this.mIsStop = mIsStop;
    }


    /**
     * Set if pause.
     * @param mIsPause pause?
     */
    public void setIsPause(boolean mIsPause) {
        this.mIsPause = mIsPause;
    }

    /**
     * Get pause state.
     */
    public boolean isPause() {
        return mIsPause;
    }


    /**
     * Get stop state.
     */
    public boolean isStop() {
        return mIsStop;
    }


    /**
     * The game power.
     * @param power Game power.
     */
    public final void setPower(Thread power) {
        mPower = power;
    }


    /**
     * Get game power.
     * @return game power.
     */
    public final Thread getPower() {
        return mPower;
    }



    /**
     * Engine state listener.
     * @since 2015/11/06
     */
    public interface StateListener {
        /**
         * When engine initialized.
         */
        void onInitialize(Engine engine);


        /**
         * When engine start work.
         */
        void onStart(Engine engine);


        /**
         * When engine stop work.
         */
        void onStop(Engine engine);


        /**
         * When appear exception.
         */
        void onException(Engine engine);
    }
}