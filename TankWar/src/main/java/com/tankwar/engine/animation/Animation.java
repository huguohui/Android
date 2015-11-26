package com.tankwar.engine.animation;

import android.graphics.Canvas;

import com.tankwar.engine.GameContext;
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

    /** The context of game. */
    private GameContext mGameContext;

    /** The current animation playing state. */
    private int mState = AS_NONE;

    /** The animation total duration. */
    private long mDuration;

    /** The animation start time. */
    private long mStartTime;

    /** The animation x coordinate to draw. */
    private int mX;

    /** The animation y coordinate to draw. */
    private int mY;


    /**
     * To create a animation object by two parameters.
     *
     * @param context The gameContext.
     * @param descriptor The frames.
     */
    public Animation(GameContext context, Descriptor descriptor) {
        this(context);
        if (mDescriptor != null) {
            this.mDescriptor = descriptor;
        }
    }

    /**
     * The creating a animation object by a context.
     * @param context The game context.
     */
    public Animation(GameContext context, int x, int y) {
        if (context != null) {
            this.mGameContext = context;
        }
        this.mX = x;
        this.mY = y;
    }


    /**
     * The creating a animation object by a context.
     * @param context The game context.
     */
    public Animation(GameContext context) {
        if (context != null) {
            this.mGameContext = context;
        }
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
    public void play() {

    }


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

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void setDescriptor(Descriptor Descriptor) {
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


    public GameContext getGameContext() {
        return mGameContext;
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


        /**
         * Get next frame of animation.
         * @return Next frame of animation.
         */
        public abstract void drawNext(Canvas canvas);


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