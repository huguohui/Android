package com.badsocket.core;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by skyrim on 2017/11/28.
 */
public interface Task
		extends Serializable, Callable, TaskLifecycle {

	/**
	 * Gets unique id of task.
	 *
	 * @return unique id of task.
	 */
	int id();

	/**
	 * Gets name of task.
	 *
	 * @return name of task.
	 */
	String name();

	void name(String name);

	/**
	 * Gets start time of task in milliseconds.
	 *
	 * @return start time of task in milliseconds.
	 */
	long createTime();

	/**
	 * Gets progress in precent of task.
	 *
	 * @return progress in precent of task.
	 */
	float progress();

	/**
	 * Gets used time of task that from start to finish in milliseconds.
	 *
	 * @return used time of task in milliseconds.
	 */
	long usedTime();

	/**
	 * Gets finish time of task.
	 *
	 * @return finsih time of task.
	 */
	long finishTime();

	int priority();

	void priority(int priority);

	boolean isStoped();

	boolean isRunning();

	boolean isCompleted();

	boolean isPauseSupport();

	int state();

	TaskExtraInfo extraInfo();

	boolean equals(Task t);

	void addOnTaskFinishListener(OnTaskFinishListener listener);

	void addOnTaskStopListener(OnTaskStopListener listener);

	void addOnTaskStartListener(OnTaskStartListener listener);

	void addOnTaskCreateListener(OnTaskCreateListener listener);

	interface TaskState {
		int UNSTART = 0,
			RUNNING = 1,
			STOPED = 2,
			COMPLETED = 3;
	}

	public static abstract class TaskExtraInfo {

	}

	interface OnTaskCreateListener {
		void onTaskCreate(Task t);
	}

	interface OnTaskStartListener {
		void onTaskStart(Task t);
	}

	interface OnTaskFinishListener {
		void onTaskFinish(Task t);
	}

	interface OnTaskStopListener {
		void onTaskStop(Task t);
	}

}

