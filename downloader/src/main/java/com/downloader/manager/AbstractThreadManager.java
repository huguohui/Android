package com.downloader.manager;

import com.downloader.util.Log;

/**
 * Thread manager.
 */
public abstract class AbstractThreadManager extends AbstractManager<Thread> implements Thread.UncaughtExceptionHandler {

	public abstract Thread[] alloc(Runnable... r);


	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		Log.println("There is a exception at thread " + thread.getName());
	}


	/**
	 * Thread descriptor.
	 */
	public static class ThreadDescriptor extends AbstractDescriptor {
		/** Name of thread. */
		public String name = "";

		/** priority of thread. */
		public int priority = Thread.NORM_PRIORITY;

		/** Runnable object. */
		public Runnable runnable;


		/**
		 * Create a thread descriptor with name and priority.
		 * @param name Name of thread.
		 * @param priority priority of thread.
		 */
		public ThreadDescriptor(Runnable runnable, String name, int priority) {
			if (runnable == null || name == null || priority <= 0)
				return;

			this.runnable = runnable;
			this.name = name;
			this.priority = priority;
		}


		/**
		 * Create a thread descriptor with name.
		 * @param name Thread of name.
		 */
		public ThreadDescriptor(Runnable runnable, String name) {
			this(runnable, name, Thread.NORM_PRIORITY);
		}
	}
}
