package com.badsocket.manager.factory;

import android.support.annotation.NonNull;

/**
 * Created by skyrim on 2017/12/15.
 */

public class BaseThreadFactory implements ThreadFactory {
	/**
	 * To creating a object.
	 *
	 * @return A object.
	 */
	@Override
	public Thread create() {
		return createThread(null, null, Thread.NORM_PRIORITY);
	}


	@Override
	public Thread newThread(@NonNull Runnable runnable) {
		return createThread(runnable.toString(), runnable, Thread.NORM_PRIORITY);
	}


	public Thread createThread(String name, Runnable runnable, int priority) {
		if (runnable == null)
			return null;

		Thread t = new Thread(runnable);
		t.setName(name);
		t.setPriority(priority);

		return t;
	}


	@Override
	public Thread createThread(Runnable runnable) {
		return createThread(runnable.toString(), runnable, Thread.NORM_PRIORITY);
	}
}
