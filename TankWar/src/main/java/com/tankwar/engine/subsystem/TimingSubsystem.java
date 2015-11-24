package com.tankwar.engine.subsystem;

import com.tankwar.engine.Engine;

/**
 * The timing subsystem, to keep a system timer.
 * @since @2015/11/21
 */
public class TimingSubsystem extends Subsystem implements Engine.StateListener {
    /** The system timing. */
    private long mSystemTime = 0;

    /** The the per frame distance. */
    private int mFrameTime = 0;

    /** The prev frame time. */
    private long mPrevTime = 0;


    /**
     * Construct a module object by gameContext.
     *
     * @param engine
     */
    public TimingSubsystem(Engine engine) {
        super(engine);
        engine.addStateListener(this);
    }


    /**
     * Get system time.
     * @return System time.
     */
    public long getSystemTime() {
        return mSystemTime;
    }


    /**
     * Get per frame distance.
     * @return Per frame distance.
     */
    public int getFrameTime() {
        return mFrameTime;
    }


    /**
     * Per loop will call this method.
     */
    @Override
    public void update() {
        mFrameTime = (int)(System.currentTimeMillis() - mPrevTime);
        mSystemTime += mFrameTime;
        mPrevTime = System.currentTimeMillis();
    }

    /**
     * When engine initialized.
     *
     * @param engine engine engine.
     */
    @Override
    public void onInitialize(Engine engine) {

    }

    /**
     * When engine start work.
     *
     * @param engine engine engine.
     */
    @Override
    public void onStart(Engine engine) {
        mPrevTime = System.currentTimeMillis();
    }

    /**
     * When engine pause.
     *
     * @param engine engine engine.
     */
    @Override
    public void onPause(Engine engine) {

    }

    /**
     * When engine resume.
     *
     * @param engine engine engine.
     */
    @Override
    public void onResume(Engine engine) {

    }

    /**
     * When engine stop work.
     *
     * @param engine engine engine.
     */
    @Override
    public void onStop(Engine engine) {

    }
}
