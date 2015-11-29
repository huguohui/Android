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
    public Sprite(Bitmap source, int width, int height, int dir) {
        for (int w = 0; w <= source.getWidth(); w += width) {
            for (int h = 0; h <= source.getHeight(); h += height) {

            }
        }
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