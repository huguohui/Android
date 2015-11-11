package com.tankwar.client;

import com.tankwar.engine.GameContext;
import com.tankwar.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * A 'game' is real game.
 * The game can start, stop, pause, resume, and exit.
 *
 * @since 2015/11/06
 */
public class Game {
    /**
     * Singleton instance.
     */
    private static Game mGame = null;


    /**
     * The game context;
     */
    private GameContext mGameContext;


    /**
     * A game engine instance;
     */
    private Engine mEngine;


    /**
     * The game options.
     */
    private Option mOption;


    /**
     * Private constructor.
     */
    private Game() {
        this.initialize();
        
    }


    /**
     * Get game option.
     * @return Game option.
     */
    public Option getOption() {
        return mOption;
    }


    /**
     * Get game context.
     * @return  Game context.
     */
    public GameContext getGameContext() {
        return mGameContext;
    }


    /**
     * Set game context.
     * @param mGameContext Game context.
     */
    public void setGameContext(GameContext mGameContext) {
        this.mGameContext = mGameContext;
    }

    /**
     * Set game option.
     * @param mOption Game option.
     */
    public void setOption(Option mOption) {
        this.mOption = mOption;
    }

    /**
     * Set game engine.
     * @param engine Game engine.
     */
    public void setEngine(Engine engine) {
        this.mEngine = engine;
    }


    /**
     * Get game engine instance.
     * @return Gmae engine.
     */
    public Engine getEngine() {
        return mEngine;
    }


    /**
     * Do some initialize work.
     */
    private void initialize() {

    }


    /**
     * Start a game.
     */
    public void start() {
        this.mEngine.start();
    }


    /**
     * Restart a game.
     */
    public void restart() {

    }


    /**
     * Pause a game.
     */
    public void pause() {
        this.mEngine.pause();
    }


    /**
     * Resume a game.
     */
    public void resume() {
        this.mEngine.resume();
    }


    /**
     * Stop a game.
     */
    public void stop() {
        this.mEngine.stop();
    }


    /**
     * Get singleton instance of game.
     * @return Singleton instance of game.
     */
    public static Game create() {
        return mGame == null ? (mGame = new Game()) : mGame;
    }


    /**
     * Describe game option.
     */
    public class Option {
        private boolean mIsSinglePlay = true;
        private boolean mIsCustomGame = false;
        private boolean mIsSoundOn    = true;

        public boolean isIsSinglePlay() {
            return mIsSinglePlay;
        }

        public void setIsSinglePlay(boolean mIsSinglePlay) {
            this.mIsSinglePlay = mIsSinglePlay;
        }

        public boolean isIsCustomGame() {
            return mIsCustomGame;
        }

        public void setIsCustomGame(boolean mIsCustomGame) {
            this.mIsCustomGame = mIsCustomGame;
        }

        public boolean isIsSoundOn() {
            return mIsSoundOn;
        }

        public void setIsSoundOn(boolean mIsSoundOn) {
            this.mIsSoundOn = mIsSoundOn;
        }
    }
}
