package com.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.Entity;
import com.tankwar.engine.entity.Obstacle;
import com.tankwar.engine.subsystem.Sprite;
import com.tankwar.util.GameRes;

/**
 * A kind of obstacle, it can destroy.
 */
public class Wall extends Obstacle {
	/**
	 * The wall width.
	 */
	private int mWidth = 32;

	/**
	 * The wall height.
	 */
	private int mHeight = 32;

	/**
	 * The constructor of entity.
	 *
	 * @param gameContext The game context.
	 * @param x           The default x coordinate.
	 * @param y           The default y coordinate.
	 */
	public Wall(GameContext gameContext, int x, int y) {
		super(gameContext, x, y);
		setSprite(new Sprite(GameRes.getRedflag(), 0, 0, getWidth(), getHeight()));
	}

	/**
	 * Destroy a region of destroyable.
	 *
	 * @param r
	 */
	@Override
	public void destroy(Rect r) {

	}

	/**
	 * Method destroy to implements destroy a object.
	 */
	@Override
	public void destroy() {

	}

	/**
	 * When screen was updated,
	 * this method will be called.
	 *
	 * @param canvas
	 */
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(getSprite().getBitmap(0), getX(), getY(), new Paint());
	}

	/**
	 * The layer index of draw.
	 */
	@Override
	public int getIndex() {
		return 0;
	}

	/**
	 * Checks this object if collided some entity.
	 *
	 * @param entity THe entity.
	 * @return If collided true else false.
	 */
	@Override
	public boolean isCollision(Entity entity) {
		if (Math.abs(entity.getX() - getX()) > getWidth())
			return false;
		else if (Math.abs(entity.getY() - getY()) > getHeight())
			return false;
		else
			return true;
	}

	/**
	 * When this object colliding another object,
	 * this method will be called.
	 *
	 * @param object
	 */
	@Override
	public void onCollision(Entity object) {

	}

	@Override
	public int getHeight() {
		return mHeight;
	}

	@Override
	public void setHeight(int height) {
		mHeight = height;
	}

	@Override
	public int getWidth() {
		return mWidth;
	}

	@Override
	public void setWidth(int width) {
		mWidth = width;
	}
}
