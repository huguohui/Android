package com.badsocket.manager.factory;


/**
 * Factory for creating object simply.
 */
public interface Factory<T> {
	/**
	 * To creating a object.
	 * @return A object.
	 */
	public abstract T create();
}
