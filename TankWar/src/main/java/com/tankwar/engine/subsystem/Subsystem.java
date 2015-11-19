package com.tankwar.engine.subsystem;

import com.tankwar.engine.Engine;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.Tick;

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
     * Get the game engine.
     * @return Game engine.
     */
    public Engine getEngine() {
        return mEngine;
    }
}