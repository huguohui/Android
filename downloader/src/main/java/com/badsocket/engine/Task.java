package com.badsocket.engine;

import com.badsocket.engine.worker.Workable;

/**
 * Interface of task.
 */
public abstract class Task implements Workable {

	protected static int id = 0;

	protected OnTaskFinishListener onFinishListener;

	protected String name;

	public enum State {
		unstart, waiting, initing, running, paused, resuming, stoped, finished
	}

	protected State state;


	protected Task() {
		id++;
	}


	protected Task(String name) {
		this();
	}

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


	public String getName() {
		return name;
	}


	public Task setOnFinishListener(OnTaskFinishListener onFinishListener) {
		this.onFinishListener = onFinishListener;
		return this;
	}


	public interface OnTaskFinishListener {
		void onTaskFinish(Task t);
	}
}
