package com.downloader.engine;

import com.downloader.engine.AbstractTaskInfo;
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
