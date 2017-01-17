package com.downloader;

/**
 * Interface of task.
 */
public interface Task extends Controlable {
	/**
	 * Do work in this method.
	 */
	void doWork();
	
	
	/**
	 * Get the progress of task. value: 0 ~ 100
	 * @return Progress of task.
	 */
	int progress();
}
