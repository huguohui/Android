package com.badsocket.core;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by skyrim on 2017/11/28.
 */
public interface Task
		extends Serializable, Callable<Integer>, TaskLifecycle, Updatable {

	/**
	 * Gets unique id of task.
	 *
	 * @return unique id of task.
	 */
	int getId();

	/**
	 * Gets name of task.
	 *
	 * @return name of task.
	 */
	String getName();

	void setName(String name);

	/**
	 * Gets start time of task in milliseconds.
	 *
	 * @return start time of task in milliseconds.
	 */
	long getStartTime();

	/**
	 * Gets progress in precent of task.
	 *
	 * @return progress in precent of task.
	 */
	float getProgress();

	/**
	 * Gets used time of task that from start to finish in milliseconds.
	 *
	 * @return used time of task in milliseconds.
	 */
	long getUsedTime();

	/**
	 * Gets finish time of task.
	 *
	 * @return finsih time of task.
	 */
	long getFinishTime();

	int getPriority();

	void setPriority(int priority);

	boolean isStoped();

	boolean isRunning();

	boolean isCompleted();

	boolean isPauseSupport();

	int getState();

	TaskExtraInfo getExtraInfo();

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

