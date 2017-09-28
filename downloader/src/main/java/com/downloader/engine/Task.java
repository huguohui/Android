package com.downloader.engine;

import com.downloader.engine.AbstractTaskInfo;
import com.downloader.engine.Controlable;

/**
 * Interface of task.
 */
public abstract class Task implements Controlable {
	/**
	 * Get information of current task.
	 * @return Information of current task.
	 */
	public abstract AbstractTaskInfo info();
}
