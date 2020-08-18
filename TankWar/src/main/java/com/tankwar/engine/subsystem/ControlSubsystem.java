package com.tankwar.engine.subsystem;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.tankwar.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * The control subsystem.
 *
 * @since 2015/11/10
 */
public class ControlSubsystem
		extends Subsystem
		implements View.OnTouchListener, View.OnKeyListener, Engine.StateListener {
	/**
	 * The motion event listeners.
	 */
	private List<TouchEventListener> mTouchEventListeners = new ArrayList<>();

	/**
	 * Key event listeners.
	 */
	private List<KeyEventListener> mKeyEventListeners = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * @param engine Engine instance.
	 */
	public ControlSubsystem(Engine engine) {
		super(engine);
		engine.addStateListener(this);
	}

	/**
	 * Per loop will call this method.
	 */
	@Override
	public final void update() {

	}

	/**
	 * Called when a touch event is dispatched to a view. This allows listeners to
	 * get a chance to respond before the target view.
	 *
	 * @param v     The view the touch event has been dispatched to.
	 * @param event The MotionEvent object containing full information about
	 *              the event.
	 * @return True if the listener has consumed the event, false otherwise.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int pointerCount = event.getPointerCount();
		if (pointerCount > 1) {
			for (int i = 0; i < pointerCount; i++) {
				int index = i, x, y;
				boolean isUp = false;
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_POINTER_DOWN:
						break;
					case MotionEvent.ACTION_POINTER_UP:
						isUp = true;
						break;
				}
				x = (int) event.getX(index);
				y = (int) event.getY(index);
				for (TouchEventListener tl : mTouchEventListeners) {
					if (isUp) {
						tl.onLoosen(x, y);
					} else {
						tl.onPress(x, y);
					}
				}
			}
		} else {
			boolean isUp = false;
			int x = (int) event.getX(), y = (int) event.getY();
			switch (event.getAction() & event.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_UP:
					isUp = true;
					break;
			}

			for (TouchEventListener tl : mTouchEventListeners) {
				if (isUp) {
					tl.onLoosen(x, y);
				} else {
					tl.onPress(x, y);
				}
			}
		}

		return true;
	}

	/**
	 * Called when a hardware key is dispatched to a view. This allows listeners to
	 * get a chance to respond before the target view.
	 * <p>Key presses in software keyboards will generally NOT trigger this method,
	 * although some may elect to do so in some situations. Do not assume a
	 * software input method has to be key-based; even if it is, it may use key presses
	 * in a different way than you expect, so there is no way to reliably catch soft
	 * input key presses.
	 *
	 * @param v       The view the key has been dispatched to.
	 * @param keyCode The code for the physical key that was pressed
	 * @param event   The KeyEvent object containing full information about
	 *                the event.
	 * @return True if the listener has consumed the event, false otherwise.
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		boolean isUp = true;
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			isUp = false;
		} else if (event.getAction() == KeyEvent.ACTION_UP) {
			isUp = true;
		}

		for (KeyEventListener kl : mKeyEventListeners) {
			if (kl != null) {
				if (isUp)
					kl.onKeyUp(event);
				else
					kl.onKeyDown(event);
			}
		}
		return true;
	}

	public List<TouchEventListener> getTouchEventListeners() {
		return mTouchEventListeners;
	}

	public synchronized void addTouchEventListener(TouchEventListener touchEventListener) {
		mTouchEventListeners.add(touchEventListener);
	}

	public synchronized void removeTouchEventListener(TouchEventListener touchEventListener) {
		mTouchEventListeners.remove(touchEventListener);
	}

	public List<KeyEventListener> getKeyEventListeners() {
		return mKeyEventListeners;
	}

	public synchronized void addKeyEventListener(KeyEventListener keyEventListener) {
		mKeyEventListeners.add(keyEventListener);
	}

	public synchronized void removeKeyEventListener(KeyEventListener keyEventListener) {
		mKeyEventListeners.remove(keyEventListener);
	}

	/**
	 * When engine initialized.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onInitialize(Engine engine) {

	}

	/**
	 * When engine start work.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onStart(Engine engine) {
		engine.getGraphicsSubsystem().getCanvasView().setOnTouchListener(this);
		engine.getGraphicsSubsystem().getCanvasView().setOnKeyListener(this);
	}

	/**
	 * When engine pause.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onPause(Engine engine) {

	}

	/**
	 * When engine resume.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onResume(Engine engine) {

	}

	/**
	 * When engine stop work.
	 *
	 * @param engine engine engine.
	 */
	@Override
	public void onStop(Engine engine) {

	}

	/**
	 * Touch event listener.
	 */
	public interface TouchEventListener {
		/**
		 * Called when press.
		 */
		void onPress(int x, int y);

		/**
		 * Called when loosen.
		 */
		void onLoosen(int x, int y);
	}

	/**
	 * Key event listener.
	 */
	public interface KeyEventListener {
		/**
		 * Called when key down.
		 */
		void onKeyDown(KeyEvent event);

		/**
		 * Called when key up.
		 */
		void onKeyUp(KeyEvent event);
	}
}