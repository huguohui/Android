package com.downloader.manager;

import com.downloader.net.AbstractTaskInfo;
import com.downloader.engine.Controlable;

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
