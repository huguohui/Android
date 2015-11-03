package com.tankwar.ui;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * To listening game per frame updates,
 * you can implement this interface.
 * @author  hgh
 * @since 2015/10/29
 */
public interface FrameUpdateListener {
	/**
	 * When screen was updated,
	 * this method will be called.
	 */
	void onFrameUpdate(Canvas canvas);
}
