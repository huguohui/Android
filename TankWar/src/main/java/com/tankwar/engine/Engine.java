package com.tankwar.engine;

import com.tankwar.game.Game;
import com.tankwar.engine.subsystem.ControlSubsystem;
import com.tankwar.engine.subsystem.GraphicsSubsystem;
import com.tankwar.engine.subsystem.PhysicalSubsystem;
import com.tankwar.engine.subsystem.Subsystem;
import com.tankwar.engine.subsystem.WorldSubsystem;
import com.tankwar.entity.absentity.Entity;
import com.tankwar.utils.Log;

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
public class Engine implements Runnable {
    /** Start time. */
    private long mStartTime;

    /** This subsystem whatever enable. */
    private boolean mIsEnable = true;

    /** Is pause? */
    private boolean mIsPause = false;

    /** Is stop? */
    private boolean mIsStop = false;

    /** Game context. */
    private GameContext mGameContext = GameContext.getGameContext();

    /** Modules. */
    private Map<Class<? extends Subsystem>, Subsystem> mSubsystems = new HashMap<>();

    /** All threads. */
    private List<Thread> mAllocedThread = new ArrayList<>();

    /** All entity exclude terrain in world. */
    private List<Entity> mEntitys = new ArrayList<>();

    /** All state listeners. */
    private List<StateListener> mStateListeners = new ArrayList<>();

    /** Singleton instance. */
    private static Engine mEngine;

    /** The engine power. */
    private Thread mPower;

    /** The world subsystem. */
    private WorldSubsystem mWorldSubsystem;

    /** The physical subsystem. */
    private PhysicalSubsystem mPhysicalSubsystem;


    /**
     * Constructor.
     */
    private Engine() {
        this.addSubsystem(new PhysicalSubsystem(this));
        this.addSubsystem(new GraphicsSubsystem(this));
        this.addSubsystem(new WorldSubsystem(this));
        this.addSubsystem(new ControlSubsystem(this));
    }

    /**
     * Get all subsystems.
     */
    public Set<Class<? extends Subsystem>> getSubsystems() {
        return this.mSubsystems.keySet();
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
     * Add state listener.
     * @param stateListener The state listener.
     */
    public void addStateListener(StateListener stateListener) {
        this.mStateListeners.add(stateListener);
    }


    /**
     * Initialization control.
     */
    public void initialize() {
        for (StateListener listener : mStateListeners) {
            listener.onInitialize(this);
        }
        this.setPower(this.allocThread(this));
    }


    /**
     * Start Engine.
     */
    public void start() {
        initialize();
        for (StateListener listener : mStateListeners) {
            listener.onStart(this);
        }
        this.getPower().start();
    }


    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    public void run() {
        try {
            while (!this.isStop()) {
                if (this.isPause()) {
                    wait();
                }

                for (Class<? extends Subsystem> key : this.getSubsystems()) {
                    Tick tick = getSubsystem(key);
                    tick.tick();
                }
            }
        }catch(InterruptedException ex) {
            Log.e(ex);
        }finally {
            for (Thread thread : mAllocedThread) {
                if (thread.getState().equals(Thread.State.TERMINATED)) {
                    thread = null;
                }
            }
        }
    }


    /**
     * Create a entity and add entity to list.
     */
    public void createTank() {
        //mEntitys.add();
    }


    /**
     * Get all created entities.
     * @return All created entities.
     */
    public List<Entity> getEntitys() {
        return mEntitys;
    }


    /**
     * Pause engine.
     */
    public synchronized void pause() {
        this.setIsPause(true);
    }

    /**
     * Resume engine.
     */
    public synchronized void resume() {
        this.setIsPause(false);
    }

    /**
     * Stop engine.
     */
    public synchronized void stop() {
        this.setIsStop(true);
    }


    /**
     * Get single instance of engine.
     */
    public static Engine getEngine() {
        return new Engine();
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
     * Game engine state listener.
     * @since 2015/11/06
     */
    public interface StateListener {
        /**
         * When engine initialized.
         * @param engine engine engine.
         */
        void onInitialize(Engine engine);


        /**
         * When engine start work.
         * @param engine engine engine.
         */
        void onStart(Engine engine);

        /**
         * When engine pause.
         * @param engine engine engine.
         */
        void onPause(Engine engine);


        /**
         * When engine resume.
         * @param engine engine engine.
         */
        void onResume(Engine engine);


        /**
         * When engine stop work.
         * @param engine engine engine.
         */
        void onStop(Engine engine);


        /**
         * When engine exit.
         * @pram engine engine engine.
         */
        void onExit(Engine engine);
    }
}