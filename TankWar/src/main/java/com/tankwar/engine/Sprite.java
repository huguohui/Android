package com.tankwar.engine;

import android.graphics.Bitmap;

/**
 * Sprite graphics interface.
 * @since 2015/11/06
 */
public class Sprite {
    /** The sprites. */
    private Bitmap[] mSprites;


    /**
     * To constructing a sprite.
     * @param source The sprite source.
     * @param width The per sprite width.
     * @param height The per sprite height.
     */
    public Sprite(Bitmap source, int width, int height) {

    }


    /**
     * To constructing a sprite.
     * @param source The source.
     */
    public Sprite(Bitmap source) {
        mSprites = new Bitmap[]{source};
    }


    /**
     * Get sprites.
     * @return All sprites.
     */
    public Bitmap[] getAll() {
        return mSprites;
    }


    /**
     * Get a sprite.
     * @return A sprite.
     */
    public Bitmap get(int index) {
        if (index < mSprites.length) {
            return mSprites[index];
        }
        return null;
    }
}