package com.downloader.engine;

import com.downloader.manager.ThreadManager;
import com.downloader.util.Log;
import com.downloader.util.TimeUtil;

/**
 */
public class AsyncWorker extends AbstractWorker {

	protected ThreadManager threadManager;

	protected long interval = 0;

	protected long prevWorkTime;

	protected Object waitLock = new Object();


	public AsyncWorker(ThreadManager tm) {
		this(tm, 0);
	}


	public AsyncWorker(ThreadManager tm, long interval) {
		threadManager = tm;
		this.interval = interval;
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


	protected void waitTimes(long time) throws InterruptedException {
		synchronized (waitLock) {
			waitLock.wait(time);
		}
	}


	protected void doWork(Workable workable) throws Exception {
		if (workable == null || interval == 0) {
			return;
		}

		threadManager.alloc(new RunnableAdapter(workable)).start();
		waitTimes(interval);
	}
}

