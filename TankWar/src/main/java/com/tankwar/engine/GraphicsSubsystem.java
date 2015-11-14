package com.tankwar.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

/**
 * The engine of handling graphics.
 * @since 2015/11/06
 */

public class GraphicsSubsystem extends Subsystem {
    /** All the layers. */
    private List<Layer> mLayers;

    /** The buffer canvas. */
    private Canvas mCanvasBuffer;


    /** The canvas view. */
    private CanvasView mCanvasView;


	/**
	 * Only constructor.
	 */
	public GraphicsSubsystem(Engine engine) {
        super(engine);
        mCanvasView   = new CanvasView(engine.getGameContext());
        mCanvasBuffer = mCanvasView.getBuffer();
        mLayers.add(new Layer());
	}


	/**
	 * Do some work.
	 */
	public void start() {
        getEngine().getGameContext().getClient().setContentView(mCanvasView);
	}

    /**
     * Enable a module.
     */
    @Override
    public void enable() {
        super.enable();
    }


    /**
     * Disable a module.
     */
    @Override
    public void disable() {
        super.disable();
    }


    /**
     * Get all layers.
     * @return All layers.
     */
    public List<Layer> getLayers() {
        return mLayers;
    }


    /**
     * Get a layer.
     * @return A {@link Layer}.
     */
    public Layer getLayer(int index) {
        return mLayers.get(index);
    }


    /**
     * Add a layer.
     * @param layer A {@link Layer}.
     */
    public void addLayer(Layer layer) {
        mLayers.add(layer);
    }


    /**
     * Get draw buffer.
     * @return A buffer.
     */
    public Canvas getCanvasBuffer() {
        return mCanvasBuffer;
    }


    /**
     * Set draw buffer.
     * @param canvasBuffer Buffer.
     */
    public void setCanvasBuffer(Canvas canvasBuffer) {
        mCanvasBuffer = canvasBuffer;
    }


    /**
     * Get the view of draw.
     * @return The view of draw.
     */
    public CanvasView getCanvasView() {
        return mCanvasView;
    }


    /**
     * Set view of draw
     * @param canvasView View of draw.
     */
    public void setCanvasView(CanvasView canvasView) {
        mCanvasView = canvasView;
    }

    /**
     * Game loop tick.
     */
    public void tick() {
        for (Layer layer : mLayers) {
            for (Drawable drawable : layer.getObjects()) {
                drawable.draw(mCanvasBuffer);
            }
        }
        mCanvasView.updateFrame();
    }


    /**
     * CanvasView object.
     * @since 2015/11/14
     */
    final private class CanvasView extends SurfaceView
        implements SurfaceHolder.Callback {
        /** Canvas object.*/
        private Canvas mCanvas = null;

        /** Surface holder. */
        private SurfaceHolder mHolder = null;

        /** Bitmap buffer. */
        private Bitmap mBitmapBuffer = Bitmap.createBitmap(WorldSubsystem.WORLD_WIDTH,
                    WorldSubsystem.WORLD_HEIGHT, Bitmap.Config.RGB_565);

        /** Canvas buffer. */
        private Canvas mCanvasBuffer = null;

        /** canvas constructor.
         * @param context Game context.
         */
        public CanvasView(Context context) {
            super(context);
            mHolder = getHolder();
        }


        /**
         * Get canvas buffer.
         * @return canvas buffer.
         */
        public final Canvas getBuffer() {
            if (mCanvasBuffer == null) {
                mCanvasBuffer = new Canvas(mBitmapBuffer);
            }
            return mCanvasBuffer;
        }

        /** 
         * Get canvas object.
         * @return canvas object.
         */
        public final Canvas getCanvas() {
            return mCanvas = mHolder.lockCanvas(null);
        }


        /**
         * Free canvas lock and resouces.
         */
        public final void freeCanvas() {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }


        /**
         * Update frame.
         */
        public final void updateFrame() {
            if (mBitmapBuffer != null) {
                getCanvas().drawBitmap(mBitmapBuffer, 0f, 0f, new Paint());
                freeCanvas();
            }
        }



        @Override
        public final void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

        @Override
        public final void surfaceCreated(SurfaceHolder arg0) {}

        @Override
        public final void surfaceDestroyed(SurfaceHolder arg0) {}
    }
}