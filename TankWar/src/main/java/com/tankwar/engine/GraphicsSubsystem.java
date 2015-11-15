package com.tankwar.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.tankwar.entity.MediumTank;

import java.util.ArrayList;
import java.util.List;

/**
 * The engine of handling graphics.
 * @since 2015/11/06
 */

public class GraphicsSubsystem extends Subsystem {
    /** All the layers. */
    private List<Layer> mLayers = new ArrayList<>();

    /** The canvas view. */
    private CanvasView mCanvasView;
    int i;


	/**
	 * Only constructor.
	 */
	public GraphicsSubsystem(Engine engine) {
        super(engine);
        mCanvasView   = new CanvasView(engine.getGameContext());
        mLayers.add(new Layer());
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
//        for (Layer layer : mLayers) {
//            for (Drawable drawable : layer.getObjects()) {
//                drawable.draw(mCanvasBuffer);
//            }
//        }

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(100);


        Canvas canvas = mCanvasView.getCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawText("XXXXXXXXXXXXXXXXXXXXX", 0, i ++, p);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mCanvasView.updateFrame();
        }
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

        /** canvas constructor.
         * @param context Game context.
         */
        public CanvasView(Context context) {
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
        }


        /** 
         * Get canvas object.
         * @return canvas object.
         */
        public final Canvas getCanvas() {
            return mCanvas = mHolder.lockCanvas(new Rect(0, 0, 1920, 1080));
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
            freeCanvas();
        }



        @Override
        public final void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

        @Override
        public final void surfaceCreated(SurfaceHolder arg0) {}

        @Override
        public final void surfaceDestroyed(SurfaceHolder arg0) {}
    }
}