package com.badsocket.manager;

import com.badsocket.util.Log;

/**
 * Thread manager.
 */
public abstract class AbstractThreadManager
		extends AbstractManager<Thread>
		implements Thread.UncaughtExceptionHandler {

	public abstract Thread[] create(Runnable... r);

	public abstract Thread create(Runnable r);

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		Log.d("There is a exception at thread " + thread.getName());
		throwable.printStackTrace();
	}

}
