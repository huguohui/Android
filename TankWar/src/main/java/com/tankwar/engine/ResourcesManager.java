package com.tankwar.engine;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;

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


    /**
     * The constructor.
     * @param context The game context.
     */
    public ResourcesManager(GameContext context) {
        mResources = context.getBaseContext().getResources();
        mAssetManager = context.getBaseContext().getAssets();
        mGameContext = context;
        loadBitmaps();
        loadSounds();
    }


    /**
     * Load all resource on start.
     */
    public void loadBitmaps() {
        try {
            for (String assetName : mAssetManager.list(
                    mGameContext.getBaseContext().getFilesDir().getPath())) {
                mBitmaps.put(assetName, BitmapFactory.decodeStream(
                        mAssetManager.open(assetName)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Load all sound on start.
     */
    public void loadSounds() {
        try {
            for (String assetName : mAssetManager.list(mGameContext
                    .getBaseContext().getFilesDir().getPath())) {
                SoundPool sp = new SoundPool(0, 0, 0);
                sp.load(assetName, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get bitmap by name.
     * @param name Bitmap name.
     */
    public Bitmap getBitmap(String name) {
        return mBitmaps.get(name);
    }


    /**
     * Get sound by name.
     * @param name Sound name.
     */
    public SoundPool getSound(String name) {
        return mSounds.get(name);
    }
}
