package com.tankwar.entity;

import com.tankwar.engine.Sprite;

/**
 * Tank base class.
 * @since 2015/11/04
 */
public abstract class Tank extends LivingEntity {
    /** The model of tank. */
    private Enum<?> mModel;

    /** The color of tank. */
    private Enum<?> mColor;

    /** The sprite of tank. */
    private Sprite mSprite;


    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Tank(int x, int y) {
        super(x, y);
    }


    /**
     * Empty constructor.
     */
    public Tank() {}


    /**
     * Constructing a tank with model.
     * @param model Tank model.
     */
    public Tank(Enum<?> model) {
        mModel = model;
    }


    /**
     * Constructing a tank with model and color.
     * @param model Tank model.
     * @param color Tank color.
     */
    public Tank(Enum<?> model, Enum<?> color) {
        mModel = model;
        mColor = color;
    }


    /**
     * Get model of tank.
     * @return Model of tank.
     */
    public Enum<?> getModel() {
        return mModel;
    }


    /**
     * Set model of tank.
     * @param model Tank model.
     */
    public void setModel(Enum<?> model) {
        mModel = model;
    }


    /**
     * Get model of tank.
     * @return Model of tank.
     */
    public Enum<?> getColor() {
        return mColor;
    }


    /**
     * Set model of tank.
     * @param color Tank model.
     */
    public void setColor(Enum<?> color) {
        mColor = color;
    }


    /**
     * Set tank sprite.
     * @param sprite Tank sprite.
     */
    public void setSprite(Sprite sprite) {
        mSprite = sprite;
    }


    /**
     * Get tank sprite.
     * @return Tank sprite.
     */
    public Sprite getSprite() {
        return mSprite;
    }
}

