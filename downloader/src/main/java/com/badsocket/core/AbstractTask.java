package com.badsocket.core;

import android.os.SystemClock;

import static com.badsocket.core.downloader.ControlableClock.MS_SECOND;
/**
 * Created by skyrim on 2017/11/28.
 */

public abstract class AbstractTask implements Task {

	private static final long serialVersionUID = 9103658319690261655L;

	protected int id;

	protected String name = "";

	protected long createTime;

	protected long finishTime;

	protected long usedTime;

	protected float progress = 0f;

	protected int priority;

	protected boolean isStoped;

	protected boolean isRunning;

	protected boolean isCompleted;

	private static int UNIQUE_ID = 0;

	public AbstractTask() {
		this(null);
	}

	public AbstractTask(String name) {
		synchronized (this) {
			id = UNIQUE_ID++;
		}

		createTime = System.currentTimeMillis();
		this.name = name;
	}

	/**
	 * Gets unique id of task.
	 *
	 * @return unique id of task.
	 */
	@Override
	public int id() {
		return id;
	}

	public void onTick() {
		if (isRunning) {
			usedTime += MS_SECOND;
		}
	}

	/**
	 * Gets name of task.
	 *
	 * @return name of task.
	 */
	@Override
	public String name() {
		return name == null ? "" : name;
	}

	@Override
	public void name(String name) {
		this.name = name;
	}

	/**
	 * Gets start time of task in milliseconds.
	 *
	 * @return start time of task in milliseconds.
	 */
	@Override
	public long createTime() {
		return createTime;
	}

	/**
	 * Gets progress in precent of task.
	 *
	 * @return progress in precent of task.
	 */
	@Override
	public float progress() {
		return progress;
	}

	/**
	 * Gets used time of task that from start to finish in milliseconds.
	 *
	 * @return used time of task in milliseconds.
	 */
	@Override
	public long usedTime() {
		return usedTime;
	}

	/**
	 * Gets finish time of task.
	 *
	 * @return finsih time of task.
	 */
	@Override
	public long finishTime() {
		return finishTime;
	}

	@Override
	public int priority() {
		return priority;
	}

	@Override
	public void priority(int priority) {
		this.priority = priority;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public boolean isCompleted() {
		return isCompleted;
	}

	@Override
	public boolean isStoped() {
		return isStoped;
	}
}
