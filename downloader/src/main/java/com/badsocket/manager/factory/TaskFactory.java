package com.badsocket.manager.factory;


import com.badsocket.core.Task;

public abstract class TaskFactory extends Factory {
	/**
	 * To creating a object.
	 * @return A object.
	 */
	@Override
	public abstract Task create();
}
