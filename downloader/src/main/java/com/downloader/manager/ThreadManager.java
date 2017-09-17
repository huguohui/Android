package com.downloader.manager;

import java.io.IOException;

/**
 * Thread manager.
 */
public class ThreadManager extends AbstractManager<Thread> {
	/** Single instance of ThreadManager. */
	private static ThreadManager mManager = null;


	/**
	 * To create something for managing.
	 *
	 * @param descriptor Data for creating.
	 * @return true for success, false for fail.
	 */
	@Override
	public synchronized Thread create(AbstractDescriptor descriptor) throws IOException {
		ThreadDescriptor td = (ThreadDescriptor) descriptor;
		Thread thread = new Thread(td.runnable);
		thread.setName(td.name);
		thread.setPriority(td.priority);
		mList.add(thread);
		return thread;
	}


	/**
	 * Get single instance of ThreadManager.
	 * @return Instance of ThreadManager.
	 */
	public synchronized static ThreadManager getInstance() {
		return mManager == null ? mManager = new ThreadManager() : mManager;
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
