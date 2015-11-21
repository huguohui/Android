package com.tankwar.engine.subsystem;

import com.tankwar.engine.Engine;

/**
 * The timing subsystem, to keep a system timer.
 * @since @2015/11/21
 */
public class TimingSubsystem extends Subsystem {
    /** The system timing. */
    private long mSystemTime = 0;


    /**
     * Construct a module object by gameContext.
     *
     * @param engine
     */
    public TimingSubsystem(Engine engine) {
        super(engine);
    }


    /**
     * Get system time.
     * @return System time.
     */
    public long getSystemTime() {
        return mSystemTime;
    }


    /**
     * Per loop will call this method.
     */
    @Override
    public void tick() {
        mSystemTime = System.currentTimeMillis() - mSystemTime;
    }
}
