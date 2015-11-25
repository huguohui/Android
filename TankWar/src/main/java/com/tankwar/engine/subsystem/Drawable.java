package com.tankwar.engine.subsystem;


import android.graphics.Canvas;

/**
 * A drawable describe a can draw power.
 * @author  hgh
 * @since 2015/10/29
 */
public interface Drawable {
	/**
     * To draw itself.
     * @param canvas The canvas.
	 */
	void draw(Canvas canvas);


	/**
	 * The layer index of draw.
	 * @return A number that >= 0.
	 */
	int getIndex();
}
