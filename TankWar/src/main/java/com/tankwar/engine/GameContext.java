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

    /** Directory separator. */
	public final static String DS = "/";

	/** The sd card root path. */
	public final static String BASE_PATH = "";

	/** THe game log directory. */
	public final static String GAME_LOG_PATH = "Logs";

	/** The game cache directory */
	public final static String GAME_CACHE_PATH = "Cache";

	/** Device screen width. */
	public final static int SCREEN_WIDTH = 0;

	/** Device screen heigth. */
	public final static int SCREEN_HEIGHT = 0;


	/**
	 * When game context creating, this method will run once.
	 */
	public final void onCreate() {
		super.onCreate();
		initialize();
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
	 * Do some initialization work in this place.
	 */
	private final void initialize() {
		mInstance = this;
	}


	/**
	 * Set a global shared variable, storage as key => value.
	 * @param key Variable key of value.
	 * @param obj Variable value.
	 */
	public final void setData(String key, Object obj) {
		data.put(key, obj);
	}


	/**
	 * Get a global shared variable by a key.
	 * @param key Variable key.
	 */
	public final Object getData(String key) {
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
