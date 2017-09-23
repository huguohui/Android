package com.downloader.engine;

import com.downloader.manager.ThreadManager;

/**
 */
public class AsyncWorker extends AbstractWorker {
	protected ThreadManager threadManager;

	public AsyncWorker(ThreadManager tm) {
		threadManager = tm;
		mThread = tm.alloc(this);
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
	public void start() {
		mThread.start();
	}


	protected void doWork(Workable workable) throws Exception {
		if (workable == null)
			return;

		threadManager.alloc(new RunnableAdapter(workable)).start();
	}
}

