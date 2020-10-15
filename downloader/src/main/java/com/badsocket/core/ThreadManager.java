package com.badsocket.core;

import android.support.annotation.NonNull;

import com.badsocket.core.downloader.factory.ThreadFactory;

/**
 * Thread manager.
 */
public class ThreadManager extends AbstractThreadManager implements ThreadFactory {
	/**
	 * Single instance of ThreadManager.
	 */
	private static ThreadManager mManager = null;

	protected ThreadManager() {

	}

	/**
	 * Get single instance of ThreadManager.
	 *
	 * @return Instance of ThreadManager.
	 */
	public synchronized static ThreadManager getInstance() {
		synchronized (ThreadManager.class) {
			if (mManager == null) {
				mManager = new ThreadManager();
			}
		}
		return mManager;
	}

	@Override
	public Thread newThread(@NonNull Runnable runnable) {
		return createThread(runnable.toString(), runnable, Thread.NORM_PRIORITY);
	}

	public synchronized Thread createThread(String name, Runnable runnable, int priority) {
		if (runnable == null)
			return null;

		Thread t = new Thread(runnable);
		t.setName(name);
		t.setPriority(priority);
		add(t);
		return t;
	}

	@Override
	public Thread createThread(Runnable runnable) {
		return createThread(runnable.toString(), runnable, Thread.NORM_PRIORITY);
	}
}
