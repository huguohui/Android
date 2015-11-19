package com.tankwar.entity.factory;

import com.tankwar.entity.absentity.Entity;

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
