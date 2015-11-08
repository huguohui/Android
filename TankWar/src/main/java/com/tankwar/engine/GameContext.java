package com.tankwar.engine;

/**   				Client context class.
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
import android.app.Application;
import android.widget.Toast;

import com.tankwar.client.Client;
import com.tankwar.client.Game;

final public class GameContext extends Application
{
	private static GameContext instance  = null;
	private HashMap<String, Object> data = new HashMap<String, Object>();
    private Game mGame;
    private Client mClient;
    private Engine mEngine;
	public final static String DS = "/";


	public final void onCreate() {
		super.onCreate();
		initialize();
	}



	private final void initialize() {
		instance = this;
	}


	public final void setData(String key, Object obj) {
		data.put(key, obj);
	}


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
