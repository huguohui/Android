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

import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.tankwar.client.Client;
import com.tankwar.client.Game;


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

	/** THe game log directory. */
	public final static String LOG_DIR = "Logs";

	/** The game cache directory */
	public final static String CACHE_DIR = "Cache";

    /** The game bitmaps directory. */
    public final static String BITMAP_DIR = "bitmaps";

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
        APP_ROOT = this.getApplicationInfo().dataDir + DS + this.getApplicationInfo().packageName;
        SDCARD_APP_ROOT = SDCARD_ROOT + DS + this.getApplicationInfo().packageName;
        mResourcesManager = new ResourcesManager(this);
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
