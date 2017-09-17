package com.downloader.client;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Abstract
 */
public class TaskWorker extends AbstractWorker {
	/**
	 * Start to doing work.
	 */
	@Override
	public void start() throws Exception {

	}


	/**
	 * Get thread of this worker.
	 *
	 * @return A thread of this worker.
	 */
	@Override
	public Thread thread() {
		return null;
	}
}
