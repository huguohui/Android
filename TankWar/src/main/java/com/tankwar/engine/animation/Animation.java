package com.tankwar.engine.animation;

import android.graphics.Bitmap;

import com.tankwar.engine.Engine;
import com.tankwar.engine.subsystem.Drawable;
import com.tankwar.engine.subsystem.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The animation base class.
 * @since 2015/11/20
 */
public abstract class Animation implements Drawable {
    /** The animation states. */
    public final static int AS_NONE    = 0x00;
    public final static int AS_PLAYING = 0x01;
    public final static int AS_PAUSED  = 0x02;
    public final static int AS_FINISHED= 0x03;

    /** The all frames of animation. */
    private Descriptor mDescriptor;

    /** The all state listeners. */
    private List<StateListener> mStateListeners = new ArrayList<>();

    /** The engine of game. */
    private Engine mEngine;

    /** The current animation playing state. */
    private int mState = AS_NONE;

    /** The animation start time. */
    private long mStartTime;

    private long mFrameStartTime;

    /** The animation x coordinate to draw. */
    private int mX;

    /** The animation y coordinate to draw. */
    private int mY;

    /** The frame counter. */
    private int mFrameCount = 0;

    /** The animation play time count. */
    private int mPlayTime = 0;


    /**
     * To create a animation object by two parameters.
     *
     * @param engine The engine.
     * @param descriptor The frames.
     * @param x The x coordinate to draw.
     * @param y The y coordinate to draw.
     */
    public Animation(Engine engine, Descriptor descriptor, int x, int y)
            throws NullPointerException, IllegalArgumentException {
        this(engine, descriptor);
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("The x or y can't less than 0!");
    }


    /**
     * To create a animation object by two parameters.
     *
     * @param engine The engine.
     * @param descriptor The frames.
     */
    public Animation(Engine engine, Descriptor descriptor) throws NullPointerException {
        this(engine);
        if (mDescriptor == null || descriptor == null)
            throw new NullPointerException("The Engine or Descriptor can't null!");

        this.mDescriptor = descriptor;
    }


    /**
     * The creating a animation object by a engine.
     * @param engine The game engine.
     */
    public Animation(Engine engine, int x, int y) throws IllegalArgumentException {
        this(engine);
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("The x or y can't less than 0!");

        this.mX = x;
        this.mY = y;
    }


    /**
     * The creating a animation object by a engine.
     * @param engine The game engine.
     */
    public Animation(Engine engine) {
        if (engine == null)
            throw new NullPointerException("Engine instance can't null!");

        this.mEngine = engine;
    }


    /**
     * Get the state of animation.
     * @reuturn The state of animation.
     */
    public int getState() {
        return mState;
    }


    /**
     * Set state of animation.
     * @param state State of animation.
     */
    public void setState(int state) {
        mState = state;
    }


    /**
     * To start playing the animation.
     */
    public abstract void play();


    /**
     * To pausing the animation.
     */
    public abstract void pause();


    /**
     * To resuming the animation.
     */
    public abstract void resume();


    /**
     * To stopping the animation.
     */
    public abstract void stop();


    
    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public void setDescriptor(Descriptor Descriptor) throws IllegalArgumentException {
        if (Descriptor == null)
            throw new IllegalArgumentException("The descriptor is null!");
        if (Descriptor.getSprites() == null || Descriptor.getSprites().size() == 0)
            throw new IllegalArgumentException("The descriptor without some sprites!");

        mDescriptor = Descriptor;
    }

    public List<StateListener> getStateListeners() {
        return mStateListeners;
    }

    public void addStateListener(StateListener listener) {
        mStateListeners.add(listener);
    }

    public void removeStateListener(StateListener listener) {
        mStateListeners.remove(listener);
    }

    public Descriptor getDescriptor() {
        return mDescriptor;
    }


    public Engine getEngine() {
        return mEngine;
    }

    public void setEngine(Engine engine) {
        mEngine = engine;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getFrameCount() {
        return mFrameCount;
    }

    public int nextFrameCount() {
        return ++mFrameCount;
    }

    public void setFrameCount(int frameCount) {
        mFrameCount = frameCount;
    }

    public long getFrameStartTime() {
        return mFrameStartTime;
    }

    public void setFrameStartTime(long frameStartTime) {
        mFrameStartTime = frameStartTime;
    }







    /**
     * The description of per frame in animation.
     * @since 2015/11/20
     */
    public static abstract class Descriptor {
        /** The index of layer that to drawing. */
        private int mLayerIndex;

        /** Sprites of this animation. */
        private List<Sprite> mSprites;

        /** The per frame draw duration.*/
        private int mDistance;

        /** The game engine isntance. */
        private Engine mEngine;

        /** The animation total duration. */
        private long mDuration;

        /** Animation is loop? */
        private boolean mIsLoop = false;

        /** How many frames contains in this animation? */
        private int mTotalFrames;


        /**
         * Descriptor construction mehtod.
         */
        public Descriptor(Engine engine) {
            mEngine = engine;
        }


        /**
         * Get next frame of animation.
         * @index The frame index.
         * @return Next frame of animation.
         */
        public abstract Bitmap getNextFrame(int index);

        public int getTotalFrames() {
            return mTotalFrames;
        }

        public void setTotalFrames(int totalFrames) {
            mTotalFrames = totalFrames;
        }

        public int getLayerIndex() {
            return mLayerIndex;
        }

        public void setLayerIndex(int layerIndex) {
            mLayerIndex = layerIndex;
        }

        public List<Sprite> getSprites() {
            return mSprites;
        }

        public void setSprites(List<Sprite> sprites) {
            mSprites = sprites;
        }

        public int getDistance() {
            return mDistance;
        }

        public void setDistance(int distance) {
            this.mDistance = distance;
        }

        public long getDuration() {
            return mDuration;
        }

        public void setDuration(long duration) {
            mDuration = duration;
        }

        public boolean isLoop() {
            return mIsLoop;
        }

        public void setIsLoop(boolean isLoop) {
            mIsLoop = isLoop;
        }

        public Engine getEngine() {
            return mEngine;
        }

        public void setEngine(Engine engine) {
            mEngine = engine;
        }
    }


    /**
     * The animation state listener interface.
     * @since 2015/11/20
     */
    public interface StateListener {
        /**
         * When before a animation playing, to trigger this method.
         * @param animation Current {@link Animation}.
         */
        void onBeforePlay(Animation animation);


        /**
         * When a animation start playing, to trigger this method.
         * @param animation Current {@link Animation}.
         */
        void onPlay(Animation animation);


        /**
         * When a animation play finished, to trigger this method.
         * @param animation Current {@link Animation}.
         */
        void onFinished(Animation animation);


        /**
         * When a animation play finished, to trigger this method.
         * @param animation Current {@link Animation}.
         */
        void onPause(Animation animation);


        /**
         * When a animation play finished, to trigger this method.
         * @param animation Current {@link Animation}.
         */
        void onResume(Animation animation);
    }
}