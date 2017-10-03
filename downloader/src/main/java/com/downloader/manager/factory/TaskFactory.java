package com.downloader.manager.factory;


import com.downloader.engine.Task;

public abstract class TaskFactory extends Factory {
	/**
	 * To creating a object.
	 * @return A object.
	 */
	@Override
	public abstract Task create();
}
