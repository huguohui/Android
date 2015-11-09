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
	private static GameContext instance  = null;

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

	/** Device screen width. */
	public final static int SCREEN_WIDTH = 0;

	/** Device screen heigth. */
	public final static int SCREEN_HEIGHT = 0;


	public final void onCreate() {
		super.onCreate();
		initialize();
	}



	private final void initialize() {
		instance = this;
	}


	/**
	 * Set a global shared variable, storage as key => value.
	 * @param key Variable key of value.
	 * @param value Variable value.
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


	public final void toast(String s) {
		Toast.makeText(GameContext.getGameContext().getBaseContext(), s, Toast.LENGTH_SHORT).show();
	}


	public final static GameContext getGameContext() {
		return instance;
	}
}
