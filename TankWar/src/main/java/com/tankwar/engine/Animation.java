package com.tankwar.engine;

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
    private List<FrameDescriptor> mFrameDescriptors = new ArrayList<>();

    /** The context of game. */
    private GameContext mGameContext;

    /** The current animation playing state. */
    private int mState = AS_NONE;


    /**
     * To create a animation object by two parameters.
     *
     * @param context The gameContext.
     * @param frames The frames.
     */
    public Animation(GameContext context, List<FrameDescriptor> frames) {
        this(context);
        if (mFrameDescriptors != null) {
            this.mFrameDescriptors = frames;
        }
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
     * Add a frame descriptor.
     * @param frame The {@link com.tankwar.engine.Animation.FrameDescriptor}.
     */
    public void addFrame(FrameDescriptor frame) {
        this.mFrameDescriptors.add(frame);
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



    /**
     * Get all frame descriptors.
     * @return All {@link FrameDescriptor}.
     */
    public List<FrameDescriptor> getFrameDescriptors() {
        return mFrameDescriptors;
    }


    public GameContext getGameContext() {
        return mGameContext;
    }



    /**
     * The description of per frame in animation.
     * @since 2015/11/20
     */
    public static class FrameDescriptor {
        /** The index of layer that to drawing. */
        private int mLayerIndex;

        /** Sprites of this animation. */
        private Sprite mSprites;

        /** The per frame draw duration. */
        private int duration;


        public int getLayerIndex() {
            return mLayerIndex;
        }

        public void setLayerIndex(int layerIndex) {
            mLayerIndex = layerIndex;
        }

        public Sprite getSprites() {
            return mSprites;
        }

        public void setSprites(Sprite sprites) {
            mSprites = sprites;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
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