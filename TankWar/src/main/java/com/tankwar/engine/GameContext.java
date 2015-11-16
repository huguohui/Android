package com.tankwar.engine;

/**   				Game environment context.
 * 			    Copyright (C) 2015 By Guohui Hu
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version
 * 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * if not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.tankwar.client.Client;
import com.tankwar.client.Game;
import com.tankwar.utils.FileOperator;


/**
 * The game environment context class. It can pass some data in some class,
 * It's global object, that can use it simple in everywhere.
 * 
 * @since 2015/11/09
 */
final public class GameContext extends Application
{
	/** Game context static instance. */
	private static GameContext mInstance  = null;

	/** Shared data. */
	private HashMap<String, Object> data = new HashMap<String, Object>();

	/** Game object instance. */
    private Game mGame;

    /** Client instance. */
    private Client mClient;

    /** Engine instance. */
    private Engine mEngine;

    /** ResourcesManager instance. */
    private ResourcesManager mResourcesManager;

    /** Directory separator. */
	public final static String DS = "/";

	/** The sd card root path. */
	public static String SDCARD_ROOT = Environment.getExternalStorageDirectory().getPath();

    /** The game directory. */
    public static String APP_ROOT = "";

    /** The game directory on sdcard. */
    public static String SDCARD_APP_ROOT = "";

    /** The android directory on sdcard. */
    public final static String SDCARD_ANDROID_DIR = "Android";

    /** The android data directory on sdcard. */
    public final static String SDCARD_DATA_DIR = "data";

	/** THe game log directory. */
	public final static String LOG_DIR = "logs";

	/** The game cache directory */
	public final static String CACHE_DIR = "cache";

    /** The game bitmaps directory. */
    public final static String BITMAP_DIR = "bitmaps";

    /** The game maps directory. */
    public final static String MAP_DIR = "maps";

    /** The game sounds directory. */
    public final static String SOUND_DIR = "sounds";


	/**
	 * When game context creating, this method will run once.
	 */
	public final void onCreate() {
		super.onCreate();
		initialize();
	}


    /**
     * Do some initialization work in this place.
     */
    private final void initialize() {
        mInstance = this;
        APP_ROOT = this.getApplicationInfo().dataDir;
        SDCARD_APP_ROOT = SDCARD_ROOT + DS + SDCARD_ANDROID_DIR + DS
                    + SDCARD_DATA_DIR + DS + this.getApplicationInfo().packageName;
        mResourcesManager = new ResourcesManager(this);

        checkAndCreateDirs();
        releaseResToSdcard();
    }


    /**
     * If directory not exists, create it.
     */
    private void checkAndCreateDirs() {
        File file;
        String[] paths = {
                SDCARD_APP_ROOT, SDCARD_APP_ROOT + DS + LOG_DIR,
                SDCARD_APP_ROOT + DS + CACHE_DIR,
                SDCARD_APP_ROOT + DS + BITMAP_DIR,
                SDCARD_APP_ROOT + DS + SOUND_DIR,
                SDCARD_APP_ROOT + DS + MAP_DIR

        };
        for (String path : paths) {
            if (!(file = new File(path)).isDirectory()) {
                if (!file.mkdir()) {
                    this.toast("Can't create directory: " + file.getAbsolutePath());
                }
            }
        }
    }


    /**
     * Release game resource to sdcard.
     */
    private void releaseResToSdcard() {
        try {
            if (new File(SDCARD_APP_ROOT + DS + BITMAP_DIR + DS + ".exists").isFile() &&
                new File(SDCARD_APP_ROOT + DS + SOUND_DIR + DS + ".exists").isFile())
                return;

            for (String assetName : getAssets().list(BITMAP_DIR)) {
                if (!new FileOperator(getAssets().open(BITMAP_DIR + DS + assetName))
                        .copyTo(SDCARD_APP_ROOT + DS + BITMAP_DIR + DS + assetName)) {
                    com.tankwar.utils.Log.s(assetName + " copy failed!");
                }
            }

            for (String assetName : getAssets().list(SOUND_DIR)) {
                if (assetName.matches(".+\\.(wav|mp3|mid|ogg)$"))
                    if (!new FileOperator(getAssets().open(SOUND_DIR + DS + assetName))
                            .copyTo(SDCARD_APP_ROOT + DS + SOUND_DIR + DS + assetName)) {
                        com.tankwar.utils.Log.s(assetName + "copy failed!");
                    }
            }
            new File(SDCARD_APP_ROOT + DS + BITMAP_DIR + DS + ".exists").createNewFile();
            new File(SDCARD_APP_ROOT + DS + SOUND_DIR + DS + ".exists").createNewFile();
        } catch (IOException e) {
            com.tankwar.utils.Log.e(e);
        }
    }


	/**
	 * Get game engine instance.
	 * @return Game engine.
	 */
	public final Engine getEngine() {
		return mEngine;
	}


	/**
	 * Get client instance.
	 * @return Clietn instance.
	 */
	public final Client getClient() {
		return mClient;
	}


	/**
	 * Get game instance.
	 * @return game instance.
	 */
	public final Game getGame() {
		return mGame;
	}


    /**
     * Get resources manager.
     * @return ResourcesManager.
     */
    public final ResourcesManager getResourcesManager() {
        return mResourcesManager;
    }


    /**
     * Get screen width of device.
     * @return Screen width of device.
     */
    public final int getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * Get screen height of device.
     * @return Screen height device.
     */
    public final int getScreenHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * Get the display metrics.
     * @return Display metrics.
     */
    public final DisplayMetrics getDisplayMetrics() {
        return this.getResources().getDisplayMetrics();
    }


	/**
	 * Set a global shared variable, storage as key => value.
	 * @param key Variable key of value.
	 * @param obj Variable value.
	 */
	public final void set(String key, Object obj) {
		data.put(key, obj);
	}


	/**
	 * Get a global shared variable by a key.
	 * @param key Variable key.
	 */
	public final Object get(String key) {
		return data.get(key);
	}


	/**
	 * A package of toast message, use this method will faster
	 * to show toast message.
	 *
	 * @param message The toast messsage.
	 * @param isLongShow The message if long time exists.
	 */
	public final void toast(String message, boolean isLongShow) {
		Toast.makeText(mInstance, message, isLongShow ? Toast.LENGTH_SHORT
                : Toast.LENGTH_LONG).show();
	}


    public void setGame(Game game) {
        mGame = game;
    }

    public void setClient(Client client) {
        mClient = client;
    }

    public void setEngine(Engine engine) {
        mEngine = engine;
    }

    /**
	 * A package of toast message, use this method will faster
	 * to show toast message.
	 *
	 * @param message The toast messsage.
	 */
	public final void toast(String message) {
		toast(message, false);
	}


	/**
	 * Only method to get game context instance.
	 */
	public final static GameContext getGameContext() {
		return mInstance;
	}
}
