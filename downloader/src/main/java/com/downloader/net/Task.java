package com.downloader.net;

/**
 * Interface of task.
 */
public interface Task extends Controlable {
	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	AbstractTaskInfo info();
}
