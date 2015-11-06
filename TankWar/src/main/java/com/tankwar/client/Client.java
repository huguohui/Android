/************************************************************************************************
 * Client activity.																			    *
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
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


/**
 * The game base class.
 * @since 2015/11/06
 */
public abstract class Client extends Activity {
    private boolean mIsMenuOpen = false;
    private Game mGame;


	protected final void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    						WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}


	public final void onBackPressed() {
        mIsMenuOpen = !mIsMenuOpen;
        if (mIsMenuOpen){
            super.openOptionsMenu();
        }
	}


	protected final void onPause() {
		super.onPause();
	}


	protected final void onResume() {
		super.onResume();
	}
}


