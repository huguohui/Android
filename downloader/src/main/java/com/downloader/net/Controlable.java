package com.downloader.net;

/**
 * Defines some method for describing object is controlable.
 * @since 2016/12/25 16:23:06
 */
public interface Controlable {
	/**
	 * Controls the task start.
	 */
	void start();
	
	
	/**
	 * Controls the task pause.
	 */
	void pause();
	
	
	/**
	 * Controls the task resume.
	 */
	void resume();
	
	
	/**
	 * Controls the task stop.
	 */
	void stop();
}
