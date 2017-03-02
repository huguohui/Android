package com.downloader.base;

import com.downloader.manager.AbstractDescriptor;

/**
 * Interface of task.
 */
public interface Task {
	/**
	 * Get the progress of task. value: 0 ~ 100
	 * @return Progress of task.
	 */
	int progress();


	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	AbstractTaskInfo info();
}
