package com.tankwar.entity;


/**
 * Movable enity.
 * @since 2015/11/09
 */
public abstract class MovableEntity extends DrawableEntity implements Movable {
    /**
     * The moving direction of entity.
     */
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }


    /** The speed of entity.  */
    private int mSpeed;

    /** The current direction of entity. */
    private Direction mDirection;

    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public MovableEntity(int x, int y) {
        super(x, y);
    }


    /**
     * Default constructor.
     */
    public MovableEntity() {super();}


    /**
     * Get speed of entity.
     * @return Speed of entity.
     */
    public int getSpeed() {
        return mSpeed;
    }


    /**
     * Set entity's speed.
     * @param speed Speed of entity.
     */
    public void setSpeed(int speed) {
        this.mSpeed = speed;
    }


    /**
     * Get current direction of entity.
     * @return Current direction of entity.
     */
    public Direction getDirection() {
        return mDirection;
    }


    /**
     * Set current direction of entity.
     * @param direction Current direction of entity.
     */
    public void setDirection(Direction direction) {
        mDirection = direction;
    }
}