package com.tankwar.engine;


import android.graphics.Canvas;

/**
 * To listening game per frame updates,
 * you can implement this interface.
 * @author  hgh
 * @since 2015/10/29
 */
public interface Drawable {
	/**
	 * When screen was updated,
	 * this method will be called.
	 */
	void onDraw(Canvas canvas);
}
