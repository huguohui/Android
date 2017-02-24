package com.downloader.base;

/**
 * Interface of task.
 */
public abstract class AbstractTask implements Controlable {
	/** Name of task. */
	protected String name;


	/**
	 * Create a task with name.
	 * @param name Task name.
	 */
	public AbstractTask(String name) {
		this.name = name;
	}


	/**
	 * Get the progress of task. value: 0 ~ 100
	 * @return Progress of task.
	 */
	public abstract int progress();


	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	public abstract TaskInfo info();
}
