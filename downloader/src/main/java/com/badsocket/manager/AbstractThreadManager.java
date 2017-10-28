package com.badsocket.manager;

import com.badsocket.util.Log;

/**
 * Thread manager.
 */
public abstract class AbstractThreadManager extends AbstractManager<Thread> implements Thread.UncaughtExceptionHandler {


	public abstract Thread[] alloc(Runnable... r);


	public abstract Thread alloc(Runnable r);


	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		Log.println("There is a exception at thread " + thread.getName());
		throwable.printStackTrace();
	}


}
