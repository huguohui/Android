package com.downloader.manager;

import java.io.IOException;

/**
 * Thread manager.
 */
public class ThreadManager extends AbstractThreadManager {
	/** Single instance of ThreadManager. */
	private static ThreadManager mManager = null;


	/**
	 * Get single instance of ThreadManager.
	 * @return Instance of ThreadManager.
	 */
	public synchronized static ThreadManager getInstance() {
		return mManager == null ? mManager = new ThreadManager() : mManager;
	}


	@Override
	public Thread[] alloc(Runnable... r) {
		return new Thread[0];
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
