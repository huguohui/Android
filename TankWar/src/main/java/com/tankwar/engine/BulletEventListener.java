package com.tankwar.engine;

import com.tankwar.entity.Bullet;
import com.tankwar.entity.Tank;

/**
 * To listening state change of bullet
 * by implementing this interface.
 *
 * @author  by Hui
 * @since 2015/10/30.
 */
public interface BulletEventListener {
    /**
     * The five states of bullet.
     */
    enum State {
        fired,
        flying,
        hitBuild,
        exploded,
        hitTank
    }


    /**
     * To listening bullet firing event by
     * implementing this interface.
     */
    interface OnFiringListener{
        /**
        * When bullet was fired by someone tank,
        * this method will be called.
        * @param bullet A fired bullet.
        */
        void onFiring(Bullet bullet);
    }


    /**
     * To listening bullet flying event
     * by implementing this interface.
     */
    interface OnFlyingListener {
        /**
         * When bullet was flying,
         * this method will be called.
         * @param bullet A flying bullet.
         */
        void onFlying(Bullet bullet);
    }


    /**
     * To listening bullet explosion event
     * by implementing this interface.
     */
    interface OnExplosionListener {
        /**
         * When bullet was exploded,
         * this method will be called.
         * @param bullet A exploded bullet.
         */
        void onExploding(Bullet bullet);
    }


    /**
     * To listening bullet hit building event
     * by implementing this interface.
     */
    interface OnHitBuildingListener {
        /**
         * When bullet was hitting building,
         * this method will be called.
         *
         * @param bullet A bullet that hitting a building.
         */
        void onHitBuilding(Bullet bullet);
    }


    /**
     * To listening bullet hit tank event
     * by implementing this interface.
     */
    interface OnHitTankListener {
        /**
         * When bullet was hitting tank,
         * this method will be called.
         *
         * @param bullet The bullet.
         * @param tank   The tank was hitting.
         */
        void onHitTank(Bullet bullet, Tank tank);
    }
}
