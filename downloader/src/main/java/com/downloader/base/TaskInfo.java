package com.downloader.base;

/**
 * Information for task.
 */
public abstract class TaskInfo extends AbstractInfo {
	/** Name of task. */
	public String name;


	/**
	 * Create a instance of TaskInfo by special name.
	 * @param name Name of task.
	 */
	public TaskInfo(String name) {
		this.name = name;
	}
}
