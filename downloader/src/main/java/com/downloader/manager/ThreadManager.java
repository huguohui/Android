package com.downloader.manager;

import com.downloader.manager.factory.ThreadFactory;
import com.downloader.util.Log;

import java.util.List;

/**
 * Thread manager.
 */
public class ThreadManager extends AbstractThreadManager {
	/** Single instance of ThreadManager. */
	private static ThreadManager mManager = null;

	protected ThreadManager() {
	}


	/**
	 * Get single instance of ThreadManager.
	 * @return Instance of ThreadManager.
	 */
	public synchronized static ThreadManager getInstance() {
		return mManager == null ? mManager = new ThreadManager() : mManager;
	}


	@Override
	public Thread[] alloc(Runnable... r) {
		int size = r.length;
		Thread[] threads = new Thread[size];

		synchronized (mList) {
			for (int i = 0; i < size; i++) {
				if (r[i] == null)
					continue;

				threads[i] = ThreadFactory.createThread(r[i]);
				threads[i].setUncaughtExceptionHandler(this);
				mList.add(threads[i]);
			}
		}

		return threads;
	}


	@Override
	public Thread alloc(Runnable r) {
		Thread th = ThreadFactory.createThread(r);
		th.setUncaughtExceptionHandler(this);
		synchronized (mList) {
			mList.add(th);
		}

		return th;
	}
}
