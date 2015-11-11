package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.tankwar.client.GameMap;
import com.tankwar.engine.BulletEventListener;
import com.tankwar.utils.GameSound;
import com.tankwar.engine.BulletEventListener.OnExplosionListener;
import com.tankwar.engine.BulletEventListener.OnFiringListener;
import com.tankwar.engine.BulletEventListener.OnFlyingListener;
import com.tankwar.engine.BulletEventListener.OnHitBuildingListener;
import com.tankwar.engine.BulletEventListener.OnHitTankListener;
import com.tankwar.engine.BulletEventListener.State;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class of bullet, denoting a bullet.
 *
 * @author hgh
 * @version 1.0
 * @since 2015/10/29
 */
public abstract class Bullet extends MovableEntity {
    private int x;
    private int y;
    private int speed;
    private int direction;
    private boolean isExplosion;
    private boolean canDestroyIron = false;

    private Tank ownerTank;
    private Bitmap[] bulletBitmaps;
    private BulletEventListener.State state = null;
    private List<OnExplosionListener> explosionListeners = new ArrayList<>();
    private List<OnFiringListener> firingListeners = new ArrayList<>();
    private List<OnFlyingListener> flyingListeners = new ArrayList<>();
    private List<OnHitBuildingListener> hitBuildingListeners = new ArrayList<>();
    private List<OnHitTankListener> hitTankListeners = new ArrayList<>();

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
     * @param owner The bullet owner tank.
     */
    public Bullet(Tank owner) {
    }


    /**
     * The default constructor.
     */
    public Bullet() {

    }


    /**
     * Notify all register observers.
     */
    protected void notifyObservers() {
        switch (state) {
            case fired:
                synchronized (firingListeners) {
                    for (OnFiringListener observer : firingListeners) {
                        if (observer != null)
                            observer.onFiring(this);
                    }
                }
                break;
            case flying:
                synchronized (flyingListeners) {
                    for (OnFlyingListener observer : flyingListeners) {
                        if (observer != null)
                            observer.onFlying(this);
                    }
                }
                break;
            case exploded:
                synchronized (explosionListeners) {
                    for (OnExplosionListener observer : explosionListeners) {
                        if (observer != null)
                            observer.onExploding(this);
                    }
                }
                break;
            case hitBuild:
                synchronized (hitBuildingListeners) {
                    for (OnHitBuildingListener observer : hitBuildingListeners) {
                        if (observer != null)
                            observer.onHitBuilding(this);
                    }
                }
                break;
            case hitTank:
                synchronized (hitTankListeners) {
                    for (OnHitTankListener observer : hitTankListeners) {
                        if (observer != null)
                            observer.onHitTank(this, this.ownerTank);
                    }
                }
                break;
        }
    }


    public void fire() {
        state = State.fired;
    }


    public void flying() {
        if (isExplosion)
            return;
        notifyObservers();
    }


    public synchronized void addOnFiringListener(OnFiringListener listener) {
        firingListeners.add(listener);
    }

    public synchronized void removeOnFiringListener(OnFiringListener listener) {
        firingListeners.remove(listener);
    }

    public synchronized void addOnFlyingListener(OnFlyingListener listener) {
        flyingListeners.add(listener);
    }

    public synchronized void removeOnFlyingListener(OnFlyingListener listener) {
        firingListeners.remove(listener);
    }

    public synchronized void addOnExplosionListener(OnExplosionListener listener) {
        explosionListeners.add(listener);
    }

    public synchronized void removeExplosionListener(OnExplosionListener listener) {
        explosionListeners.remove(listener);
    }


    public synchronized void addHitBuildingListener(OnHitBuildingListener listener) {
        hitBuildingListeners.add(listener);
    }

    public synchronized void removeOnHitBuildingListener(OnHitBuildingListener listener) {
        hitBuildingListeners.remove(listener);
    }

    public synchronized void addHitTankListener(OnHitTankListener listener) {
        hitTankListeners.add(listener);
    }

    public synchronized void removeOnHitTankListener(OnHitTankListener listener) {
        hitTankListeners.remove(listener);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap[] getBulletBitmaps() {
        return bulletBitmaps;
    }

    public void setBulletBitmaps(Bitmap[] bulletBitmap) {
        this.bulletBitmaps = bulletBitmap;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
}