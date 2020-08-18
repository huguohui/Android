package com.tankwar.entity.factory;

import com.tankwar.engine.GameContext;
import com.tankwar.entity.LightTank;

/**
 * A factory to product tank.
 *
 * @since 2015/11/13
 */
public class LightTankFactory implements TankFactory {
	/**
	 * Instance.
	 **/
	private static LightTankFactory ourInstance = new LightTankFactory();

	/**
	 * Get instance.
	 *
	 * @return Factory instance.
	 */
	public static LightTankFactory getInstance() {
		return ourInstance;
	}

	/**
	 * Default constructor.
	 */
	private LightTankFactory() {
	}

	/**
	 * Create a light tank.
	 *
	 * @return A light tank.
	 */
	@Override
	public LightTank create() {
		return new LightTank(GameContext.getGameContext());
	}

	/**
	 * Creates a tank with model and color.
	 *
	 * @param model Tank model.
	 * @param color Tank color.
	 */
	@Override
	public LightTank create(Enum<?> model, Enum<?> color) {
		return new LightTank(GameContext.getGameContext(), model, color);
	}
}
