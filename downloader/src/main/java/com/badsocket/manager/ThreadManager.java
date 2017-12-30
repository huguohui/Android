package com.badsocket.manager;

import com.badsocket.core.downloader.factory.BaseThreadFactory;
import com.badsocket.core.downloader.factory.ThreadFactory;

/**
 * Thread manager.
 */
public class ThreadManager extends AbstractThreadManager {
	/** Single instance of ThreadManager. */
	private static ThreadManager mManager = null;

	private ThreadFactory factory = new BaseThreadFactory();

	protected ThreadManager() {

	}


	/**
	 * Get single instance of ThreadManager.
	 * @return Instance of ThreadManager.
	 */
	public synchronized static ThreadManager getInstance() {
		synchronized (ThreadManager.class) {
			if (mManager == null) {
				mManager = new ThreadManager();
			}

			return mManager;
		}
	}


	@Override
	public Thread[] alloc(Runnable... r) {
		int size = r.length;
		Thread[] threads = new Thread[size];

		synchronized (mList) {
			for (int i = 0; i < size; i++) {
				if (r[i] == null)
					continue;

				threads[i] = factory.createThread(r[i]);
				threads[i].setUncaughtExceptionHandler(this);
				mList.add(threads[i]);
			}
		}

		return threads;
	}


	@Override
	public Thread alloc(Runnable r) {
		Thread th = factory.createThread(r);
		th.setUncaughtExceptionHandler(this);
		synchronized (mList) {
			mList.add(th);
		}

		return th;
	}


	public final static void release() {
		mManager.list().clear();
		mManager = null;
	}
}
