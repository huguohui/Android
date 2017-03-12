package com.downloader.base;

/**
 * A controlable thread.
 */
public class ControlableThread extends Thread implements Controlable {
	/** Runnable for thread. */
	protected Runnable mRunnable;

	/** State of thread. */
	protected State mState;


	/**
	 * Controls the task pause.
	 */
	@Override
	public void pause() throws Exception {

	}


	public void resume() {

	}
}
