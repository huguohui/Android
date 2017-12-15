package com.badsocket.newthink;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by skyrim on 2017/11/28.
 */
public interface Task extends Runnable, Serializable, Callable<Task> {

	/**
	 * Gets unique id of task.
	 * @return unique id of task.
	 */
	int getId();


	/**
	 * Gets name of task.
	 * @return name of task.
	 */
	String getName();


	void setName(String name);


	/**
	 * Gets start time of task in milliseconds.
	 * @return start time of task in milliseconds.
	 */
	long getStartTime();


	/**
	 * Gets progress in precent of task.
	 * @return progress in precent of task.
	 */
	float getProgress();


	/**
	 * Gets used time of task that from start to finish in milliseconds.
	 * @return used time of task in milliseconds.
	 */
	long getUsedTime();


	/**
	 * Gets finish time of task.
	 * @return finsih time of task.
	 */
	long getFinishTime();


	int getPriority();


	void setPriority(int priority);


	boolean isStoped();


	boolean isRunning();


	boolean isFinished();


	void onCreate();


	void onStart();


	void onStop();


	void onFinish();


	TaskStatus getStatus();


	abstract class TaskStatus {

		protected String description;

		protected int code;


		public String getDescription() {
			return description;
		}


		protected void setDescription(String description) {
			this.description = description;
		}


		public int getCode() {
			return code;
		}


		protected void setCode(int code) {
			this.code = code;
		}


		public TaskStatus(int code, String desc) {
			this.code = code;
			description = desc;
		}

	}

}
