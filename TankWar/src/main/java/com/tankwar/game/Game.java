package com.tankwar.game;

import com.tankwar.engine.Engine;
import com.tankwar.engine.GameContext;

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
	 * A game game instance;
	 */
	private Engine mEngine;

	/**
	 * The game options.
	 */
	private Option mOption;

	/**
	 * The game default screen width.
	 */
	public final static int SCREEN_WIDTH = 480;

	/**
	 * The game default scrren height.
	 */
	public final static int SCREEN_HEIGHT = 320;

	/**
	 * Private constructor.
	 */
	private Game() {
		this.initialize();
	}

	/**
	 * Get game option.
	 *
	 * @return Game option.
	 */
	public Option getOption() {
		return mOption;
	}

	/**
	 * Get game context.
	 *
	 * @return Game context.
	 */
	public GameContext getGameContext() {
		return mGameContext;
	}

	/**
	 * Set game context.
	 *
	 * @param mGameContext Game context.
	 */
	public void setGameContext(GameContext mGameContext) {
		this.mGameContext = mGameContext;
	}

	/**
	 * Set game option.
	 *
	 * @param mOption Game option.
	 */
	public void setOption(Option mOption) {
		this.mOption = mOption;
	}

	/**
	 * Set game context.
	 *
	 * @param engine Game context.
	 */
	public void setEngine(Engine engine) {
		this.mEngine = engine;
	}

	/**
	 * Get game game instance.
	 *
	 * @return Gmae context.
	 */
	public Engine getEngine() {
		return mEngine;
	}

	/**
	 * Do some initialize work.
	 */
	private void initialize() {
		GameContext.getGameContext().setGame(this);
	}

	/**
	 * Start a game.
	 */
	public void start() {
		GameRound round = new GameRound(mEngine);
		mEngine.addUpdatable(round);
		mEngine.start();
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
	 *
	 * @return Singleton instance of game.
	 */
	public static Game create() {
		return mGame == null ? (mGame = new Game()) : mGame;
	}

	/**
	 * Describe game option.
	 */
	public static class Option {
		private boolean mIsSinglePlay = true;
		private boolean mIsCustomGame = false;
		private boolean mIsSoundOn = true;

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

	/**
	 * Game state listener.
	 *
	 * @since 2015/11/06
	 */
	public interface StateListener {
		/**
		 * When game initialized.
		 *
		 * @param context Game context.
		 */
		void onInitialize(GameContext context);

		/**
		 * When game start work.
		 *
		 * @param context Game context.
		 */
		void onStart(GameContext context);

		/**
		 * When game pause.
		 *
		 * @param context Game context.
		 */
		void onPause(GameContext context);

		/**
		 * When game resume.
		 *
		 * @param context Game context.
		 */
		void onResume(GameContext context);

		/**
		 * When game stop work.
		 *
		 * @param context Game context.
		 */
		void onStop(GameContext context);

		/**
		 * When engine exit.
		 *
		 * @pram engine engine engine.
		 */
		void onExit(Engine engine);
	}
}
