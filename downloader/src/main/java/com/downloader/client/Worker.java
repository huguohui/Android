package com.downloader.client;

/**
 * Worker is running on child thread for do somthing.
 */
public interface Worker extends Runnable {
	/**
	 * Start to doing work.
	 */
	void start() throws Exception;


	/**
	 * Add a workable to working.
	 * @param workable For working.
	 */
	void add(Workable workable) throws Exception;


	/**
	 * Remove a workable.
	 * @param workable Will to removing.
	 */
	Workable remove(Workable workable);


	/**
	 * Get thread of this worker.
	 * @return A thread of this worker.
	 */
	Thread thread();


	/**
	 * Listener for listening worker state.
	 */
	interface OnWorkDoneListener {
		/**
		 * Invokes on work done.
		 * @param w {@lik Worker}.
		 */
		void onWorkDone(Worker w);
	}
}
