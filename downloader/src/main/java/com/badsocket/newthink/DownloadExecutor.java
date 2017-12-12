package com.badsocket.newthink;

import android.support.annotation.NonNull;

import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by skyrim on 2017/12/2.
 */

public class DownloadExecutor extends ScheduledThreadPoolExecutor {

	/**
	 * Creates a new {@code ScheduledThreadPoolExecutor} with the
	 * given core pool size.
	 *
	 * @param corePoolSize the number of threads to keep in the pool, even
	 *                     if they are idle, unless {@code allowCoreThreadTimeOut} is set
	 * @throws IllegalArgumentException if {@code corePoolSize < 0}
	 */
	public DownloadExecutor(int corePoolSize) {
		super(corePoolSize);
	}
}