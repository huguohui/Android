package com.badsocket.core;

/**
 * Created by skyrim on 2017/11/28.
 */

public abstract class AbstractTask implements Task {

	protected int id;

	protected String name;

	protected long startTime;

	protected long finishTime;

	protected long usedTime;

	protected float progress;

	protected int priority;

	protected boolean isStoped;

	protected boolean isRunning;

	protected boolean isFinished;

	private static int UNIQUE_ID = 0;


	public AbstractTask() {
		this(null);
	}


	public AbstractTask(String name) {
		synchronized (this) {
			id = UNIQUE_ID++;
		}

		this.name = name;
	}


	/**
	 * Gets unique id of task.
	 *
	 * @return unique id of task.
	 */
	@Override
	public int getId() {
		return id;
	}


	/**
	 * Gets name of task.
	 *
	 * @return name of task.
	 */
	@Override
	public String getName() {
		return name == null ? toString() : name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets start time of task in milliseconds.
	 *
	 * @return start time of task in milliseconds.
	 */
	@Override
	public long getStartTime() {
		return startTime;
	}


	/**
	 * Gets progress in precent of task.
	 *
	 * @return progress in precent of task.
	 */
	@Override
	public float getProgress() {
		return progress;
	}


	/**
	 * Gets used time of task that from start to finish in milliseconds.
	 *
	 * @return used time of task in milliseconds.
	 */
	@Override
	public long getUsedTime() {
		return usedTime;
	}


	/**
	 * Gets finish time of task.
	 *
	 * @return finsih time of task.
	 */
	@Override
	public long getFinishTime() {
		return finishTime;
	}


	@Override
	public int getPriority() {
		return priority;
	}


	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}


	@Override
	public boolean isRunning() {
		return isRunning;
	}


	@Override
	public boolean isFinished() {
		return isFinished;
	}


	@Override
	public boolean isStoped() {
		return isStoped;
	}
}
