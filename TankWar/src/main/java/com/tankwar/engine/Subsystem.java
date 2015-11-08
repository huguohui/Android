package com.tankwar.engine;

/**
 * A subsystem implements some functions.
 * @since 2015/11/06
 */
public abstract class Subsystem {
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
	 * Construct a module object by gameContext.
	 * @param gameContext The module rely.
	 */
	public Subsystem(GameContext gameContext) {
		this.mGameContext = gameContext;
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
	public abstract void run();


	/**
	 * Enable a module.
	 */
	public abstract void enable();


	/**
	 * Disable a module.
	 */
	public abstract void disable();
}