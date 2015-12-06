package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.tankwar.engine.entity.MovableEntity;
import com.tankwar.engine.subsystem.Updatable;
import com.tankwar.game.GameMap;
import com.tankwar.engine.GameContext;
import com.tankwar.utils.GameSound;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class of bullet, denoting a bullet.
 *
 * @author hgh
 * @version 1.0
 * @since 2015/10/29
 */
public abstract class Bullet extends MovableEntity implements Updatable {
    private boolean isExplosion;
    private boolean canDestroyIron = false;

    private Tank ownerTank;
    private Bitmap[] bulletBitmaps;
    private BulletEventListener.State state = null;
    private List<BulletEventListener.OnExplosionListener> explosionListeners = new ArrayList<>();
    private List<BulletEventListener.OnFiringListener> firingListeners = new ArrayList<>();
    private List<BulletEventListener.OnFlyingListener> flyingListeners = new ArrayList<>();
    private List<BulletEventListener.OnHitBuildingListener> hitBuildingListeners = new ArrayList<>();
    private List<BulletEventListener.OnHitTankListener> hitTankListeners = new ArrayList<>();

    public final int SPEED = 2;
    public final int WIDTH = 8;
    public final int HEIGHT = 8;
    public final int X_MIN = GameMap.MAP_LEFT;
    public final int Y_MIN = GameMap.MAP_TOP;
    public final int X_MAX = GameMap.MAP_WIDTH - WIDTH;
    public final int Y_MAX = GameMap.MAP_HEIGHT - HEIGHT;
    public final int SD_ID = GameSound.GSD_HITWALL;

    /**
     * The bullet damage range.
     */
    public final Rect DAMAGE_RANGE = new Rect(0, 0, WIDTH << 2, HEIGHT << 2);


    /**
     * The constructor to do some initialize work,
     * parameter tank tell bullet belong which tank.
     *
     * @param gameContext The game context.
     * @param owner The bullet owner tank.
     */
    public Bullet(GameContext gameContext, Tank owner) {
        super(gameContext, owner.getX(), owner.getY());
    }


    /**
     * The default constructor.
     * @param gameContext The game context.
     */
    public Bullet(GameContext gameContext) {
        super(gameContext);
    }


    /**
     * Notify all register observers.
     */
    protected void notifyObservers() {
        switch (state) {
            case fired:
                synchronized (firingListeners) {
                    for (BulletEventListener.OnFiringListener observer : firingListeners) {
                        if (observer != null)
                            observer.onFiring(this);
                    }
                }
                break;
            case flying:
                synchronized (flyingListeners) {
                    for (BulletEventListener.OnFlyingListener observer : flyingListeners) {
                        if (observer != null)
                            observer.onFlying(this);
                    }
                }
                break;
            case exploded:
                synchronized (explosionListeners) {
                    for (BulletEventListener.OnExplosionListener observer : explosionListeners) {
                        if (observer != null)
                            observer.onExploding(this);
                    }
                }
                break;
            case hitBuild:
                synchronized (hitBuildingListeners) {
                    for (BulletEventListener.OnHitBuildingListener observer : hitBuildingListeners) {
                        if (observer != null)
                            observer.onHitBuilding(this);
                    }
                }
                break;
            case hitTank:
                synchronized (hitTankListeners) {
                    for (BulletEventListener.OnHitTankListener observer : hitTankListeners) {
                        if (observer != null)
                            observer.onHitTank(this, this.ownerTank);
                    }
                }
                break;
        }
    }


    public void fire() {
        state = BulletEventListener.State.fired;
    }


    public void flying() {
        if (isExplosion)
            return;
        notifyObservers();
    }


    public synchronized void addOnFiringListener(BulletEventListener.OnFiringListener listener) {
        firingListeners.add(listener);
    }

    public synchronized void removeOnFiringListener(BulletEventListener.OnFiringListener listener) {
        firingListeners.remove(listener);
    }

    public synchronized void addOnFlyingListener(BulletEventListener.OnFlyingListener listener) {
        flyingListeners.add(listener);
    }

    public synchronized void removeOnFlyingListener(BulletEventListener.OnFlyingListener listener) {
        firingListeners.remove(listener);
    }

    public synchronized void addOnExplosionListener(BulletEventListener.OnExplosionListener listener) {
        explosionListeners.add(listener);
    }

    public synchronized void removeExplosionListener(BulletEventListener.OnExplosionListener listener) {
        explosionListeners.remove(listener);
    }


    public synchronized void addHitBuildingListener(BulletEventListener.OnHitBuildingListener listener) {
        hitBuildingListeners.add(listener);
    }

    public synchronized void removeOnHitBuildingListener(BulletEventListener.OnHitBuildingListener listener) {
        hitBuildingListeners.remove(listener);
    }

    public synchronized void addHitTankListener(BulletEventListener.OnHitTankListener listener) {
        hitTankListeners.add(listener);
    }

    public synchronized void removeOnHitTankListener(BulletEventListener.OnHitTankListener listener) {
        hitTankListeners.remove(listener);
    }

    public Bitmap[] getBulletBitmaps() {
        return bulletBitmaps;
    }

    public void setBulletBitmaps(Bitmap[] bulletBitmap) {
        this.bulletBitmaps = bulletBitmap;
    }

    public boolean isExplosion() {
        return isExplosion;
    }

    public void setIsExplosion(boolean isExplosion) {
        this.isExplosion = isExplosion;
    }

    public boolean isCanDestroyIron() {
        return canDestroyIron;
    }

    public void setCanDestroyIron(boolean canDestroyIron) {
        this.canDestroyIron = canDestroyIron;
    }

    public Tank getOwnerTank() {
        return ownerTank;
    }

    public void setOwnerTank(Tank ownerTank) {
        this.ownerTank = ownerTank;
    }


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
}
