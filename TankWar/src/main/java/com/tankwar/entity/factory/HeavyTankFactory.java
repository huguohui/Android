package com.tankwar.entity.factory;

import com.tankwar.engine.GameContext;
import com.tankwar.entity.HeavyTank;
import com.tankwar.entity.LightTank;
import com.tankwar.entity.Tank;

/**
 * A factory to product tank.
 * @since 2015/11/13
 */
public class HeavyTankFactory implements TankFactory {
    /** Instance. **/
    private static HeavyTankFactory ourInstance = new HeavyTankFactory();

    /**
     * Get instance.
     * @return Factory instance.
     */
    public static HeavyTankFactory getInstance() {
        return ourInstance;
    }

    /**
     * Default constructor.
     */
    private HeavyTankFactory() {
    }


    /**
     * Create a light tank.
     * @return A light tank.
     */
    @Override
    public HeavyTank create() {
        return new HeavyTank(GameContext.getGameContext());
    }


    /**
     * Creates a tank with model.
     *
     * @param model Tank model.
     */
    @Override
    public HeavyTank create(Enum<?> model) {
        return new HeavyTank(GameContext.getGameContext(), model);
    }

    /**
     * Creates a tank with model and color.
     *
     * @param model Tank model.
     * @param color Tank color.
     */
    @Override
    public HeavyTank create(Enum<?> model, Enum<?> color) {
        return new HeavyTank(GameContext.getGameContext(), model, color);
    }
}
