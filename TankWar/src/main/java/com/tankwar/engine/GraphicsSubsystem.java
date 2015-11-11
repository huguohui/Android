package com.tankwar.engine;

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
        engine.getGameContext().getClient().setContentView(mCanvasView);
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
     * Game loop tick.
     */
    public void tick() {
        for (Layer layer : mlayers) {
            layer.show(mCanvas);
        }
    }


    /**
     * CanvasView object.
     */
    final public class CanvasView extends SurfaceView
        implements SurfaceHolder.Callback {
        /** Canvas object.*/
        private Canvas mCanvas = null;

        /** Surface holder. */
        private SurfaceHolder mHolder = null;

        /** Bitmap buffer. */
        private Bitmap mBitmapBuffer = Bitmap.createBitmap(WorldSubsystem.WORLD_WIDTH,
                    WorldSubsystem.WORLD_HEIGHT);

        /** Canvas buffer. */
        private Canvas mCanvasBuffer = null;

        /** canvas constructor. */
        public CanvasView(Context context) {
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
            return mCanvas = lockCanvas(null);
        }


        /**
         * Free canvas lock and resouces.
         */
        public final void freeCanvas() {
            if (mCanvas != null) {
                mHolder.unlockAndPostCanvas(mCanvas);
            }
        }


        /**
         * Update frame.
         */
        public final void updateFrame() {
            if (mBitmapBuffer != null) {
                getCanvas().drawBitmap(mBitmapBuffer, 0, 0);
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