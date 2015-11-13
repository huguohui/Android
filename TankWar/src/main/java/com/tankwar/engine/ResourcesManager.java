package com.tankwar.engine;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;

import com.tankwar.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The game resources manager.
 * @since 2015/11/12
 */
public class ResourcesManager {
    /** The system resource. */
    private Resources mResources;

    /** The asset manager. */
    private AssetManager mAssetManager;

    /** The sound pool. */
    private SoundPool mSoundPool;

    /** The all loaded bitmaps */
    private Map<String, Bitmap> mBitmaps = new HashMap<>();

    /** The all sounds. */
    private Map<String, SoundPool> mSounds = new HashMap<>();

    /** The game context. */
    private GameContext mGameContext;

    /** Is load? */
    private boolean mIsLoaded = false;


    /**
     * The constructor.
     * @param context The game context.
     */
    public ResourcesManager(GameContext context) {
        mResources = context.getBaseContext().getResources();
        mAssetManager = context.getBaseContext().getAssets();
        mGameContext = context;
    }


    /**
     * Load all resources.
     */
    public final void loadAll() {
        if (!mIsLoaded) {
            loadBitmaps();
            loadSounds();
        }
    }


    /**
     * Release all resources.
     */
    public final void releaseAll() {
        if (mIsLoaded) {
            unloadBitmaps();
            unloadSounds();
        }
    }


    /**
     * Load all resource on start.
     */
    public void loadBitmaps() {
        try {
            for (String assetName : mAssetManager.list(GameContext.BITMAP_DIR)) {
                mBitmaps.put(assetName, BitmapFactory.decodeStream(mAssetManager.open(assetName)));
            }
        } catch (IOException e) {
            Log.e(e);
        }
    }


    /**
     * Load all sound on start.
     */
    public void loadSounds() {
        try {
            for (String assetName : mAssetManager.list(GameContext.SOUND_DIR)) {
                SoundPool sp = new SoundPool(0, 0, 0);
                sp.load(assetName, 0);
            }
        } catch (IOException e) {
            Log.e(e);
        }
    }


    /**
     * Unload all bitmaps.
     */
    public final void unloadBitmaps() {
        for (String name : mBitmaps.keySet()) {
            Bitmap bitmap = mBitmaps.get(name);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        mBitmaps = null;
    }


    /**
     * Unload all sounds.
     */
    public final void unloadSounds() {
        for (String name : mSounds.keySet()) {
            SoundPool sp = mSounds.get(name);
            if (sp != null) {
                sp.release();
                sp = null;
            }
        }
        mSounds = null;
    }


    /**
     * Get a sprite.
     * @param name Bitmap name.
     * @return A sprite.
     */
    public Sprite getSprite(String name) {
        return new Sprite(getBitmap(name));
    }


    /**
     * Get bitmap by name.
     * @param name Bitmap name.
     */
    public final Bitmap getBitmap(String name) {
        return mBitmaps.get(name);
    }


    /**
     * Get sound by name.
     * @param name Sound name.
     */
    public final SoundPool getSound(String name) {
        return mSounds.get(name);
    }
}
