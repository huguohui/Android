package com.tankwar.engine;

/**
 * A subsystem implements some functions.
 * @since 2015/11/06
 */
public abstract class Subsystem implements Tick {
	/**
	 * The context of module rely.
	 */
	private GameContext mGameContext;


    /**
     * This subsystem whatever enable.
     */
    private boolean mIsEnable = true;


    /**
     * Record subsystem start time.
     */
    private long mStartTime;


    /**
     * Is pause?
     */
    private boolean mIsPause = false;


    /**
     * Is stop?
     */
    private boolean mIsStop = false;


    /**
     * Game engine.
     */
    private Engine mEngine;


	/**
	 * Construct a module object by gameContext.
	 */
	public Subsystem(Engine engine) {
        this.mEngine = engine;
	}


    /**
     * Check if module is enable.
     */
    public boolean isEnable() {
        return mIsEnable;
    }


	/**
	 * The work of module can do.
	 */
	public abstract void start();


    /**
     * Stop subsystem.
     */
    public void stop() {
        this.mIsStop = true;
    }


	/**
	 * Enable a module.
	 */
	public void enable() {
        this.mIsEnable = true;
    }


	/**
	 * Disable a module.
	 */
	public void disable() {
        this.mIsPause = false;
    }


    /**
     * Pause!
     */
    public void pause() {
        this.mIsPause = true;
    }


    public void resume() {
        this.mIsPause = false;
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
     * Get the game engine.
     * @return Game engine.
     */
    public Engine getEngine() {
        return mEngine;
    }
}