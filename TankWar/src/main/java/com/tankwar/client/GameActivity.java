/************************************************************************************************
 * Game activity.																			    *
 * Copyright (C) 2015 - 2017 Guohui Hu														    *
 *																							    *
 * This program is free software: you can redistribute it and/or modify it under the terms of   *
 * the GNU General Public License as published by the Free Software Foundation, either version  *
 * 3 of the License, or (at your option) any later version.										*
 *																								*
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;	*
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	*
 * See the GNU General Public License for more details.											*
 *																								*
 * You should have received a copy of the GNU General Public License along with this program.	*
 * if not, see <http://www.gnu.org/licenses/>.													*
 *																								*
 * 2015/6/10 02:24 Wed																			*
 ************************************************************************************************/

package com.tankwar.client;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.tankwar.utils.GameLog;

final public class GameActivity extends Activity {
	private GameView gameView = null;
	private boolean  isExited = false;
	private static GameActivity mInstance = null;

	protected final void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    						WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mInstance = this;
		gameView = new GameView(this);
		setContentView(gameView);
		gameView.onGameStart();
	}


	public final void onBackPressed() {
		try {
			gameView.onGameStop();
			gameView.getViewThread().join();
			gameView.release();
			finish();
		} catch (Throwable e) {
			GameLog.e(e);
		}
	}


	public final static GameActivity getInstance() {
		return mInstance;
	}


	protected final void onPause() {
		super.onPause();
		gameView.onGamePause();
	}


	protected final void onResume() {
		super.onResume();
		gameView.onGameResume();
	}


	@Override
	public final void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}


