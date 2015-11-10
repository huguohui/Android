package com.tankwar.engine;

import android.graphics.Canvas;

import java.util.List;

/**
 * A graphics layer hold some drawable objects,
 * when this layer was showed, all objects will be showed.
 * @since 2015/11/10
 */
public class Layer implements Drawable {
	/** All holds objects. */
	private List<Drawable> mObjects;

	/**
	 * Draw all objects in the layer.
	 */
	public void draw(Canvas canvas) {
		for (Drawable drawable : mObjects) {
			drawable.draw(canvas);
		}
	}
}