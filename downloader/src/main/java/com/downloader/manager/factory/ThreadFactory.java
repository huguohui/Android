package com.downloader.manager.factory;

import com.downloader.manager.AbstractDescriptor;
import com.downloader.util.StringUtil;

public abstract class ThreadFactory {
	public final static int THREAD_NAME_LEN = 20;

	public static Thread createThread(String name, Runnable runnable, int priority) {
		if (runnable == null)
			return null;

		Thread t = new Thread(runnable);
		t.setName(name);
		t.setPriority(priority);

		return t;
	}


	public static Thread createThread(Runnable runnable) {
		return createThread(StringUtil.nonceStr(THREAD_NAME_LEN), runnable, Thread.NORM_PRIORITY);
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
