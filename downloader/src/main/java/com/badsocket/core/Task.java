package com.badsocket.core;

import com.badsocket.worker.Workable;

/**
 * Interface of task.
 */
public abstract class Task implements Workable {

	protected static int GLOBAL_ID = 0;

	protected int id;

	protected String name;

	protected OnTaskFinishListener onFinishListener;

	public enum State {
		unstart, waiting, initing, running, paused, resuming, stoped, finished
	}

	protected State state = State.unstart;


	protected Task() {
		synchronized (this) {
			id = GLOBAL_ID++;
		}
	}


	protected Task(String name) {
		this();
		this.name = name;
	}

	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	public abstract TaskInfo info();


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
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
