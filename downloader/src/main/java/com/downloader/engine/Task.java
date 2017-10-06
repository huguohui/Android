package com.downloader.engine;

import com.downloader.engine.worker.Workable;

/**
 * Interface of task.
 */
public abstract class Task implements Controlable, Workable {

	protected static int id = 0;

	protected OnTaskFinishListener onFinishListener;

	protected Task() {
		id++;
	}

	public enum State {
		unstart, waiting, initing, running, paused, resuming, stoped, finished
	}

	protected State state;

	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	public abstract TaskInfo info();


	public State getState() {
		return state;
	}


	public int getId() {
		return id;
	}


	public Task setOnFinishListener(OnTaskFinishListener onFinishListener) {
		this.onFinishListener = onFinishListener;
		return this;
	}


	public interface OnTaskFinishListener {
		void onTaskFinish(Task t);
	}
}
