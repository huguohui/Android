package com.downloader.engine;

import com.downloader.manager.ThreadManager;

/**
 */
public class AsyncWorker extends AbstractWorker {
	protected ThreadManager threadManager;

	public AsyncWorker(ThreadManager tm) {
		threadManager = tm;
	}


	/**
	 * Get thread of this worker.
	 *
	 * @return A thread of this worker.
	 */
	@Override
	public Thread thread() {
		return mThread;
	}


	/**
	 * Controls the task start.
	 */
	@Override
	public void start() throws Exception {
		mThread.start();
	}


	protected void doWork(Workable workable) throws Exception {
		if (workable == null)
			return;
	}
}
