package com.xstream.manager.factory;


/**
 * Factory for creating object simply.
 */
public abstract class Factory<T> {
	/**
	 * To creating a object.
	 * @return A object.
	 */
	public abstract T create();
}
