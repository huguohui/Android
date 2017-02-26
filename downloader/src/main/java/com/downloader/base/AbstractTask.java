package com.downloader.base;

import com.downloader.manager.AbstractDescriptor;

/**
 * Interface of task.
 */
public abstract class AbstractTask implements Controlable {
	/**
	 * Create a task with name.
	 * @param name Task name.
	 */
	public AbstractTask(String name) {

	}



	/**
	 * Create a task by special task descriptor.
	 * @param desc Task descriptor.
	 */
	public AbstractTask(AbstractDescriptor desc) {

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
	public abstract AbstractTaskInfo info();
}
