package com.tankwar.engine.animation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tankwar.engine.Engine;
import com.tankwar.engine.subsystem.TimingSubsystem;

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
        getEngine().getGraphicsSubsystem().addDrawable(this);
        setStartTime(getEngine().getTimingSubsystem().getSystemTime());
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
        getEngine().getGraphicsSubsystem().removeDrawable(this);
    }

    /**
     * To draw itself.
     *
     * @param canvas The canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        TimingSubsystem ts = getEngine().getTimingSubsystem();
        long systemTime = ts.getSystemTime();
        Descriptor descriptor = getDescriptor();

        if (systemTime - getStartTime() >= descriptor.getDuration()) {
			if (descriptor.isLoop()) {
				setFrameCount(1);
				setFrameStartTime(0);
				setStartTime(systemTime);
			}
			else {
				this.stop();
				return;
			}
        }
        else {
            if (getFrameStartTime() == 0) {
				setFrameStartTime(systemTime);
			}

            if (systemTime - getFrameStartTime() >= getDescriptor().getDistance()
					&& getFrameCount() < getDescriptor().getTotalFrames()) {
				setFrameStartTime(systemTime);
                nextFrameCount();
            }
        }

        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        canvas.drawText("Frame count: " + getFrameCount(), 300, 1000, paint);
        canvas.drawBitmap(getDescriptor().getNextFrame(getFrameCount() - 1),
                getX(), getY(), new Paint());
    }

    /**
     * The layer index of draw.
     *
     * @return A number that >= 0.
     */
    @Override
    public int getIndex() {
        return getDescriptor().getLayerIndex();
    }
}
