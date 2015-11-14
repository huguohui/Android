package com.tankwar.entity.factory;

import com.tankwar.entity.Entity;
import com.tankwar.entity.LightTank;
import com.tankwar.entity.MediumTank;

/**
 * Tank factory interface.
 * @since 2015/11/13
 */
public interface Factory {
    /**
     * Create a entity.
     */
    Entity create();
}
