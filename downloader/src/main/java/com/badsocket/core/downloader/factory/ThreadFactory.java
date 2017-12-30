package com.badsocket.core.downloader.factory;

public interface ThreadFactory extends Factory<Thread>, java.util.concurrent.ThreadFactory {

	Thread createThread(String name, Runnable runnable, int priority);


	Thread createThread(Runnable runnable);

}
