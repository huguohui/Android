package com.badsocket.core;

import com.badsocket.core.downloader.factory.ThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by skyrim on 2018/1/1.
 */

public class ThreadExecutor extends ScheduledThreadPoolExecutor {

	/**
	 * Creates a new {@code ScheduledThreadPoolExecutor} with the
	 * given core pool size.
	 *
	 * @param corePoolSize the number of threads to keep in the pool, even
	 *                     if they are idle, unless {@code allowCoreThreadTimeOut} is set
	 * @throws IllegalArgumentException if {@code corePoolSize < 0}
	 */
	public ThreadExecutor(int corePoolSize) {
		super(corePoolSize);
	}


	public ThreadExecutor(int corePoolSize, ThreadFactory factory) {
		super(corePoolSize, factory);
	}
}
