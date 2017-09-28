package com.downloader.manager.factory;

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
}
