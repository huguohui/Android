package com.tankwar.entity.factory;

import com.tankwar.entity.Entity;
import com.tankwar.entity.LightTank;

/**
 * A factory to product tank.
 * @since 2015/11/13
 */
public class LightTankFactory implements Factory {
    /** Instance. **/
    private static LightTankFactory ourInstance = new LightTankFactory();

    /**
     * Get instance.
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
     * Create a entity.
     * @return A light tank.
     */
    @Override
    public LightTank create() {
        return new LightTank();
    }
}
