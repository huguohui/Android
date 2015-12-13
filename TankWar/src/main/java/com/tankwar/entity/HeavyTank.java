package com.tankwar.entity;

import android.graphics.Canvas;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.Entity;

/**
 * A light tank.
 */
final public class HeavyTank extends Tank {
    /** The models of tank. */
    public enum Model {
        P1, P2, E1
    }

    /** The colors of tank. */
    public enum Color {
        RED, WHITE, YELLOW, GREEN
    }


    /**
     * The constructor of entity.
     *
     * @param gameContext The game context.
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public HeavyTank(GameContext gameContext, int x, int y) {
        super(gameContext, x, y);
    }


    /**
     * Empty constructor.
     * @param gameContext The game context.
     */
    public HeavyTank(GameContext gameContext) {
        super(gameContext);
    }


    /**
     * Constructing a tank with model and color.
     * @param gameContext The game context.
     * @param model Tank model.
     * @param color Tank color.
     */
    public HeavyTank(GameContext gameContext, Enum<?> model, Enum<?> color) {
        super(gameContext, model, color);
    }



    /**
     * Checks this object if collided some entity.
     *
     * @param entity@return If collided true else false.
     */
    @Override
    public boolean isCollision(Entity entity) {
        return false;
    }

    /**
     * When this object colliding another object,
     * this method will be called.
     *
     * @param object The entity.
     */
    @Override
    public void onCollision(Entity object) {

    }


    /**
     * When screen was updated,
     * this method will be called.
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

    }

    /**
     * The layer index of draw.
     */
    @Override
    public int getIndex() {
        return 0;
    }

    /**
     * This method implemets move behavior.
     */
    @Override
    public void move() {

    }


	/**
	 * Move a distance by special direction.
	 *
	 * @param direction
	 */
	@Override
	public void move(Direction direction) {

	}


	/**
	 * Stopping!
	 */
	@Override
	public void stop() {

	}


	/**
	 * Attacks on current direction.
	 */
	@Override
	public void attack() {

	}


	/**
	 * Attacks to some target.
	 *
	 * @param entity
	 */
	@Override
	public void attack(Entity entity) {

	}
}
