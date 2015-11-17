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
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.tankwar.R;
import com.tankwar.engine.Engine;
import com.tankwar.engine.GameContext;


/**
 * The game base class.
 * @since 2015/11/06
 */
final public class Client extends Activity
                    implements View.OnClickListener{
    /**
     * The game instance.
     */
    private Game mGame;


    /**
     * The game options.
     */
    private Game.Option mOption = new Game.Option();


    /**
     * A state of menu whatever open.
     */
    private boolean mIsMenuOpen = false;


    /**
     * When application was launcher, this method will be called
     * by android os.
     * @param bundle Data bundle.
     */
	protected final void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_launcher);

        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.rdbSingle).setOnClickListener(this);
        findViewById(R.id.rdbDouble).setOnClickListener(this);
        findViewById(R.id.ckbCustomMap).setOnClickListener(this);
        findViewById(R.id.ckbMusicCase).setOnClickListener(this);
        GameContext.getGameContext().setClient(this);
    }


    /**
     * The game controller, to start a game.
     */
    protected void startGame() {
        mGame = GameFactory.createGame(mOption);
        mGame.setGameContext(GameContext.getGameContext());
        mGame.setEngine(Engine.getEngine());
        mGame.start();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                startGame();
                break;
            case R.id.btnExit:
                finish();
                break;
            case R.id.rdbDouble:
                mOption.setIsSinglePlay(false);
                break;
            case R.id.rdbSingle:
                mOption.setIsSinglePlay(true);
                break;
            case R.id.ckbCustomMap:
                if (((CheckBox)v).isChecked()) {
                    mOption.setIsCustomGame(true);
                }else{
                    mOption.setIsCustomGame(false);
                }
            case R.id.ckbMusicCase:
                if (((CheckBox)v).isChecked()) {
                    mOption.setIsSoundOn(true);
                }else{
                    mOption.setIsSoundOn(false);
                }
                break;
        }
    }


	public final void onBackPressed() {
        mIsMenuOpen = !mIsMenuOpen;
        if (mIsMenuOpen){
            super.openOptionsMenu();
        }

        if (mGame != null) mGame.stop();
        finish();
	}


	protected final void onPause() {
		super.onPause();
	}


	protected final void onResume() {
		super.onResume();
	}


    public final void onConfigurationChanged(Configuration cfg) {
        super.onConfigurationChanged(cfg);
    }
}


