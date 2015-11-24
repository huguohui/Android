package com.tankwar.engine;

import com.tankwar.engine.subsystem.Updatable;

/**
 * The round for game.
 * @since 2015/11/23
 */
public abstract class Round implements Updatable {
    /** The round number. */
    private int mRoundNumber = 0;

    /** The max round time. */
    private int mMaxRoundTime = 0;

    /** The max round retry times. */
    private int mMaxRetryTimes = 0xff;

    /** The round is start? */
    private boolean mIsStart = false;

    /** The round is clear? */
    private boolean mIsClear = false;

    /** The round is failed? */
    private boolean mIsFailed = false;

    /** The engine instance. */
    private Engine mEngine;


    /**
     * Default constructor.
     * @param engine A {@link Engine}.
     */
    public Round(Engine engine) {
        mEngine = engine;
    }


    /**
     * Start a round.
     */
    public abstract void start();


    /**
     * Handler round progress.
     */
    public abstract void progress();


    /**
     * When round failed, to do something.
     */
    public abstract void failed();


    /**
     * When round clear to do something.
     */
    public abstract void clear();


    /**
     * If this round is cleared, start a new round by this method returned.
     * @return a new {@link Round}.
     */
    public abstract Round getNext();



    /**
     * Check this round state on per tick.
     */
    public void tick() {
        if (!mIsStart) start();

        if (!isClear()) {
            if (isFailed()) {
                failed();
                return;
            }
            progress();
        }

        getNext();
    }


    /**
     * Get current round number.
     * @return Current round number.
     */
    public int getRoundNumber() {
        return mRoundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        mRoundNumber = roundNumber;
    }

    public int getMaxRoundTime() {
        return mMaxRoundTime;
    }

    public void setMaxRoundTime(int maxRoundTime) {
        mMaxRoundTime = maxRoundTime;
    }

    public int getMaxRetryTimes() {
        return mMaxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        mMaxRetryTimes = maxRetryTimes;
    }

    public boolean isStart() {
        return mIsStart;
    }

    public void setIsStart(boolean isStart) {
        mIsStart = isStart;
    }

    public void setIsClear(boolean isClear) {
        mIsClear = isClear;
    }

    public boolean isClear() {
        return mIsClear;
    }

    public boolean isFailed() {
        return mIsFailed;
    }

    public void setIsFailed(boolean isFailed) {
        mIsFailed = isFailed;
    }

    public Engine getEngine() {
        return mEngine;
    }

    public void setEngine(Engine engine) {
        mEngine = engine;
    }
}
