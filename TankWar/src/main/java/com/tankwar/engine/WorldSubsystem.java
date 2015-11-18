package com.tankwar.engine;

import com.tankwar.entity.Terrain;

/**
 * The game world subsystem implements.
 * @since 2015/11/08
 */
public class WorldSubsystem extends Subsystem {
	/** World top margin.*/
	public final static int WORLD_TOP = 20;

	/** World left margin. */
	public final static int WORLD_LEFT = 20;

	/** World right margin. */
	public final static int WORLD_RIGHT = 20;

	/** World bottom margin.*/
	public final static int WORLD_BOTTOM = 20;

	/** World base grid width. */
	public final static int WORLD_GRID_WIDTH = 32;

	/** World base grid height. */
	public final static int WORLD_GRID_HEIGHT = 32;

    /** The default width of device. */
    public final static int DEFAULT_DEVICE_HEIGHT = 320;

    /** The default width of device. */
    public final static int DEFAULT_DEVICE_WIDTH = 480;

	/** World horizontal gird count. */
	public static int WORLD_HOR_GRID_NUM = 13;

	/** World vertical gird count. */
	public static int WORLD_VER_GRID_NUM = 13;

    /** The current device height. */
    public final static int DEVICE_HEIGHT = GameContext.getGameContext().getScreenHeight();

    /** The current device width. */
    public final static int DEVICE_WIDTH = GameContext.getGameContext().getScreenWidth();

	/** World height.*/
	public static int WORLD_HEIGHT = WORLD_VER_GRID_NUM * WORLD_GRID_HEIGHT;

	/** World width. */
	public static int WORLD_WIDTH = WORLD_HOR_GRID_NUM * WORLD_GRID_WIDTH;

	/** World system terrain data. */
	private Terrain[][] mWorld = new Terrain[WORLD_HOR_GRID_NUM][WORLD_VER_GRID_NUM];

    /** The graphics subsystem instance. */
    private GraphicsSubsystem mGraphicsSubsystem;


	/**
	 * Construct a module object by gameContext.
	 * @param engine The game engine.
	 */
	public WorldSubsystem(Engine engine) {
		super(engine);
		initializeSystem();
        mGraphicsSubsystem = (GraphicsSubsystem)getEngine().getSubsystem(GraphicsSubsystem.class);
	}


	/**
	 * Initialize world system.
	 */
	private void initializeSystem() {
		for (int i = 0; i < WORLD_VER_GRID_NUM; i++) {
			for (int j = 0; j < WORLD_HOR_GRID_NUM; j++) {
				mWorld[i][j] = null;
			}
		}
	}


    /**
     * Set the world map data.
     * @param terrain The terrain data.
     */
    public void setWorld(Terrain[][] terrain) {
        mWorld = terrain;
    }


	/**
	 * Enable a module.
	 */
	@Override
	public void enable() {

	}

	/**
	 * Disable a module.
	 */
	@Override
	public void disable() {

	}


	/**
	 * Game loop tick.
	 */
	public void tick() {
        mGraphicsSubsystem.getCanvasView().getCanvas();
	}


	/**
	 */
}
