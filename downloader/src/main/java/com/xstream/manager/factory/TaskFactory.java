package com.xstream.manager.factory;


import com.xstream.engine.Task;

public abstract class TaskFactory extends Factory {
	/**
	 * To creating a object.
	 * @return A object.
	 */
	@Override
	public abstract Task create();
}
