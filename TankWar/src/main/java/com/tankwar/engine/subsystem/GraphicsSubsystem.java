package com.tankwar.engine.subsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tankwar.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * The engine of handling graphics.
 * @since 2015/11/06
 */

public class GraphicsSubsystem extends Subsystem implements Engine.StateListener {
    /** The max layers number. */
    public final int MAX_LAYERS = 5;

    /** All the layers. */
    private List<Layer> mLayers = new ArrayList<>();

    /** The canvas view. */
    private CanvasView mCanvasView;

    /** The width scale value of the default device to current device. */
    private float mWidthScale;

    /** The height scale value of the default device to current device. */
    private float mHeightScale;

    int i = 0;


	/**
	 * Only constructor.
	 */
	public GraphicsSubsystem(Engine engine) {
        super(engine);
        engine.addStateListener(this);
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
     * Get the width scale.
     * @return The width scale.
     */
    public float getWidthScale() {
        return mWidthScale;
    }


    /**
     * Get the height scale.
     * @return The height scale.
     */
    public float getHeightScale() {
        return mHeightScale;
    }


    /**
     * Get all layers.
     * @return All layers.
     */
    public List<Layer> getLayers() {
        return mLayers;
    }


    /**
     * Add a drawable to special layer.
     * @param drawable A {@link Drawable}.
     * @param index The layer index.
     */
    public void addDrawable(Drawable drawable, int index) {
        mLayers.get(index).addObject(drawable);
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
     * Game loop tick.
     */
    public void tick() {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(100);

        Canvas canvas = mCanvasView.getCanvas();
        if (canvas == null) return;

        canvas.drawColor(Color.BLACK);
        canvas.drawText("------------------>", 0, i++, p);
        canvas.drawText("Game time: " + ((TimingSubsystem)getEngine().getSubsystem(TimingSubsystem.class))
                .getSystemTime(), 0, i+100, p);
        canvas.drawText("Frame time: " + ((TimingSubsystem)getEngine().getSubsystem(TimingSubsystem.class))
                .getFrameTime(), 0, i+200, p);
        canvas.drawText("System time: " + System.currentTimeMillis(), 0, i + 300, p);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Layer layer : mLayers) {
            for (Drawable drawable : layer.getObjects()) {
                drawable.draw(canvas);
            }
        }

        mCanvasView.updateFrame();
    }


    /**
     * When engine initialized.
     *
     * @param engine engine engine.
     */
    @Override
    public void onInitialize(Engine engine) {
        mCanvasView = new CanvasView(getEngine().getGameContext());
        for (int i = 0; i < MAX_LAYERS; i++) {
            this.addLayer(new Layer());
        }
    }

    /**
     * When engine start work.
     *
     * @param engine engine engine.
     */
    @Override
    public void onStart(Engine engine) {
        mCanvasView.setOnTouchListener((ControlSubsystem) getEngine()
                .getSubsystem(ControlSubsystem.class));
        getEngine().getGameContext().getClient().setContentView(mCanvasView);
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


    /**
     * CanvasView object.
     * @since 2015/11/14
     */
    final public class CanvasView extends SurfaceView
        implements SurfaceHolder.Callback {
        /** Canvas object.*/
        private Canvas mCanvas = null;

        /** Surface holder. */
        private SurfaceHolder mHolder = null;

        /** The surface if can be used. */
        private boolean mIsCreated = false;

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
            if (!mIsCreated) return null;

            mCanvas = mHolder.lockCanvas();
            return mCanvas;
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
        public final void surfaceCreated(SurfaceHolder arg0) {
            mIsCreated = true;
        }

        @Override
        public final void surfaceDestroyed(SurfaceHolder arg0) {}
    }
}