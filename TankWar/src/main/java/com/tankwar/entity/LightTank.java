package com.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.Entity;
import com.tankwar.engine.subsystem.Sprite;

/**
 * A light tank.
 */
final public class LightTank extends Tank {
	/**
	 * The model of tank.
	 */
	public enum Model {
		P1, P2, E1
	}

	/**
	 * The colors of tank.
	 */
	public enum Color {
		RED, WHITE, YELLOW, GREEN
	}

	/**
	 * The speed of tank.
	 */
	private int mSpeed = 1;

	@Override
	public int getSpeed() {
		return mSpeed;
	}

	@Override
	public void setSpeed(int speed) {
		mSpeed = speed;
	}

	/**
	 * The constructor of entity.
	 *
	 * @param gameContext The game context.
	 * @param x           The default x coordinate.
	 * @param y           The default y coordinate.
	 */
	public LightTank(GameContext gameContext, int x, int y) {
		super(gameContext, x, y);
	}

	/**
	 * Empty constructor.
	 *
	 * @param gameContext The game context.
	 */
	public LightTank(GameContext gameContext) {
		super(gameContext);
	}

	/**
	 * Constructing a tank with model and color.
	 *
	 * @param gameContext The game context.
	 * @param model       Tank model.
	 * @param color       Tank color.
	 */
	public LightTank(GameContext gameContext, Enum<?> model, Enum<?> color) {
		super(gameContext, model, color);

	}

	/**
	 * Initialize sprite of tank.
	 */
	private void initSprite() {
		int x = 0, y = 0;
		switch ((Model) getModel()) {
			case E1:
				break;

		}
	}

	/**
	 * Checks this object if collided some entity.
	 *
	 * @param entity@return If collided true else false.
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
	 * @param object The entity.
	 */
	@Override
	public void onCollision(Entity object) {
		this.stop();
	}

	/**
	 * When screen was updated,
	 * this method will be called.
	 *
	 * @param canvas
	 */
	@Override
	public void draw(Canvas canvas) {
		if (getSprite() == null)
			//setSprite(new Sprite(getGameContext().getEngine().
			//		getWorldSubsystem().getBitmap("enemy.png"), 0, 0, getWidth(), getHeight()));

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
	 * This method implemets move behavior.
	 */
	@Override
	public synchronized void move() {
		switch (getDirection()) {
			case UP:
				setY(getY() - getSpeed());
				break;
			case DOWN:
				setY(getY() + getSpeed());
				break;
			case LEFT:
				setX(getX() - getSpeed());
				break;
			case RIGHT:
				setX(getX() + getSpeed());
		}
	}

	/**
	 * Move a distance by special direction.
	 *
	 * @param direction
	 */
	@Override
	public void move(Direction direction) {
		setDirection(direction);
		move();
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
