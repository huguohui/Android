package com.badsocket.core;

/**
 * A stopable object for managment.
 */
public interface Stopable {

	/**
	 * stop the object of managment.
	 */
	void stop();

	boolean stoped();

}