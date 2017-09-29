package com.downloader.engine;

import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.Controlable;

/**
 * Interface of task.
 */
public abstract class Task implements Controlable {

	public enum State {
		unstart, init, running, paused, resuming, stoped, finished
	}

	protected State state;

	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	public abstract AbstractTaskInfo info();


	public State getState() {
		return state;
	}
}
