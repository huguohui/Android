package com.tankwar.entity;

import com.tankwar.engine.subsystem.Controllable;
import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.LivingEntity;
import com.tankwar.engine.subsystem.Sprite;

/**
 * Tank base class.
 * @since 2015/11/04
 */
public abstract class Tank extends LivingEntity implements Controllable {
    /** The model of tank. */
    private Enum<?> mModel;

    /** The color of tank. */
    private Enum<?> mColor;

    /** The sprite of tank. */
    private Sprite mSprite;

	/** The tank width. */
	public int mWidth = 28;

	/** The tank height. */
	public int mHeight = 28;


    /**
     * The constructor of entity.
     *
     * @param gameContext The game context.
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public Tank(GameContext gameContext, int x, int y) {
        super(gameContext, x, y);
    }


    /**
     * Empty constructor.
     * @param gameContext The game context.
     */
    public Tank(GameContext gameContext) {
		super(gameContext);
    }


    /**
     * Constructing a tank with model.
     * @param gameContext The game context.
     * @param model Tank model.
     */
    public Tank(GameContext gameContext, Enum<?> model) {
        super(gameContext);
        mModel = model;
    }


    /**
     * Constructing a tank with model and color.
     * @param gameContext The game context.
     * @param model Tank model.
     * @param color Tank color.
     */
    public Tank(GameContext gameContext, Enum<?> model, Enum<?> color) {
        super(gameContext);
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


	@Override
	public int getWidth() {
		return mWidth;
	}


	@Override
	public void setWidth(int width) {
		mWidth = width;
	}


	@Override
	public int getHeight() {
		return mHeight;
	}


	@Override
	public void setHeight(int height) {
		mHeight = height;
	}
}

