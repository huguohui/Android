package com.tankwar.entity;


/**
 * Describe a living entity.
 * @since 2015/11/09
 */
public abstract class LivingEntity extends MovableEntity {
	/**
	 * The current hit-point of entity.
	 */
	private int mHitPoint = 100;


	/**
	 * THe full hit-point of entity.
	 */
	private int mFullHitPoint = 100;

    /**
     * The constructor of entity.
     *
     * @param x The default x coordinate.
     * @param y The default y coordinate.
     */
    public LivingEntity(int x, int y) {
        super(x, y);
    }


    /**
     * Default constructor.
     */
    public LivingEntity() {
        super();
    }


    /**
	 * Get hit-point.
	 * @return HP.
	 */
	public int getHitPoint() {
		return mHitPoint;
	}


	/**
	 * Set hit-point.
	 * @param hp hit-point.
	 */
	public void setHitPoint(int hp) {
		mHitPoint = hp;
	}


	/**
	 * Get full hp.
	 * @return full hp.
	 */
	public int getFullHitPoint() {
		return mFullHitPoint;
	}


    /**
     * Set full hp.
     */
    public void setFullHitPoint(int hp) {
        this.mFullHitPoint = hp;
    }
}