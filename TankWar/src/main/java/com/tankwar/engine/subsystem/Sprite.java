package com.tankwar.engine.subsystem;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Sprite graphics interface.
 * @since 2015/11/06
 */
public class Sprite {
    /** The sprites. */
    private List<Bitmap> mSprites = new ArrayList<>();


    /**
     * To constructing a sprite.
     * @param source The sprite source.
     * @param width The per sprite width.
     * @param height The per sprite height.
     */
    public Sprite(Bitmap source, int width, int height) {
		if (source == null)
			throw new NullPointerException("Bitmap can't null!");

		for (int h = 0; h <= source.getHeight(); h += height) {
			for (int w = 0; w <= source.getWidth(); w += width) {
				mSprites.add(Bitmap.createBitmap(source, w * width, h * height, width, height));
			}
		}
    }


	/**
	 * To constructing a sprite.
	 * @param source The sprite source.
	 * @param x The bmp x in source.
	 * @param y The bmp y in source.
	 * @param width The per sprite width.
	 * @param height The per sprite height.
	 */
	public Sprite(Bitmap source, int x, int y, int width, int height) {
		if (source == null)
			throw new NullPointerException("Bitmap can't null!");

		if (x < 0 || y < 0 || y > source.getHeight() || x > source.getWidth() || width <= 0
				|| height <= 0 || width + x > source.getWidth() || height + y > source.getHeight())
			throw new IllegalArgumentException("The x or y or width or height is illegal argument!");

		mSprites.add(Bitmap.createBitmap(source, x, y, width, height));
	}


    /**
     * To constructing a sprite.
     * @param source The source.
     */
    public Sprite(Bitmap source) throws NullPointerException {
        if (source == null)
            throw new NullPointerException("Bitmap can't null!");

        mSprites.add(source);
    }


    /**
     * Get sprites.
     * @return All sprites.
     */
    public List<Bitmap> getAll() {
        return mSprites;
    }


    /**
     * Get a sprite.
     * @return A sprite.
     */
    public Bitmap getBitmap(int index) {
        if (index < mSprites.size() && index >= 0) {
            return mSprites.get(index);
        }
        return null;
    }
}