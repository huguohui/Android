package com.tankwar.entity.factory;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.subsystem.Sprite;
import com.tankwar.entity.MediumTank;

/**
 * A factory to product tank.
 *
 * @since 2015/11/13
 */
public class MediumTankFactory implements TankFactory {
	/**
	 * Instance.
	 **/
	private static MediumTankFactory ourInstance = new MediumTankFactory();

	/**
	 * Get instance.
	 *
	 * @return Factory instance.
	 */
	public static MediumTankFactory getInstance() {
		return ourInstance;
	}

	/**
	 * Default constructor.
	 */
	private MediumTankFactory() {
	}

	/**
	 * Create a light tank.
	 *
	 * @return A light tank.
	 */
	@Override
	public MediumTank create() {
		return new MediumTank(GameContext.getGameContext());
	}

	/**
	 * Creates a tank with model and color.
	 *
	 * @param model Tank model.
	 * @param color Tank color.
	 */
	@Override
	public MediumTank create(Enum<?> model, Enum<?> color) {
		GameContext c = GameContext.getGameContext();
		MediumTank mt = new MediumTank(GameContext.getGameContext(), model, color);
		//Sprite sprite = new Sprite();
		return mt;
	}
}
