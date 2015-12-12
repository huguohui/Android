package com.tankwar.ui;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.entity.MovableEntity;
import com.tankwar.engine.subsystem.ControlSubsystem;
import com.tankwar.engine.subsystem.Controllable;
import com.tankwar.engine.subsystem.Drawable;
import com.tankwar.game.Game;
import com.tankwar.utils.GameSound;

final public class VirtualPad implements ControlSubsystem.TouchEventListener, Drawable {
	public int D_Pad_W = 50;
	public int D_Pad_H = 50;
	public int A_Pad_W = 50;
	public int A_Pad_H = 50;
	public int keyDistance 	= 50;
	public int bottomMargin = 20;
	public int topMargin	= Game.SCREEN_HEIGHT- (bottomMargin + (D_Pad_H << 1) + keyDistance);
	public int leftMargin 	= 20;
	public int rightMargin 	= 20;

	public Rect upKeyRect 	= null;
	public Rect downKeyRect = null;
	public Rect leftKeyRect = null;
	public Rect rightKeyRect= null;
	public Rect atkKeyRect	= null;
	public RectF ukRealRect = new RectF();
	public RectF dkRealRect = new RectF();
	public RectF lkRealRect = new RectF();
	public RectF rkRealRect = new RectF();
	public RectF akRealRect = new RectF();

	private Controllable mControllable;

	public final int KEY_UP		= 1;
	public final int KEY_RIGHT	= 2;
	public final int KEY_LEFT	= 3;
	public final int KEY_DOWN	= 4;
	public final int KEY_ATTACK = 5;
	public final int KEY_NONE	= 6;
	public final int SOUND_ID	=  GameSound.GSD_PLAYERMOVE;

	public VirtualPad() {
		Matrix mtx = new Matrix();
		GameContext g = GameContext.getGameContext();
		mtx.setScale(g.getEngine().getGraphicsSubsystem().getXScale(),
				g.getEngine().getGraphicsSubsystem().getYScale());

		upKeyRect = new Rect(leftMargin + D_Pad_W + ((keyDistance - D_Pad_W) / 2),
				topMargin, rightMargin + D_Pad_W + ((keyDistance - D_Pad_W) / 2) + D_Pad_W,
				topMargin + D_Pad_H);
		downKeyRect = new Rect(upKeyRect.left, upKeyRect.top + keyDistance + D_Pad_H,
				upKeyRect.right, upKeyRect.top + keyDistance + D_Pad_H + D_Pad_H);
		leftKeyRect = new Rect(leftMargin, topMargin + D_Pad_H + ((keyDistance -
				D_Pad_W) / 2), leftMargin + D_Pad_W, topMargin + D_Pad_H + ((keyDistance - D_Pad_W) / 2)
				+ D_Pad_W);
		rightKeyRect = new Rect(leftKeyRect.left + keyDistance + D_Pad_W, leftKeyRect.top,
				leftKeyRect.left + keyDistance + D_Pad_W * 2, leftKeyRect.bottom);
		atkKeyRect = new Rect(Game.SCREEN_WIDTH - rightMargin - A_Pad_W, Game.SCREEN_HEIGHT -
				bottomMargin - A_Pad_H, Game.SCREEN_WIDTH - rightMargin, Game.SCREEN_HEIGHT -
				bottomMargin);

		mtx.mapRect(ukRealRect, new RectF(upKeyRect));
		mtx.mapRect(dkRealRect, new RectF(downKeyRect));
		mtx.mapRect(lkRealRect, new RectF(leftKeyRect));
		mtx.mapRect(rkRealRect, new RectF(rightKeyRect));
		mtx.mapRect(akRealRect, new RectF(atkKeyRect));
	}


	public void onLoosen(int x, int y) {
		switch(getKey(x, y)) {
			case KEY_UP:
				loosenUp();
				break;
			case KEY_DOWN:
				loosenDown();
				break;
			case KEY_RIGHT:
				loosenRight();
				break;
			case KEY_LEFT:
				loosenLeft();
				break;
			case KEY_ATTACK:
				loosenA();
				break;
		}
	}


	private void pressA() {
		mControllable.attack();
	}


	private void pressUp() {
		mControllable.move(MovableEntity.Direction.UP);
	}


	private void pressRight() {
		mControllable.move(MovableEntity.Direction.RIGHT);
	}


	private void pressDown() {
		mControllable.move(MovableEntity.Direction.DOWN);
	}


	private void pressLeft() {
		mControllable.move(MovableEntity.Direction.LEFT);
	}


	private void loosenA() {
	}


	private void loosenUp() {

	}


	private void loosenRight() {

	}


	private void loosenDown() {

	}


	private void loosenLeft() {

	}


	private int getKey(int x, int y) {
		if (ukRealRect.contains(x, y)) {
			return KEY_UP;
		}else if (dkRealRect.contains(x, y)) {
			return KEY_DOWN;
		}else if (rkRealRect.contains(x, y)) {
			return KEY_RIGHT;
		}else if (lkRealRect.contains(x, y)) {
			return KEY_LEFT;
		}else if (akRealRect.contains(x, y)){
			return KEY_ATTACK;
		}else{
			return KEY_NONE;
		}
	}


	public void onPress(int x, int y) {
		switch(getKey(x, y)) {
			case KEY_UP:
				pressUp();
				break;
			case KEY_DOWN:
				pressDown();
				break;
			case KEY_RIGHT:
				pressRight();
				break;
			case KEY_LEFT:
				pressLeft();
				break;
			case KEY_ATTACK:
				pressA();
				break;
		}
	}


	/**
	 * To draw itself.
	 *
	 * @param c The canvas.
	 */
	@Override
	public void draw(Canvas c) {
		Paint p = new Paint();
		p.setARGB(100, 255, 255, 255);
		c.drawRect(upKeyRect, p);
		c.drawRect(downKeyRect, p);
		c.drawRect(leftKeyRect, p);
		c.drawRect(rightKeyRect, p);
		c.drawRect(atkKeyRect, p);
	}


	/**
	 * The layer index of draw.
	 *
	 * @return A number that >= 0.
	 */
	@Override
	public int getIndex() {
		return 4;
	}


	public void setControllable(Controllable controllable) {
		if (controllable == null) return;
		this.mControllable = controllable;
	}


	public Controllable getControllable() {
		return mControllable;
	}
}
