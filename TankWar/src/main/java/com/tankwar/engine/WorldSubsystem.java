package com.tankwar.engine;

import com.tankwar.entity.Entity;
import com.tankwar.entity.Terrain;

import java.util.ArrayList;
import java.util.List;

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

	/** World horizontal gird count. */
	public static int WORLD_HOR_GRID_NUM = 13;

	/** World vertical gird count. */
	public static int WORLD_VER_GRID_NUM = 13;

	/** World height.*/
	public static int WORLD_HEIGHT = WORLD_VER_GRID_NUM * WORLD_GRID_HEIGHT;

	/** World width. */
	public static int WORLD_WIDTH = WORLD_HOR_GRID_NUM * WORLD_GRID_WIDTH;

	/** World system terrian data. */
	private Terrain[][] mWorldData = new Terrain[WORLD_HOR_GRID_NUM][WORLD_VER_GRID_NUM];


	/**
	 * Construct a module object by gameContext.
	 * @param engine The game engine.
	 */
	public WorldSubsystem(Engine engine) {
		super(engine);
		initializeSystem();
	}


	/**
	 * Initialize world system.
	 */
	private void initializeSystem() {
        getEngine().getGameContext().getResourcesManager().loadAll();
		for (int i = 0; i < WORLD_VER_GRID_NUM; i++) {
			for (int j = 0; j < WORLD_HOR_GRID_NUM; j++) {
				mWorldData[i][j] = null;
			}
		}
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
		
	}


	/**
	 */
}
