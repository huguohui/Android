package com.tankwar.engine.animation;

import android.graphics.Canvas;

import com.tankwar.engine.Engine;

/**
 * A frame per animation is simply animation that draw one times per frame.
 * @since 2015/11/27
 */
public class FrameAnimation extends Animation {
    /**
     * To create a animation object by special descriptor and coordinate.
     *
     * @param engine     The engine.
     * @param descriptor The frames.
     * @param x          The x coordinate to draw.
     * @param y          The y coordinate to draw.
     */
    public FrameAnimation(Engine engine, Descriptor descriptor, int x, int y) {
        super(engine, descriptor, x, y);
    }

    /**
     * To create a animation object by two parameters.
     *
     * @param engine     The engine.
     * @param descriptor The frames.
     */
    public FrameAnimation(Engine engine, Descriptor descriptor) {
        super(engine, descriptor);
    }

    /**
     * The creating a animation object by a engine.
     *
     * @param engine The game engine.
     * @param x
     * @param y
     */
    public FrameAnimation(Engine engine, int x, int y) {
        super(engine, x, y);
    }

    /**
     * The creating a animation object by a engine.
     *
     * @param engine The game engine.
     */
    public FrameAnimation(Engine engine) {
        super(engine);
    }

    /**
     * To start playing the animation.
     */
    @Override
    public void play() {

    }

    /**
     * To pausing the animation.
     */
    @Override
    public void pause() {

    }

    /**
     * To resuming the animation.
     */
    @Override
    public void resume() {

    }

    /**
     * To stopping the animation.
     */
    @Override
    public void stop() {

    }

    /**
     * To draw itself.
     *
     * @param canvas The canvas.
     */
    @Override
    public void draw(Canvas canvas) {

    }

    /**
     * The layer index of draw.
     *
     * @return A number that >= 0.
     */
    @Override
    public int getIndex() {
        return 0;
    }
}
