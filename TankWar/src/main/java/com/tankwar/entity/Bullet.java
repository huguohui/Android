package com.tankwar.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.tankwar.client.GameMap;
import com.tankwar.engine.BulletEventListener;
import com.tankwar.engine.CollisionCheckable;
import com.tankwar.engine.CollisionListener;
import com.tankwar.ui.FrameUpdateListener;
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
 * @author hgh
 * @since 2015/10/29
 * @version 1.0
 */
public abstract class Bullet extends Entity implements
		FrameUpdateListener, CollisionListener, CollisionCheckable {
	private int x;
	private int y;
	private int cx;
	private int cy;
	private int direction;
	private boolean isExplosion;
	private boolean canDestroyIron = false;

	private Tank ownerTank;
	private Bitmap[] bulletBitmaps;
	private BulletEventListener.State 	state = null;
	private List<OnExplosionListener>	explosionListeners	= new ArrayList<>();
	private List<OnFiringListener>		firingListeners		= new ArrayList<>();
	private List<OnFlyingListener>		flyingListeners		= new ArrayList<>();
	private List<OnHitBuildingListener> hitBuildingListeners= new ArrayList<>();
	private List<OnHitTankListener>		hitTankListeners	= new ArrayList<>();

	public final int SPEED = 2;
	public final int WIDTH = 8;
	public final int HEIGHT= 8;
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
	 * @param owner The bullet owner tank.
	 */
	public Bullet(Tank owner) {
		ownerTank = owner;
		direction = owner.getDirection();
		state = BulletEventListener.State.fired;

		initPos();
		setProfile();
		notifyObservers();
	}


	/**
	 * The default constructor.
	 */
	public Bullet() {

	}


	protected void initPos() {
		switch(direction) {
			case Tank.DIR_UP:
				x = ownerTank.getX() + ((ownerTank.getW() - WIDTH) >> 1);
				y = ownerTank.getY();
				break;
			case Tank.DIR_DOWN:
				x = ownerTank.getX() + ((ownerTank.getW() - WIDTH) >> 1);
				y = ownerTank.getY() + ownerTank.getH();
				break;
			case Tank.DIR_LEFT:
				x = ownerTank.getX();
				y = ownerTank.getY() + ((ownerTank.getH() - HEIGHT) >> 1);
				break;
			case Tank.DIR_RIGHT:
				x = ownerTank.getX() + ownerTank.getW();
				y = ownerTank.getY() + ((ownerTank.getH() - HEIGHT) >> 1);
				break;
		}
		cx = ownerTank.getX();
		cy = ownerTank.getY();
	}


	/**
	 * Notify all register listen event observers.
	 */
	protected void notifyObservers() {
		switch (state) {
			case fired:
				synchronized(firingListenres) {
					for (OnFiringListener observer : firingListeners) {
						if (observer != null)
							observer.onFiring(this);
					}
				}
				break;
			case flying:
				synchronized(flyingListeners) {
					for (OnFlyingListener observer : flyingListeners) {
						if (observer != null)
							observer.onFlying(this);
					}
				}
				break;
			case exploded:
				synchronized(explosionListeners) {
					for (OnExplosionListener observer : explosionListeners) {
						if (observer != null)
							observer.onExploding(this);
					}
				}
				break;
			case hitBuild:
				synchronized(hitBuildingListeners) {
					for (OnHitBuildingListener observer : hitBuildingListeners) {
						if (observer != null)
							observer.onHitBuilding(this);
					}
				}
				break;
			case hitTank:
				synchronized(hitTankListeners) {
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
		if (isExplosion) {
			return;
		}

		switch(direction) {
			case Tank.DIR_UP:
				y -= speed;
				cy -= speed;
				break;
			case Tank.DIR_DOWN:
				y += speed;
				cy += speed;
				break;
			case Tank.DIR_LEFT:
				x -= speed;
				cx -= speed;
				break;
			case Tank.DIR_RIGHT:
				x += speed;
				cx += speed;
				break;
		}
		notifyObservers();
	}


	public boolean isHitting() {
		Tank[] ts = eownerTank.getTanks();
		int  size = 0;
		boolean play = false;
		boolean cols = false;

		for (Point p : hitPoint) {
			Point np  = ownerTank.getMapTPos(p.x, p.y);
			Tile tile =  gameMap.getMapData()[np.x][np.y];
			int type = tile.getType();
			if (type == GameMap.TILE_WALL || type == GameMap.TILE_IRON) {
				Rect[] rs = tile.getRects();
				for (int i = 0; i < rs.length; i++) {
					if (rs[i] != null) {
						if (ownerTank.isCollisions(cx, cy, CCW, CCH, rs[i].left, rs[i].top, rs[i].right -
								rs[i].left, rs[i].bottom - rs[i].top)) {
							Rect destroyRegion = null;
							if (type == GameMap.TILE_IRON) {
								if (canDestroyIron)
									destroyRegion = ((Tile.IronTile)tile).destroyRegion(i);
								else
									play = true;
							}else{
								destroyRegion = ((Tile.WallTile)tile).destroyRegion(i);
							}
							if (destroyRegion != null)
								gameMap.onDestroyTile(destroyRegion);

							cols = true;
						}
					}
				}
			}else if (type == GameMap.TILE_SYMBOL) {
				gameMap.onDestroySymbol();
				cols = true;
			}
		}

		if (x > X_MAX || y > Y_MAX || x < X_MIN || y < Y_MIN) {
			hittingType = HIT_SIDE;
			cols = true;
			play = true;
		}
		if (play && ownerTank.isPlayer()) GameSound.play(SD_ID);
		if (cols) return cols;

		for (Tank t : PlayerTank.getAllPlayers()) {
			if (t != null && t != ownerTank) {
				if (ownerTank.isCollisions(x, y, WIDTH, HEIGHT, t.getX(), t.getY(), t.getW(), t.getH())) {
					hittingType = HIT_TANK;
					ownerTank.onHittingTank(t);
					return true;
				}
			}
		}

		if (ownerTank.isPlayer()) {
			for (int i = 0; i < ts.length; i++) {
				Tank t = ts[i];
				if (t != null && ownerTank.isCollisions(x, y, WIDTH, HEIGHT, t.getX(), t.getY(), t.getW(),
					t.getH())) {
					hittingType = HIT_TANK;
					ownerTank.onHittingTank(t);
					return true;
				}
			}
		}

		size = allBullets.size();
		for (int i = 0; i < size; i++) {
			Bullet b = allBullets.get(i);
			if (b != null && b != this) {
				if (ownerTank.isCollisions(x, y, WIDTH, HEIGHT, b.getX(), b.getY(), WIDTH, HEIGHT)) {
					hittingType = HIT_BULLET;
					onHittingByBullet();
					b.onHittingByBullet();
					return false;
				}
			}
		}

		return false;
	}


	protected void setProfile() {
		if (ownerTank.isPlayer()) {
			int lv = ownerTank.getLevel();
			speed = playerBulletSpeeds[lv];
			canDestroyIron = killIron[lv];
			ownerTank.setBulletTotal(bullets[lv]);
		}else{
			speed = enemyBulletSpeeds[ownerTank.getLevel()];
		}
	}


	public void draw(Canvas c) {
		if (!isExplosion) {
			c.drawBitmap(bulletBitmaps[direction], gameMap.getAbsoluteX(x), gameMap.getAbsoluteY(y), null);
		}
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

	public int getCx() {
		return cx;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
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
