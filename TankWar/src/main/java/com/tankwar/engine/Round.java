package com.tankwar.engine;

import com.tankwar.engine.subsystem.Tick;

/**
 * The round for game.
 * @since 2015/11/23
 */
public abstract class Round implements Tick {
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


    /**
     * Start a round.
     */
    public abstract void start();


    /**
     * Progress a round.
     */
    public abstract void progress();


    /**
     * If this round is cleared, start a new round by this method returned.
     * @return a new {@link Round}.
     */
    public abstract Round getNext();


    /**
     * Get the round if is end round.
     * @reutrn true is end, false not end.
     */
    public abstract boolean isEnd();



    /**
     * Check this round state on per tick.
     */
    public void tick() {
        if (!mIsStart) start();

        if (!isClear()) {
            progress();
            if (isEnd()) {

            }
        }
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
}
