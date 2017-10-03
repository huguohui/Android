package com.downloader.engine;

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
	public abstract AbstractTaskInfo info();


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
