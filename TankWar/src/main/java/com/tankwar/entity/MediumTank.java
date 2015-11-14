package com.tankwar.entity;

import android.graphics.Canvas;

/**
 * A light tank.
 */
final public class MediumTank extends Tank {
    /** The model of tank. */
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
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public MediumTank(int x, int y) {
        super(x, y);
    }


    /**
     * Empty constructor.
     */
    public MediumTank() {
        super();
    }


    /**
     * Constructing a tank with model.
     * @param model Tank model.
     */
    public MediumTank(Enum<?> model) {
        super(model);
    }


    /**
     * Constructing a tank with model and color.
     * @param model Tank model.
     * @param color Tank color.
     */
    public MediumTank(Enum<?> model, Enum<?> color) {
        super(model, color);
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
     * This method implemets move behavior.
     */
    @Override
    public void move() {

    }
}
