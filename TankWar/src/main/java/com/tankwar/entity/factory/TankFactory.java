package com.tankwar.entity.factory;

import com.tankwar.entity.Tank;

/**
 * The factory to product tank.
 * @since 2015/11/14
 */
public interface TankFactory extends Factory {
    /**
     * Creates a tank with model and color.
     * @param model Tank model.
     * @param color Tank color.
     */
    Tank create(Enum<?> model, Enum<?> color);
}
